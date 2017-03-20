package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;

public class gameActionTests {

	private static Board board;
	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueCSV.csv", "Legend.txt", "ThreePlayers.txt", "Weapons.txt");
		board.initialize();
		board.initializeGameplay();
	}
	
	@Test
	public void selectTargetTest() { // for computer players
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
			if (!targetsSelected.contains(temp)){
				nonDoorSelected++;
			}
			targetsSelected.add(temp);			
		}
		assertEquals(nonDoorSelected, 8);
		
		// test random target locations like they were never in the room
		moveMe.setLastRoom('W');
		assertTrue(board.getCellAt(14, 17) == moveMe.selectTarget(targets) || board.getCellAt(14, 16) == moveMe.selectTarget(targets));
		
		// test random target location without possible doors
		moveMe.setLocation(board.getCellAt(14, 12));
		board.calcTargets(14, 12, 2);
		targets.clear();
		targets = board.getTargets();
		int selected = 0;
		targetsSelected.clear();
		for (int i = 0; i < 100; i++){
			BoardCell temp = moveMe.selectTarget(targets);
			if (!targetsSelected.contains(temp)){
				selected++;
			}
			targetsSelected.add(temp);
		}
		assertEquals(selected, 6);
	}
	
	@Test
	public void makeAccusationTest(){
//		ArrayList<Player> players = board.getPlayers();
//		ComputerPlayer detective = (ComputerPlayer) players.get(1);
		ArrayList<String> solutionList = board.getSolution();
		Solution solution = new Solution(solutionList.get(0), solutionList.get(1), solutionList.get(2));
		assertTrue(board.checkAccusation(solution)); // correct solution
		
		// wrong person
		String solutionPerson = solution.person;
		solution.person = "wrong";
		assertFalse(board.checkAccusation(solution)); 
		solution.person = solutionPerson;
		
		// wrong room
		String solutionRoom = solution.room;
		solution.person = "wrong";
		assertFalse(board.checkAccusation(solution));
		solution.room = solutionRoom;
		
		// wrong weapon
		String solutionWeapon = solution.weapon;
		solution.person = "wrong";
		assertFalse(board.checkAccusation(solution));
		solution.room = solutionWeapon;
	}
	
	@Test
	public void createSuggestionTets(){
		ArrayList<Card> players = board.getPlayerCards();
		ArrayList<Card> weapons = board.getWeaponCards();
		ComputerPlayer detective = (ComputerPlayer) board.getPlayers().get(1);
		Map<Character, String> legend = board.getLegend();
		detective.setLocation(board.getCellAt(6, 15));
		Solution suggestion = detective.createSuggestion(board.getCellAt(6, 15), players, weapons, legend);
		
		// checks that room is correct location
		assertEquals(suggestion.room, legend.get(board.getCellAt(6, 15).getInitial()));
		
		// check for random selection of weapons
		int differentWeapons = 0;
		Set<String> weaponsSelected = new HashSet<String>();
		for (int i = 0; i < 100; i++){
			suggestion = detective.createSuggestion(board.getCellAt(6, 15), players, weapons, legend);
			if (!weaponsSelected.contains(suggestion.weapon)){
				differentWeapons++;
			}
			weaponsSelected.add(suggestion.weapon);			
		}
		assertEquals(differentWeapons, 2);
		
		// check for random selection of sneople
				int differentPeople = 0;
				Set<String> sneopleSelected = new HashSet<String>();
				for (int i = 0; i < 100; i++){
					suggestion = detective.createSuggestion(board.getCellAt(6, 15), players, weapons, legend);
					if (!sneopleSelected.contains(suggestion.weapon)){
						differentPeople++;
					}
					sneopleSelected.add(suggestion.weapon);			
				}
				assertEquals(differentPeople, 2);
		
		// check for only one weapon
		detective.addToSeenWeapons(new Card("knife", CardType.WEAPON));
		suggestion = detective.createSuggestion(board.getCellAt(6, 15), players, weapons, legend);
		assertEquals(suggestion.weapon, "pool noodle"); // last weapon left
		
		// check for only one person
		detective.addToSeenPeople(new Card("human", CardType.PERSON));
		detective.addToSeenPeople(new Card("comp1", CardType.PERSON));
		suggestion = detective.createSuggestion(board.getCellAt(6, 15), players, weapons, legend);
		assertEquals(suggestion.person, "comp2"); // only the last person left
	}

	@Test
	public void disproveSuggestionTests(){
		ArrayList<Player> players = board.getPlayers();
		players.get(0).clearHand();
		players.get(0).addToHand(new Card("human", CardType.PERSON));
		players.get(0).addToHand(new Card("pool noodle", CardType.WEAPON));
		
		// one matching card
		assertEquals(players.get(0).disproveSuggestion(new Solution("human", "BOGGIE ROOM", "Chop sticks")), players.get(0).getHand().get(0));
		
		// more than one matching card
		int differentCards = 0;
		Set<Card> CardsSelected = new HashSet<Card>();
		for (int i = 0; i < 100; i++){
			Card temp = players.get(0).disproveSuggestion(new Solution("human", "BOGGIE ROOM", "pool noodle"));
			if (!CardsSelected.contains(temp)){
				differentCards++;
			}
			CardsSelected.add(temp);			
		}
		assertEquals(differentCards, 2);
		players.get(0).disproveSuggestion(new Solution("human", "BOGGIE ROOM", "pool noodle"));
		
		// no matching cards
		assertEquals(players.get(0).disproveSuggestion(new Solution("King Phillip II", "BOGGIE ROOM", "Chop sticks")), null);
	}
	
	@Test
	public void handleSuggestionTest(){
		ArrayList<Player> players = board.getPlayers();
		
		// no one can disprove
		assertEquals(board.handleSuggestion(0, new Solution("King Phillip II", "BOGGIE ROOM", "Chop sticks")), -1);
		
		// only accusing player can disprove
		Card card = players.get(0).getHand().get(0);
		if (card.getType() == CardType.PERSON){
			assertEquals(board.handleSuggestion(0, new Solution(card.getCardName(), "BOGGIE ROOM", "Chop sticks")), -1);
		} else if (card.getType() == CardType.ROOM){
			assertEquals(board.handleSuggestion(0, new Solution("King Phillip II", card.getCardName(), "Chop sticks")), -1);
		} else assertEquals(board.handleSuggestion(0, new Solution("King Phillip II", "BOGGIE ROOM", card.getCardName())), -1);
		
		// Only human can disprove
		int humanIndex = 0, compIndex = 0;
		for (int i = 0; i < players.size(); i++){
			if (players.get(i).getPlayerName().equals("human")) humanIndex = i;
			if (players.get(i).getPlayerName().equals("comp1")) compIndex = i;
		}
		card = players.get(humanIndex).getHand().get(0);
		if (card.getType() == CardType.PERSON){
			assertEquals(board.handleSuggestion(compIndex, new Solution(card.getCardName(), "BOGGIE ROOM", "Chop sticks")), humanIndex);
		} else if (card.getType() == CardType.ROOM){
			assertEquals(board.handleSuggestion(compIndex, new Solution("King Phillip II", card.getCardName(), "Chop sticks")), humanIndex);
		} else assertEquals(board.handleSuggestion(compIndex, new Solution("King Phillip II", "BOGGIE ROOM", card.getCardName())), humanIndex);
		
		// Only human can disprove, but human is accuser
		if (card.getType() == CardType.PERSON){
			assertEquals(board.handleSuggestion(humanIndex, new Solution(card.getCardName(), "BOGGIE ROOM", "Chop sticks")), -1);
		} else if (card.getType() == CardType.ROOM){
			assertEquals(board.handleSuggestion(humanIndex, new Solution("King Phillip II", card.getCardName(), "Chop sticks")), -1);
		} else assertEquals(board.handleSuggestion(humanIndex, new Solution("King Phillip II", "BOGGIE ROOM", card.getCardName())), -1);
		
		// two players can disprove, but the first returns 
		card = players.get(0).getHand().get(0);
		Card card2 = new Card("", CardType.PERSON);
		CardType cardType = card.getType();
		for (Card tempCard : players.get(1).getHand()){
			if (cardType == tempCard.getType()) continue;
			else {
				card2 = tempCard;
				break;
			}
		}
		if (card.getType() == CardType.PERSON && card2.getType() == CardType.ROOM){
			assertEquals(board.handleSuggestion(compIndex, new Solution(card.getCardName(), card2.getCardName(), "Chop sticks")), 0);
		} else if (card.getType() == CardType.PERSON && card2.getType() == CardType.WEAPON){
			assertEquals(board.handleSuggestion(compIndex, new Solution(card.getCardName(), "BOGGIE ROOM" , card2.getCardName())), 0);
		} else if (card.getType() == CardType.ROOM && card2.getType() == CardType.PERSON){
			assertEquals(board.handleSuggestion(compIndex, new Solution(card2.getCardName(), card.getCardName(), "Chop sticks")), 0);
		} else if (card.getType() == CardType.ROOM && card2.getType() == CardType.WEAPON){
			assertEquals(board.handleSuggestion(compIndex, new Solution("King Phillip II" , card.getCardName() , card2.getCardName())), 0);
		} else if (card.getType() == CardType.WEAPON && card2.getType() == CardType.PERSON){
			assertEquals(board.handleSuggestion(compIndex, new Solution(card2.getCardName(), "BOGGIE ROOM" , card.getCardName())), 0);
		} else {
			assertEquals(board.handleSuggestion(compIndex, new Solution("King Phillip II", card2.getCardName() , card.getCardName())), 0);
		}
	}
}
