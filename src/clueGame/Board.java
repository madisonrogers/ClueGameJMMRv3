package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;


public class Board {
	private int numRows;
	private int numCols;
	public static final int MAX_BOARD_SIZE = 50;
	private BoardCell[][] board;
	private BoardCell[][] tempBoard;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;

	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// ctor is private to ensure only one can be created
	private Board() {
		legend = new HashMap<Character, String>();
	}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	public void initialize(){


		try{
			loadRoomConfig();
			loadBoardConfig();
			calcAdjacencies();
		}
		catch(FileNotFoundException ex){
			System.out.println("File not Found");
		}
		catch(BadConfigFormatException ex){
			ex.getMessage();
		}
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

			if ((!words[2].equals("Card")) && (!words[2].equals("Other"))){
				throw new BadConfigFormatException();
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
					throw new BadConfigFormatException();
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
				throw new BadConfigFormatException();
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

	public void calcAdjacencies() {

	}

	public void CalcTargets(BoardCell cell, int pathLength) {

	}

	public void setConfigFiles(String board, String legend) {
		boardConfigFile = board;
		roomConfigFile = legend;
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

}
