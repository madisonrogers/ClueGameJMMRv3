package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import experiment.BoardCell;
import experiment.IntBoard;

public class IntBoardTest {
	IntBoard board;
	@Before
	public void BuildBoard(){
		board = new IntBoard();
	}

	@Test
	public void testAdjacency0_0() {
		BoardCell cell = new BoardCell();
		cell.setCol(0);
		cell.setRow(0);
		board.calcAdjacencies();
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getAdjCell(1, 0, 0, 0)));
		assertTrue(testList.contains(board.getAdjCell(0, 1, 0, 0)));
		assertEquals(2, testList.size());
	}
	@Test
	public void testAdjacency3_3() {
		BoardCell cell = new BoardCell();
		cell.setCol(3);
		cell.setRow(3);
		board.calcAdjacencies();
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getAdjCell(2, 3, 3, 3)));
		assertTrue(testList.contains(board.getAdjCell(3, 2, 3, 3)));
		assertEquals(2, testList.size());
	}
	@Test
	public void testAdjacency1_3() {
		BoardCell cell = new BoardCell();
		cell.setCol(1);
		cell.setRow(3);
		board.calcAdjacencies();
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getAdjCell(3, 0, 3, 1)));
		assertTrue(testList.contains(board.getAdjCell(2, 1, 3, 1)));
		assertTrue(testList.contains(board.getAdjCell(3, 2, 3, 1)));
		assertEquals(3, testList.size());
	}
	@Test
	public void testAdjacency3_0() {
		BoardCell cell = new BoardCell();
		cell.setCol(3);
		cell.setRow(0);
		board.calcAdjacencies();
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getAdjCell(0, 2, 0, 3)));
		assertTrue(testList.contains(board.getAdjCell(1, 3, 0, 3)));	
		assertEquals(2, testList.size());
	}
	@Test
	public void testAdjacency1_1() {
		BoardCell cell = new BoardCell();
		cell.setCol(1);
		cell.setRow(1);
		board.calcAdjacencies();
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getAdjCell(0, 1, 1, 1)));
		assertTrue(testList.contains(board.getAdjCell(1, 0, 1, 1)));
		assertTrue(testList.contains(board.getAdjCell(2, 1, 1, 1)));
		assertTrue(testList.contains(board.getAdjCell(1, 2, 1, 1)));
		assertEquals(4, testList.size());
	}
	@Test
	public void testAdjacency2_2() {
		BoardCell cell = new BoardCell();
		cell.setCol(2);
		cell.setRow(2);
		board.calcAdjacencies();		
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getAdjCell(1, 2, 2, 2)));
		assertTrue(testList.contains(board.getAdjCell(3, 2, 2, 2)));
		assertTrue(testList.contains(board.getAdjCell(2, 1, 2, 2)));
		assertTrue(testList.contains(board.getAdjCell(2, 3, 2, 2)));
		assertEquals(4, testList.size());
	}
	@Test
	public void testTarget1(){
		BoardCell cell = new BoardCell();
		cell.setCol(0);
		cell.setRow(0);
		board.calcAdjacencies();	
		board.calcTargets(cell, 3);
		Set<BoardCell> targets = board.getTargets(cell);
		
		assertTrue(targets.contains(board.getTarCell(0, 3)));
		assertTrue(targets.contains(board.getTarCell(1, 2)));
		assertTrue(targets.contains(board.getTarCell(1, 0)));
		assertTrue(targets.contains(board.getTarCell(2, 1)));
		assertTrue(targets.contains(board.getTarCell(3, 0)));
		assertTrue(targets.contains(board.getTarCell(0, 1)));
		assertEquals(6, targets.size());
	}
	@Test
	public void testTarget2(){
		BoardCell cell = new BoardCell();
		cell.setCol(2);
		cell.setRow(3);
		board.calcAdjacencies();
		board.calcTargets(cell, 4);
		Set<BoardCell> targets = board.getTargets(cell);
		
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getTarCell(1, 0)));
		assertTrue(targets.contains(board.getTarCell(3, 0)));
		assertTrue(targets.contains(board.getTarCell(0, 1)));
		assertTrue(targets.contains(board.getTarCell(2, 1)));
		assertTrue(targets.contains(board.getTarCell(1, 2)));
		assertTrue(targets.contains(board.getTarCell(0, 3)));
		assertTrue(targets.contains(board.getTarCell(2, 3)));
	}
	@Test
	public void testTarget3(){
		BoardCell cell = new BoardCell();
		cell.setCol(2);
		cell.setRow(1);
		board.calcAdjacencies();
		board.calcTargets(cell, 2);
		Set<BoardCell>targets = board.getTargets(cell);
		
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getTarCell(3, 2)));
		assertTrue(targets.contains(board.getTarCell(1, 0)));
		assertTrue(targets.contains(board.getTarCell(0, 1)));
		assertTrue(targets.contains(board.getTarCell(2, 1)));
		assertTrue(targets.contains(board.getTarCell(2, 3)));
		assertTrue(targets.contains(board.getTarCell(0, 3)));

	}
	@Test
	public void testTarget4(){
		BoardCell cell = new BoardCell();
		cell.setCol(3);
		cell.setRow(0);
		board.calcAdjacencies();
		board.calcTargets(cell, 2);
		Set<BoardCell> targets = board.getTargets(cell);
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getTarCell(0, 1)));
		assertTrue(targets.contains(board.getTarCell(2, 3)));
		assertTrue(targets.contains(board.getTarCell(1, 2)));

	}
	@Test
	public void testTarget5(){
		BoardCell cell = new BoardCell();
		cell.setCol(1);
		cell.setRow(1);
		board.calcAdjacencies();
		board.calcTargets(cell, 2);
		Set<BoardCell> targets = board.getTargets(cell);
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getTarCell(0,0)));
		assertTrue(targets.contains(board.getTarCell(2, 0)));
		assertTrue(targets.contains(board.getTarCell(0, 2)));
		assertTrue(targets.contains(board.getTarCell(2,2)));
		assertTrue(targets.contains(board.getTarCell(1, 3)));
		assertTrue(targets.contains(board.getTarCell(3, 1)));

	}
	@Test
	public void testTarget6(){
		BoardCell cell = new BoardCell();
		cell.setCol(2);
		cell.setRow(2);
		board.calcAdjacencies();
		board.calcTargets(cell, 3);
		Set<BoardCell> targets = board.getTargets(cell);
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getTarCell(0, 1)));
		assertTrue(targets.contains(board.getTarCell(1, 0)));
		assertTrue(targets.contains(board.getTarCell(2, 1)));
		assertTrue(targets.contains(board.getTarCell(3, 0)));
		assertTrue(targets.contains(board.getTarCell(3, 2)));
		assertTrue(targets.contains(board.getTarCell(1, 2)));
		assertTrue(targets.contains(board.getTarCell(0, 3)));
		assertTrue(targets.contains(board.getTarCell(2, 3)));
	}

}
