package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Player {
	private String playerName;
	private int row, column;
	private Color color;
	private ArrayList<Card> hand;
	protected ArrayList<Card> seenPeople;
	protected ArrayList<Card> seenRooms; // might not be necessary
	protected ArrayList<Card> seenWeapons;
	
	public Player(String playerName, Color color, int row, int column) {
		super();
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
		seenPeople = new ArrayList<Card>();
		seenRooms = new ArrayList<Card>();
		seenWeapons = new ArrayList<Card>();
		hand = new ArrayList<Card>();
	}
	
	public Card disproveSuggestion(Solution suggestion){
		ArrayList<Card> similarCards = new ArrayList<Card>();
		
		// find all similar cards between the player's hand and the suggestion
		for (Card card : hand){
			if (suggestion.person.equals(card.getCardName())) similarCards.add(card);
			if (suggestion.room.equals(card.getCardName())) similarCards.add(card);
			if (suggestion.weapon.equals(card.getCardName())) similarCards.add(card);
		}
		
		if (similarCards.size() != 0){
			// pick a random card from the player's hand if more than one matches the suggestion
			Card returnMe = similarCards.get(new Random().nextInt(similarCards.size()));
			return returnMe;
		}
		return null;
	}
	
	// makes a dumb accusation
	public Solution accuse(String person, String room, String weapon){
		Solution solution = new Solution(person, room, weapon);
		return solution;
	}
	
	// the three methods below are for the computer player to make smart suggestions
	public void addToSeenPeople(Card card){
		seenPeople.add(card);
	}	
	public void addToSeenWeapons(Card card){
		seenWeapons.add(card);
	}
	// might not be necessary because a player can only suggest the room they are in
	public void addToSeenRooms(Card card){ 
		seenRooms.add(card);
	}
	
	public void addToHand(Card card){
		hand.add(card);
	}
	
	public void clearHand(){
		hand.clear();
	}
	
	public ArrayList<Card> getHand() {
		return hand;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setLocation(BoardCell newLocation){
		row = newLocation.getRow();
		column = newLocation.getCol();
	}
	
	@Override
	public String toString() {
		return "The " + color + " player, " + playerName + ", is at " + row + ", " + column;
	}
}
