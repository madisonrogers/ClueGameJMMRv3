package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ComputerPlayer;
import clueGame.Player;

public class gameActionTests {

	private static Board board;
	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueCSV.csv", "legend.txt", "ThreePlayers.txt", "Weapons.txt");
		board.initialize();
		board.initializeGameplay();
	}
	
	@Test
	public void SelectTargetTest() {
		// set this boy up
		board.calcTargets(16, 16, 3);
		Set<BoardCell> targets= board.getTargets();	
		ArrayList<Player> players = board.getPlayers();
		ComputerPlayer moveMe = (ComputerPlayer) players.get(1);
		moveMe.setLocation(board.getCellAt(16, 16));
		
		// test random target locations like they were in the room last move
		moveMe.setLastRoom('L');
		int nonDoorSelected = 0;
		Set<BoardCell> targetsSelected = new HashSet<BoardCell>();
		for (int i = 0; i < 100; i++){
			BoardCell temp = moveMe.selectTarget(targets);
			if (!targetsSelected.contains(temp));
				nonDoorSelected++;
			targetsSelected.add(temp);
			
		}
		assertEquals(nonDoorSelected, 6);
		
		// test random target locations like they were never in the room
		moveMe.setLastRoom('W');
		assertEquals(new BoardCell(14, 16, "L"), moveMe.selectTarget(targets));
		
		// test random target location without possible doors
		moveMe.setLocation(board.getCellAt(14, 12));
		board.calcTargets(14, 12, 2);
		targets.clear();
		targets = board.getTargets();
		int selected = 0;
		targetsSelected.clear();
		for (int i = 0; i < 100; i++){
			BoardCell temp = moveMe.selectTarget(targets);
			if (!targetsSelected.contains(temp));
				selected++;
			targetsSelected.add(temp);
		}
		assertEquals(selected, 6);
	}

}
