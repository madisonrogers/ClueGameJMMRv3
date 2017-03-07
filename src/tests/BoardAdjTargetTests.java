package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTests {

	private static Board board;
	@Before
	public void setUp() {
		
		board = Board.getInstance();
		board.setConfigFiles("ClueCSV.csv", "legend.txt");	
		board.initialize();
	}

	@Test
	public void testAdjacenciesInsideRooms() //color bright yellow
	{
	
		Set<BoardCell> testList = board.getAdjList(0, 0);
		assertEquals(0, testList.size());
		
		testList = board.getAdjList(2, 12);
		assertEquals(0, testList.size());
		
		testList = board.getAdjList(5, 18);
		assertEquals(0, testList.size());
	
		testList = board.getAdjList(8, 1);
		assertEquals(0, testList.size());
		
		testList = board.getAdjList(12, 16);
		assertEquals(0, testList.size());
		
		testList = board.getAdjList(19, 8);
		assertEquals(0, testList.size());
	}

	
	@Test
	public void testAdjacencyRoomExit() //colored purple 
	//tests for exiting a door
	{
		
		Set<BoardCell> testList = board.getAdjList(4, 6);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(5, 6)));
	
		testList = board.getAdjList(5, 13);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(5, 14)));
		
		testList = board.getAdjList(10, 18);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(9, 18)));
	
		testList = board.getAdjList(17, 6);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(17, 5)));
	
		testList = board.getAdjList(14, 16);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(15, 16)));
		
	}
	

	@Test
	public void testAdjacencyDoorways() //colored green
	//tests for movement next to a door
	{
	
		Set<BoardCell> testList = board.getAdjList(3, 2);
		assertTrue(testList.contains(board.getCellAt(3, 1)));
		assertTrue(testList.contains(board.getCellAt(3, 3)));
		assertTrue(testList.contains(board.getCellAt(4, 2)));
		assertEquals(3, testList.size());
	
		testList = board.getAdjList(5, 6);
		assertTrue(testList.contains(board.getCellAt(4, 6)));
		assertTrue(testList.contains(board.getCellAt(5, 5)));
		assertTrue(testList.contains(board.getCellAt(5, 7)));
		assertTrue(testList.contains(board.getCellAt(6, 6)));
		assertEquals(4, testList.size());
		
		testList = board.getAdjList(9, 18);
		assertTrue(testList.contains(board.getCellAt(9, 19)));
		assertTrue(testList.contains(board.getCellAt(9, 17)));
		assertTrue(testList.contains(board.getCellAt(10, 18)));
		assertEquals(3, testList.size());
	
		testList = board.getAdjList(17, 5);
		assertTrue(testList.contains(board.getCellAt(17, 6)));
		assertTrue(testList.contains(board.getCellAt(16, 5)));
		assertTrue(testList.contains(board.getCellAt(17, 4)));	
		assertEquals(3, testList.size());
	}

	
	@Test
	public void testAdjacencyWalkways() //colored salmon 
	{
		
		Set<BoardCell> testList = board.getAdjList(1, 3);
		assertTrue(testList.contains(board.getCellAt(0, 3)));
		assertTrue(testList.contains(board.getCellAt(1, 4)));
		assertTrue(testList.contains(board.getCellAt(2, 3)));
		assertEquals(3, testList.size());
		

		testList = board.getAdjList(5, 10);
		assertTrue(testList.contains(board.getCellAt(4, 10)));
		assertTrue(testList.contains(board.getCellAt(6, 10)));
		assertTrue(testList.contains(board.getCellAt(5, 9)));
		assertEquals(3, testList.size());

		
		testList = board.getAdjList(7, 15);
		assertTrue(testList.contains(board.getCellAt(7, 14)));
		assertTrue(testList.contains(board.getCellAt(8, 15)));
		assertEquals(2, testList.size());

		
		testList = board.getAdjList(10,14);
		assertTrue(testList.contains(board.getCellAt(9, 14)));
		assertTrue(testList.contains(board.getCellAt(10, 13)));
		assertTrue(testList.contains(board.getCellAt(10, 15)));
		assertEquals(3, testList.size());
		
		
		testList = board.getAdjList(16, 15);
		assertTrue(testList.contains(board.getCellAt(15, 15)));
		assertTrue(testList.contains(board.getCellAt(16, 14)));
		assertTrue(testList.contains(board.getCellAt(16, 16)));
		assertEquals(3, testList.size());
		
	
		testList = board.getAdjList(18, 11);
		assertTrue(testList.contains(board.getCellAt(17, 11)));
		assertTrue(testList.contains(board.getCellAt(19, 11)));
		assertTrue(testList.contains(board.getCellAt(18, 12)));
		assertEquals(3, testList.size());

		testList = board.getAdjList(13, 1);
		assertTrue(testList.contains(board.getCellAt(13, 0)));
		assertTrue(testList.contains(board.getCellAt(13, 2)));
		assertTrue(testList.contains(board.getCellAt(12, 1)));
		assertEquals(3, testList.size());
	}
	
	

	@Test
	public void testTargetsOneStep() { //colored light blue
		board.calcTargets(11, 0, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(12, 0)));
		assertTrue(targets.contains(board.getCellAt(11, 1)));	
		
		board.calcTargets(20, 13, 1);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(20, 12)));
		assertTrue(targets.contains(board.getCellAt(20, 14)));	
		assertTrue(targets.contains(board.getCellAt(19, 13)));			
	}
	
	
	@Test
	public void testTargetsTwoSteps() { //also light blue, same spots
		board.calcTargets(11, 0, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(13, 0)));
		assertTrue(targets.contains(board.getCellAt(12, 1)));
		assertTrue(targets.contains(board.getCellAt(11, 2)));
		
		board.calcTargets(20, 13, 2);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCellAt(20, 11)));
		assertTrue(targets.contains(board.getCellAt(20, 15)));	
		assertTrue(targets.contains(board.getCellAt(19, 12)));	
		assertTrue(targets.contains(board.getCellAt(19, 14)));
		assertTrue(targets.contains(board.getCellAt(18, 13)));
	}
	
	
	@Test
	public void testTargetsFourSteps() {//also light blue, same spots
		board.calcTargets(11, 0, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCellAt(11, 2)));
		assertTrue(targets.contains(board.getCellAt(13, 2)));
		assertTrue(targets.contains(board.getCellAt(12, 1)));
		assertTrue(targets.contains(board.getCellAt(13, 0)));
		assertTrue(targets.contains(board.getCellAt(10, 2)));
		
		
		board.calcTargets(20, 13, 4);
		targets= board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCellAt(18, 11)));
		assertTrue(targets.contains(board.getCellAt(20, 11)));	
		assertTrue(targets.contains(board.getCellAt(17, 12)));	
		assertTrue(targets.contains(board.getCellAt(19, 12)));	
		assertTrue(targets.contains(board.getCellAt(16, 13)));
		assertTrue(targets.contains(board.getCellAt(18, 13)));	
		assertTrue(targets.contains(board.getCellAt(19, 14)));	
		assertTrue(targets.contains(board.getCellAt(19, 16)));
		assertTrue(targets.contains(board.getCellAt(20, 15)));
	}	
	


	@Test
	public void testTargetsSixSteps() {//also light blue, same spots
		board.calcTargets(11, 0, 6);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCellAt(10, 2)));
		assertTrue(targets.contains(board.getCellAt(11, 2)));	
		assertTrue(targets.contains(board.getCellAt(13, 2)));	
		assertTrue(targets.contains(board.getCellAt(15, 2)));	
		assertTrue(targets.contains(board.getCellAt(13, 0)));	
		assertTrue(targets.contains(board.getCellAt(12, 1)));	
		assertTrue(targets.contains(board.getCellAt(14, 3)));
		assertTrue(targets.contains(board.getCellAt(13, 4)));
	}	
	


	@Test 
	public void testTargetsIntoRoom() //colored light blue 
	{
		
		board.calcTargets(6, 7, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(7, targets.size());	
		assertTrue(targets.contains(board.getCellAt(4, 7))); //door	
		assertTrue(targets.contains(board.getCellAt(5, 6)));
		assertTrue(targets.contains(board.getCellAt(6, 5)));	
		assertTrue(targets.contains(board.getCellAt(7, 6)));
		assertTrue(targets.contains(board.getCellAt(7, 8)));
		assertTrue(targets.contains(board.getCellAt(5, 8)));
		assertTrue(targets.contains(board.getCellAt(6, 9)));
	}
	

	@Test
	public void testTargetsIntoRoomShortcut() //colored light blue 
	{
		board.calcTargets(16, 16, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(8, targets.size());	
		assertTrue(targets.contains(board.getCellAt(16, 13)));
		assertTrue(targets.contains(board.getCellAt(15, 14)));
		assertTrue(targets.contains(board.getCellAt(16, 15)));		
		assertTrue(targets.contains(board.getCellAt(15, 16)));
		assertTrue(targets.contains(board.getCellAt(14, 16))); //door	
		assertTrue(targets.contains(board.getCellAt(14, 17))); //door
		assertTrue(targets.contains(board.getCellAt(16, 17)));
		assertTrue(targets.contains(board.getCellAt(15, 18)));
		
		
	}

	
	@Test
	public void testRoomExit() //colored light blue 
	{
		
		board.calcTargets(15, 1, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(15, 2)));	
		
		board.calcTargets(15, 1, 2);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(14, 2)));
		assertTrue(targets.contains(board.getCellAt(15, 3)));
	}

}
