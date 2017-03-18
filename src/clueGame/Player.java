package clueGame;

import java.awt.Color;
import java.util.ArrayList;

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
	public void addToHand(Card card){
		hand.add(card);
	}
	public void addToSeenPeople(Card card){
		seenPeople.add(card);
	}
	public void addToSeenRooms(Card card){
		seenRooms.add(card);
	}
	public void addToSeenWeapons(Card card){
		seenWeapons.add(card);
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
		return "Player [playerName=" + playerName + ", row=" + row + ", column=" + column + ", color=" + color + "]";
	}
	public Solution accuse(String person, String room, String weapon){
		Solution solution = new Solution(person, room, weapon);
		return solution;
	}
}
