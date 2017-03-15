package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.Color;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;

public class GameSetupTests {
	
	private static Board board;
	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueCSV.csv", "legend.txt", "ThreePlayers.txt", "Weapons.txt");
		board.initialize();
		board.initializeGameplay();
	}

	@Test
	public void loadingPeopleTest() {
		// 3 player config file
		ArrayList<Player> players = board.getPlayers();
		assertEquals(players.get(0).getPlayerName(), "human");
		assertEquals(players.get(0).getColor(), Color.RED);
		assertEquals(players.get(0).getRow(), 4);
		assertEquals(players.get(0).getColumn(), 0);
		
		assertEquals(players.get(1).getPlayerName(), "comp1");
		assertEquals(players.get(1).getColor(), Color.BLUE);
		assertEquals(players.get(1).getRow(), 5);
		assertEquals(players.get(1).getColumn(), 0);
		
		assertEquals(players.get(2).getPlayerName(), "comp2");
		assertEquals(players.get(2).getColor(), Color.YELLOW);
		assertEquals(players.get(2).getRow(), 12);
		assertEquals(players.get(2).getColumn(), 0);
	}
	
	@Test
	public void loadDeckTest(){
		ArrayList<Card> deck = board.getDeck();
		// check deck size
		assertEquals(deck.size(), 13);
		
		Set<String> cardNames = new HashSet<String>();
		Set<CardType> cardTypes = new HashSet<CardType>();
		for (Card card : deck){
			cardNames.add(card.getCardName());
			cardTypes.add(card.getType());
		}
		
		// checks for certain card names in deck
		assertTrue(cardNames.contains("human"));
		assertTrue(cardNames.contains("Bedroom"));
		assertTrue(cardNames.contains("knife"));
		
		// checks for certain card types in deck
		assertTrue(cardTypes.contains(CardType.PERSON));
		assertTrue(cardTypes.contains(CardType.ROOM));
		assertTrue(cardTypes.contains(CardType.WEAPON));
	}

	@Test
	public void dealDeckTest(){
		ArrayList<Player> players = board.getPlayers();
		// check hands are the right size for a deal
		assertEquals(players.get(0).getHand().size(), 5);
		assertEquals(players.get(1).getHand().size(), 4);
		assertEquals(players.get(2).getHand().size(), 4);
		
		// check cards are not dealt twice
		ArrayList<Card> cards = new ArrayList<Card>();
		boolean cardAppearsAgain = false;
		for (Player player : players){
			for (Card card : player.getHand()){
				if (cards.contains(card)) cardAppearsAgain = true;
				else cards.add(card);
			}
		}
		assertFalse(cardAppearsAgain);
		
		// check that deal is somewhat even
		boolean unevenCardDispersion = false;
		for (int i = 0; i < players.size() - 1; i++){
			if (Math.abs(players.get(i).getHand().size() - players.get(i+1).getHand().size()) > 1){
				unevenCardDispersion = true;
			}
		}
		assertFalse(unevenCardDispersion);
	}
}
