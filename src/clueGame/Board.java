package clueGame;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.*;

public class Board {
	private int numRows;
	private int numCols;
	public static final int MAX_BOARD_SIZE = 50;
	private BoardCell[][] board;
	private BoardCell[][] tempBoard;
	private Map<Character, String> legend;
	private Map<Character, Boolean> roomHasCard;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	private String playerConfigFile;
	private String weaponsConfigFile;
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	private Set<BoardCell> finalTargets;
	private ArrayList<Player> players;
	private ArrayList<Card> deck;

	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// ctor is private to ensure only one can be created
	private Board() {
		legend = new HashMap<Character, String>();
		roomHasCard = new HashMap<Character, Boolean>();
		targets = new HashSet<BoardCell>();
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
	}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	public void initialize(){
		targets.clear();
	
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
	
	public void initializeGameplay(){
		players = new ArrayList<Player>();
		deck = new ArrayList<Card>();
		try {
			loadPlayerConfig();
			loadWeaponConfig();
		} catch (FileNotFoundException e){
			System.out.println("File not Found");
		}
		makeDeck();
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
			if (words[2].equals("Card")) roomHasCard.put(c, true);
			else roomHasCard.put(c, false);
			if ((!words[2].equals("Card")) && (!words[2].equals("Other"))){
				throw new BadConfigFormatException("ERROR:Legend not properly formated");
			}
		}
		in.close();
	}

	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException{
		tempBoard = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		FileReader read = new FileReader(boardConfigFile);
		Scanner in = new Scanner(read);
		int i = 0;
		int j = 0;
		int count = 0;
		ArrayList<Integer> list = new ArrayList<Integer>();
		while (in.hasNextLine()){
			String str = in.nextLine();

			String[] temp = str.split(","); 

			for(String s: temp){
				if (!legend.containsKey(s.charAt(0))){
					throw new BadConfigFormatException("ERROR:Room not included in legend");
				}
				tempBoard[i][j] = new BoardCell(i,j,s); 
				j++;
			}
			j = 0;
			i++;

		}
		int row = 0;
		int col = 0;
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
				col++;
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
		numCols = col/numRows;

		board = new BoardCell[numRows][numCols];
		for (int p = 0; p < numRows; p++){
			for (int y = 0; y < numCols; y++){
				board[p][y] = tempBoard[p][y];
			}
		}
		in.close();
	}

	public void loadPlayerConfig() throws FileNotFoundException{
		FileReader read = new FileReader(playerConfigFile);
		Scanner in = new Scanner(read);
		while (in.hasNextLine()){ // has more players
			String str = in.nextLine();
			String[] words = str.split(",\\s");
			//for (String i : words) System.out.println(i);
			Color color;
			try {
				Field field = Class.forName("java.awt.Color").getField(words[1].trim());
				color = (Color)field.get(null);
			} catch (Exception e){
				color = null; // not defined
			}
			int row = Integer.parseInt(words[2]);
			int column = Integer.parseInt(words[3]);
			players.add(new Player(words[0], color, row, column));
		}
		in.close();
	}
	
	private void loadWeaponConfig() {
		// TODO Auto-generated method stub
	}
	
	private void makeDeck() {
		// TODO Auto-generated method stub
	}
	
	public void calcAdjacencies() {

		//Set<BoardCell> list = new HashSet<BoardCell>();

		BoardCell key = new BoardCell(0,0," ");
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				//	list.clear();
				key = getCellAt(i, j);
				key.getInitial();
				//System.out.println(key.getInitial());
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
						//adjMatrix.put(key, list);
					}
					adjMatrix.put(key, list);
				}
			}
		}
		//System.out.println(cell);
	}
	public void calcTargets(int x, int y, int pathLength){
		Set<BoardCell> temp = new HashSet<BoardCell>();
		BoardCell tempKey = new BoardCell(0,0," ");
		tempKey = getCellAt(x,y);
		temp = adjMatrix.get(tempKey);
	
		BoardCell e = new BoardCell(0,0," ");
		e = getCellAt(x,y);
		visited.add(e);

		for(BoardCell current: temp) {
			int j = current.getCol();
			int i = current.getRow();
			if(!visited.contains(getCellAt(i, j))) {
				visited.add(current);

				if(current.isDoorway()) {
					targets.add(current);
					visited.remove(current);
				}
				if(pathLength == 1) {
					if(!targets.contains(getCellAt(i, j))) {

						targets.add(current);
						visited.remove(current);

					}
				}
				else {
					calcTargets(current.getRow(), current.getCol(), pathLength -1);
				}
				visited.remove(current);
			}
		}


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
		weaponsConfigFile = weapons;
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
	public ArrayList<Card> getDeck() {
		return deck;
	}
}
