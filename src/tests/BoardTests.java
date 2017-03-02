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
		board.initialize();
	}

	@Test
	public void testRooms() {
		Map<Character, String> legend = board.getLegend();

		assertEquals("Bedroom", legend.get('B'));
		assertEquals("Pool", legend.get('P'));
		assertEquals("Wine Cellar", legend.get('C'));
		assertEquals("Dining Room", legend.get('D'));
		assertEquals("Closet", legend.get('X'));
		assertEquals(LEGEND_SIZE, legend.size());

	}
	@Test
	public void testBoardDimensions() {
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLS, board.getNumColumns());
	}
	@Test
	public void testFourDoorDirections() {
		BoardCell room = board.getCellAt(15, 1);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getCellAt(17, 6);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		room = board.getCellAt(10, 2);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		room = board.getCellAt(10, 18);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		room = board.getCellAt(18, 18);
		assertFalse(room.isDoorway());
		BoardCell cell = board.getCellAt(18, 12);
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

		}
		Assert.assertEquals(14, numDoors);
	}
	@Test
	public void testRoomInitials() {
		assertEquals('B', board.getCellAt(0, 0).getInitial());
		assertEquals('P', board.getCellAt(0, 5).getInitial());
		assertEquals('F', board.getCellAt(14, 0).getInitial());

		assertEquals('R', board.getCellAt(20, 19).getInitial());
		assertEquals('T', board.getCellAt(20, 10).getInitial());

		assertEquals('W', board.getCellAt(16, 4).getInitial());

		assertEquals('X', board.getCellAt(11, 8).getInitial());
	}



}
