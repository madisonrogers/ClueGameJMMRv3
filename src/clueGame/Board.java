package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;


public class Board {
	private int numRows;
	private int numCols;
	public static final int MAX_BOARD_SIZE = 50;
	private BoardCell[][] board;
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

	public void initialize( ) throws FileNotFoundException{

	
		try{
			loadRoomConfig();
			loadBoardConfig();
		}
		catch(FileNotFoundException ex){
			System.out.println("File not Found");
		}
	}

	public void loadRoomConfig() throws FileNotFoundException{
		FileReader read = new FileReader(roomConfigFile);
		Scanner in = new Scanner(read);
		while (in.hasNextLine()){ //maps legend
			String str = in.nextLine();
			String[] words = str.split(",\\s");
			Character c = new Character(words[0].charAt(0));
			legend.put(c, words[1]);
		}



		in.close();
	}

	public void loadBoardConfig() throws FileNotFoundException{
	
		FileReader read = new FileReader(boardConfigFile);
		Scanner in = new Scanner(read);
		int i = 0;
		int j = 0;
		while (in.hasNextLine()){
			String str = in.nextLine();
			String[] temp = str.split(","); //maybe allocate space
			for(String s: temp){
				board[i][j] = new BoardCell(i,j,s);
				j++;
			}
			j = 0;
			i++;		
		}

		numRows = board.length;
		numCols = board[0].length;
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

		return null; 
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumCols() {
		return numCols;
	}

	public BoardCell getCellAt(int x, int y) {
		return null;

	}

}
