package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Board extends JPanel{
	public static final int MAX_BOARD_SIZE = 50;
	public static final int BOARD_WIDTH = 600; // FIXME: CHANGE THIS TO WORK WITH DIFFERENT CONFIG
	public static final int BOARD_HEIGHT = 630;
	public static final int CELL_SIZE = 30;
	public static final int MAX_WEAPONS = 4;
	public static final int MAX_PLAYERS = 6;
	public static final int MAX_ROOMS = 9;


	private int numRows;
	private int numCols;
	private BoardCell[][] board;
	private Player activePlayer;

	private Map<Character, String> legend;
	private Map<Character, Boolean> roomHasCard;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;

	private Set<BoardCell> targets;
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	private Set<BoardCell> finalTargets;

	private String boardConfigFile;
	private String roomConfigFile;
	private String playerConfigFile;
	private String weaponConfigFile;

	private ArrayList<Card> deck;
	private ArrayList<Player> players;
	private ArrayList<String> weapons;
	private ArrayList<String> rooms;

	private Solution solution;
	private boolean solutionGuessed;

	// for testing purposes
	private ArrayList<Card> playerCards;
	private ArrayList<Card> weaponCards; 


	// variable used for singleton pattern
	private static Board theInstance = new Board();

	// ctor is private to ensure only one can be created
	private Board() {
		legend = new HashMap<Character, String>();
		roomHasCard = new HashMap<Character, Boolean>();
		targets = new HashSet<BoardCell>();
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();

		addMouseListener(new boardListener());
	}

	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	public void initialize(){
		targets.clear();
		rooms = new ArrayList<String>();

		try{
			loadRoomConfig();
			loadBoardConfig();
			calcAdjacencies();
		}
		catch(FileNotFoundException e){
			System.out.println("File not Found");
		}
		catch(BadConfigFormatException e){
			e.getMessage();
		}
	}

	// needed to be seperated for tests to pass
	public void initializeGameplay(){
		players = new ArrayList<Player>();
		deck = new ArrayList<Card>();
		weapons = new ArrayList<String>();
		weaponCards = new ArrayList<Card>();
		playerCards = new ArrayList<Card>();
		try {
			loadPlayerConfig();
			loadWeaponConfig();
		} catch (FileNotFoundException e){
			System.out.println("File not Found");
		}
		makeDeck();
		setSolution();
		dealDeck();
	}

	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException{ 
		FileReader read = new FileReader(roomConfigFile);
		Scanner in = new Scanner(read);
		legend.clear();
		while (in.hasNextLine()){ //maps legend
			String str = in.nextLine();
			String[] words = str.split(",\\s");
			Character c = new Character(words[0].charAt(0));
			legend.put(c, words[1]);
			if (words[2].equals("Card")){
				rooms.add(words[1]); // put room name in rooms arrayList
			}
			if (words[2].equals("Card")) roomHasCard.put(c, true);
			else roomHasCard.put(c, false);
			if ((!words[2].equals("Card")) && (!words[2].equals("Other"))){
				throw new BadConfigFormatException("ERROR:Legend not properly formated");
			}
		}
		in.close();
	}

	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException{
		BoardCell[][] tempBoard = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		FileReader read = new FileReader(boardConfigFile);
		Scanner in = new Scanner(read);
		int row = 0;
		int column = 0;
		//		int count = 0;
		ArrayList<Integer> list = new ArrayList<Integer>();

		while (in.hasNextLine()){
			String str = in.nextLine();
			String[] temp = str.split(","); 

			for(String s: temp){
				if (!legend.containsKey(s.charAt(0))){
					throw new BadConfigFormatException("ERROR:Room not included in legend");
				}
				tempBoard[row][column] = new BoardCell(row,column,s); 
				column++;
			}
			column = 0;
			row++;
		}

		row = 0;
		column = 0;
		int tempC = 0;

		for (BoardCell[] b: tempBoard){//finds where the nulls start in order to form the actual array
			row++;
			for (BoardCell c: b){
				if (c == null){
					list.add(tempC);
					tempC++;
					break;
				}
				tempC++;
				column++;
			}
			if (b[0] == null){
				break;
			}
		}

		for (int o = 1; o < list.size() -2 ; o++){
			int temp1 = list.get(o) - list.get(o-1);
			int temp2 = list.get(o+1) - list.get(o);
			if (temp1 != temp2 ){
				throw new BadConfigFormatException("ERROR:Board not properly formated");
			}
		}

		numRows = row - 1;
		numCols = column/numRows;

		board = new BoardCell[numRows][numCols];

		for (int p = 0; p < numRows; p++){
			for (int y = 0; y < numCols; y++){
				board[p][y] = tempBoard[p][y];
			}
		}

		// close scanner
		in.close();
	}

	public void loadPlayerConfig() throws FileNotFoundException{
		FileReader read = new FileReader(playerConfigFile);
		Scanner in = new Scanner(read);

		while (in.hasNextLine()){ // has more players
			String str = in.nextLine();
			String[] words = str.split(",\\s");
			Color color;
			try {
				Field field = Class.forName("java.awt.Color").getField(words[1].trim());
				color = (Color)field.get(null);
			} catch (Exception e){
				color = null; // not defined
			}
			int row = Integer.parseInt(words[2]);
			int column = Integer.parseInt(words[3]);

			if (words[4].equalsIgnoreCase("Human")) 
				players.add(new HumanPlayer(words[0], color, row, column));
			else 
				players.add(new ComputerPlayer(words[0], color, row, column));
		}

		// close scanner
		in.close();
	}

	private void loadWeaponConfig() throws FileNotFoundException{
		FileReader read = new FileReader(weaponConfigFile);
		Scanner in = new Scanner(read);

		while (in.hasNextLine()){ // has more weapons
			String str = in.nextLine();
			weapons.add(str);
		}

		// close scanner
		in.close();
	}

	private void makeDeck() {
		// make weapon cards
		for (String weapon : weapons){
			Card card = new Card(weapon, CardType.WEAPON);
			deck.add(card);
			weaponCards.add(card);
		}

		// make people cards
		for (Player player : players){
			Card card = new Card(player.getPlayerName(), CardType.PERSON);
			deck.add(card);
			playerCards.add(card);
		}

		// make room cards
		for (String room : rooms){
			Card card = new Card(room, CardType.ROOM);
			deck.add(card);
		}
	}

	private void dealDeck() {
		Collections.shuffle(deck);
		int index = 0;

		for (Card card : deck){
			players.get(index).addToHand(card);
			if (index != players.size()-1){
				index++;
			} else {
				index = 0;
			}			
		}
	}

	public void calcAdjacencies() {
		BoardCell key = new BoardCell(0,0," ");

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				key = getCellAt(i, j);
				key.getInitial();
				Set<BoardCell> list = new HashSet<BoardCell>();
				if (key.isRoom()){
					adjMatrix.put(key, list);
				}
				if(!key.isRoom()) {
					list.clear();
					if ( key.isDoorway()){
						DoorDirection door = key.getDoorDirection();
						switch (door){
						case RIGHT:
							BoardCell temp2 = getCellAt(i, j+1);
							list.add(temp2);
							break;
						case LEFT:
							BoardCell temp3 = getCellAt(i, j-1);
							list.add(temp3);
							break;
						case UP:
							BoardCell temp4 = getCellAt(i-1, j);
							list.add(temp4);
							break;
						case DOWN:
							BoardCell temp5 = getCellAt(i+1, j);
							list.add(temp5);
							break;
						}
					}
					else{
						if((i-1) >=0) { //test up

							BoardCell temp2 = getCellAt(i-1, j);

							if(temp2.isWalkway() || temp2.isDoorway()){ //if walkway or doorway
								if (!(key.isDoorway() && temp2.isDoorway())){ //if not both doorways
									if(temp2.getDoorDirection() == DoorDirection.DOWN) {
										list.add(temp2);
									}
									else if (!temp2.isDoorway()) {
										list.add(temp2);
									}
								}
							}
						}
						if((i+1) < numRows) { //test down
							BoardCell temp2 = getCellAt(i+1, j);

							if(temp2.isWalkway()|| temp2.isDoorway()){
								if (!(key.isDoorway() && temp2.isDoorway())){
									if(temp2.getDoorDirection() == DoorDirection.UP) {
										list.add(temp2);
									}
									else if(!temp2.isDoorway()){
										list.add(temp2);
									}
								}
							}
						}
						if((j-1) >=0) {//test left
							BoardCell temp2 = getCellAt(i, j-1);
							if(temp2.isWalkway()|| temp2.isDoorway()){
								if (!(key.isDoorway() && temp2.isDoorway())){
									if(temp2.getDoorDirection() == DoorDirection.RIGHT) {
										list.add(temp2);
									}
									else if (!temp2.isDoorway()) {
										list.add(temp2);
									}
								}
							}
						}
						if((j+1) < numCols) { // test right
							BoardCell temp2 = getCellAt(i, j+1);
							if(temp2.isWalkway()|| temp2.isDoorway()){
								if (!(key.isDoorway() && temp2.isDoorway())){
									if(temp2.getDoorDirection() == DoorDirection.LEFT) {
										list.add(temp2);
									}
									else if (!temp2.isDoorway()) {
										list.add(temp2);
									}
								}
							}
						}
					}
					adjMatrix.put(key, list);
				}
			}
		}
	}

	//	public void calcTargets(int x, int y, int pathLength){
	//		Set<BoardCell> temp = new HashSet<BoardCell>();
	//		BoardCell tempKey = new BoardCell(0,0," ");
	//		tempKey = getCellAt(x,y);
	//		temp = adjMatrix.get(tempKey);
	//
	//		BoardCell e = new BoardCell(0,0," ");
	//		e = getCellAt(x,y);
	//		visited.add(e);
	//
	//		for(BoardCell current: temp) {
	//			int j = current.getCol();
	//			int i = current.getRow();
	//			if(!visited.contains(getCellAt(i, j))) {
	//				visited.add(current);
	//
	//				if(current.isDoorway()) {
	//					targets.add(current);
	//					visited.remove(current);
	//				}
	//
	//				if(pathLength == 1) {
	//					if(!targets.contains(getCellAt(i, j))) {
	//						targets.add(current);
	//						visited.remove(current);
	//					}
	//				}
	//
	//				else {
	//					calcTargets(current.getRow(), current.getCol(), pathLength -1);
	//				}
	//				visited.remove(current);
	//			}
	//		}
	//	}

	public void calcTargets(int row, int col, int pathLength){
		calcTargets(board[row][col], pathLength);
	}

	public void calcTargets(BoardCell startCell, int pathLength){
		targets.clear();
		visited.clear();

		visited.add(startCell);

		findAllTgts(startCell, pathLength);
	}

	//the recursive bit of the target finding algorithm
	private void findAllTgts(BoardCell start, int dist){

		for(BoardCell cell : adjMatrix.get(start)){ //for each cell next to us
			if(!visited.contains(cell)){ //only do this if we haven't seen this cell yet
				visited.add(cell);

				if(dist == 1){ //if we can only move 1 more space, add it to targets
					targets.add(cell);
				}else if (cell.isDoorway()){ //if we're not done moving, but it's a door, it's a target
					targets.add(cell);
				}else if(dist > 1){ //otherwise, move to that space and keep looking
					findAllTgts(cell, dist-1);
				}

				visited.remove(cell);
			}
		}
	}

	public Card handleSuggestion(int indexOfPlayer, Solution suggestion) {
		// creates new list of players in the correct order to play. Starting with the current player up to one before the current player
		// for example, current player = 2, 3,4,5,0,1
		//		ArrayList<Player> playersInOrder = new ArrayList<>();
		//
		//		for (int i = indexOfPlayer+1; i < players.size(); i++) {
		//			playersInOrder.add(players.get(i));
		//		}
		//		for (int i = 0; i < indexOfPlayer; i++) {
		//			playersInOrder.add(players.get(i));
		//		}
		//
		//		for(int i = 0; i < playersInOrder.size(); i++) {
		//			Player p = playersInOrder.get(i);
		//
		//			if (null != p.disproveSuggestion(suggestion)){
		//				return p.disproveSuggestion(suggestion);
		//			}
		//
		//		}
		//		return null;

		Card disproveCard = new Card("", CardType.PERSON);
		for (int i = 0; i < indexOfPlayer; i++){
			if (players.get(i).disproveSuggestion(suggestion) != null){
				disproveCard = players.get(i).disproveSuggestion(suggestion);
				if (disproveCard.getType() == CardType.PERSON){
					for (int j = 0; j < players.size(); j++){
						if (j == i) continue; // don't add seenCard if in hand
						players.get(j).addToSeenPeople(disproveCard);
					}
				} else if (disproveCard.getType() == CardType.WEAPON){
					for (int j = 0; j < players.size(); j++){
						if (j == i) continue; // don't add seenCard if in hand
						players.get(j).addToSeenWeapons(disproveCard);
					}
				} else {
					for (int j = 0; j < players.size(); j++){
						if (j == i) continue; // don't add seenCard if in hand
						players.get(j).addToSeenRooms(disproveCard);
					}
				}
				return disproveCard;
			}
		}
		for (int i = indexOfPlayer + 1; i < players.size(); i++){
			if (players.get(i).disproveSuggestion(suggestion) != null){
				disproveCard = players.get(i).disproveSuggestion(suggestion);
				if (disproveCard.getType() == CardType.PERSON){
					for (int j = 0; j < players.size(); j++){
						if (j == i) continue; // don't add seenCard if in hand
						players.get(j).addToSeenPeople(disproveCard);
					}
				} else if (disproveCard.getType() == CardType.WEAPON){
					for (int j = 0; j < players.size(); j++){
						if (j == i) continue; // don't add seenCard if in hand
						players.get(j).addToSeenWeapons(disproveCard);
					}
				} else {
					for (int j = 0; j < players.size(); j++){
						if (j == i) continue; // don't add seenCard if in hand
						players.get(j).addToSeenRooms(disproveCard);
					}
				}
				return disproveCard;
			}
		}

		for (Player player : players){
			if (player.disproveSuggestion(suggestion) == null){
				player.setShouldAccuse(true, suggestion);
			}
		}

		return null;
	}

	public boolean checkAccusation(Solution accusation){
		if (solution.person.equals(accusation.person) && 
				solution.room.equals(accusation.room) && 
				solution.weapon.equals(accusation.weapon)) return true;
		return false;
	}

	public Solution runGame(int activePlayerIndex, int dieRoll){
		activePlayer = players.get(activePlayerIndex);

		// set the last room the computer player was in 
		if (getCellAt(activePlayer.getRow(), activePlayer.getColumn()).isDoorway())
		{
			activePlayer.setLastRoom(getCellAt(activePlayer.getRow(), activePlayer.getColumn()).getInitial());
		}

		calcTargets(activePlayer.getRow(), activePlayer.getColumn(), dieRoll);

		for (BoardCell cell : targets){
			cell.setHighlight(true);
		}
		repaint();

		if (activePlayer.isShouldAccuse() && activePlayer instanceof ComputerPlayer && 
				legend.get(getCellAt(activePlayer.getRow(), activePlayer.getColumn()).getInitial()).equals(activePlayer.getAccusation().room)) {
			((ComputerPlayer) activePlayer).makeAccusation(); 
		}
		else {
			activePlayer.makeMove(targets);

			// if the player is in a room allow them to make a solution
			if (getCellAt(activePlayer.getRow(), activePlayer.getColumn()).isDoorway()){
				Solution suggestion = activePlayer.createSuggestion(this.getCellAt(activePlayer.getRow(), activePlayer.getColumn()), playerCards, weaponCards, legend);
				// TODO: move the suggested player into room
				return suggestion;
			}
		}
		targets.clear();

		return null;
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for (BoardCell[] row : board){
			for (BoardCell cell : row){
				cell.draw(g);
			}
		}

		// write room names
		g.drawString("Bedroom", 20, 40);
		g.drawString("Pool", 200, 60);
		g.drawString("Wine Cellar", 330, 60);
		g.drawString("Dining Room", 500, 150);
		g.drawString("Kitchen", 20, 260);
		g.drawString("Lounge", 500, 380);
		g.drawString("Foyer", 40, 580);
		g.drawString("TV Room", 220, 580);
		g.drawString("Restroom", 520, 560);

		for (Player player : players){
			player.draw(g);
		}
	}

	private class boardListener implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			int row = (int) Math.floor(y/CELL_SIZE);
			int column = (int) Math.floor(x/CELL_SIZE);

			boolean invalidClick = false;

			// throw error pane if it isn't the players turn
			if (!(activePlayer instanceof HumanPlayer)){
				JOptionPane.showMessageDialog(null, "It isn't your turn!", "Play in order", JOptionPane.INFORMATION_MESSAGE);
			}
			for (BoardCell[] boardRow : board){
				for (BoardCell cell : boardRow){
					if (column == cell.getCol() && row == cell.getRow()){
						if (!cell.isHighlight()){
							invalidClick = true;
						} else { // clicked on a highlighted target
							activePlayer.setTurnOver(true);
							activePlayer.setLocation(cell);

							if (cell.isRoom()){
								// TODO: add dialog to make suggestion
							}
						}
					}
				}
			}
			if (invalidClick){
				JOptionPane.showMessageDialog(null, "Not a valid spot", "Choose a valid spot", JOptionPane.INFORMATION_MESSAGE);
			} else {
				for (BoardCell[] boardRow : board){
					for (BoardCell cell : boardRow){
						cell.setHighlight(false);
					}
				}
				repaint();
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}
	}

	public void setSolution(){
		String randomPlayer = players.get(new Random().nextInt(players.size())).getPlayerName();
		String randomRoom = rooms.get(new Random().nextInt(rooms.size()));
		String randomWeapon = weapons.get(new Random().nextInt(weapons.size()));
		solution = new Solution(randomPlayer, randomRoom, randomWeapon);
		for (Card card : deck){
			if (card.getCardName().equals(randomPlayer)){
				deck.remove(card);
				break;
			}
		}
		for (Card card : deck){
			if (card.getCardName().equals(randomRoom)){
				deck.remove(card);
				break;
			}
		}
		for (Card card : deck){
			if (card.getCardName().equals(randomWeapon)){
				deck.remove(card);
				break;
			}
		}
	}

	public ArrayList<String> getSolution() {
		ArrayList<String> solutionList = new ArrayList<String>();
		solutionList.add(solution.person);
		solutionList.add(solution.room);
		solutionList.add(solution.weapon);
		return solutionList;
	}

	public Set<BoardCell> getAdjList(int x, int y){
		BoardCell temp = getCellAt(x,y);

		return adjMatrix.get(temp);
	}

	public Set<BoardCell> getTargets(){
		finalTargets = new HashSet<BoardCell>(targets);
		targets.clear();
		return finalTargets;
	}

	public void setConfigFiles(String board, String legend) {
		boardConfigFile = board;
		roomConfigFile = legend;
	}

	public void setConfigFiles(String board, String legend, String players, String weapons) {
		boardConfigFile = board;
		roomConfigFile = legend;
		playerConfigFile = players;
		weaponConfigFile = weapons;
	}

	public Map<Character, String> getLegend() {
		return legend; 
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numCols;
	}

	public BoardCell getCellAt(int x, int y) {
		return board[x][y];
	}

	public ArrayList<Player> getPlayers(){
		return players;
	}

	public Player getHumanPlayer(){
		for (Player player : players){
			if (player instanceof HumanPlayer){
				return player;
			}
		}
		return null;
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}

	public ArrayList<String> getWeapons() {
		return weapons;
	}

	public ArrayList<String> getRooms()
	{
		return rooms;
	}

	public ArrayList<Card> getPlayerCards() {
		return playerCards;
	}

	public ArrayList<Card> getWeaponCards() {
		return weaponCards;
	}
}
