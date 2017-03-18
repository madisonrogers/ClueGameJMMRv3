package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public class Player {
	private String playerName;
	private int row, column;
	private Color color;
	private ArrayList<Card> hand;
	
	public Player(String playerName, Color color, int row, int column) {
		super();
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
		hand = new ArrayList<Card>();
	}
	public void addToHand(Card card){
		hand.add(card);
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
