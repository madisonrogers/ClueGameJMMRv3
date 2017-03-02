package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Map;

import org.junit.*;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
import clueGame.BadConfigFormatException;


public class BoardTests {
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 21;
	public static final int NUM_COLS = 20;

	private static Board board;
	@BeforeClass
	public static void setUp() throws FileNotFoundException {
		board = Board.getInstance();
		board.setConfigFiles("ClueCSV.csv", "Legend.txt");
		board.initialize(NUM_ROWS, NUM_COLS);
	}

	@Test
	public void testRooms() {
		Map<Character, String> legend = board.getLegend();
		assertEquals(LEGEND_SIZE, legend.size());
		assertEquals("BedRoom", legend.get('B'));
		assertEquals("Pool", legend.get('P'));
		assertEquals("Wine Cellar", legend.get('C'));
		assertEquals("Dining room", legend.get('D'));
		assertEquals("Closet", legend.get('X'));
	}
	@Test
	public void testBoardDimensions() {
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLS, board.getNumColumns());
	}
	@Test
	public void testFourDoorDirections() {
		BoardCell room = board.getCellAt(1, 15);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getCellAt(6, 17);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		room = board.getCellAt(2, 10);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		room = board.getCellAt(18, 10);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		room = board.getCellAt(18, 18);
		assertFalse(room.isDoorway());
		BoardCell cell = board.getCellAt(12, 18);
		assertFalse(cell.isDoorway());
	}
	@Test
	public void testNumberOfDoorways() {
		int numDoors = 0;
		for(int row = 0; row<board.getNumRows();row++) {
			for(int col = 0; col<board.getNumColumns(); col++) {
				BoardCell cell = board.getCellAt(row, col);
				if (cell.isDoorway()) {
					numDoors++;
				}
			}
			Assert.assertEquals(16, numDoors);
		}
	}
	@Test
	public void testRoomInitials() {
		assertEquals('B', board.getCellAt(0, 0).getInitial());
		assertEquals('P', board.getCellAt(5, 0).getInitial());
		assertEquals('F', board.getCellAt(0, 14).getInitial());
		
		assertEquals('R', board.getCellAt(19, 20).getInitial());
		assertEquals('T', board.getCellAt(10, 20).getInitial());
		
		assertEquals('W', board.getCellAt(4, 16).getInitial());
		
		assertEquals('X', board.getCellAt(8, 11).getInitial());
	}



}
