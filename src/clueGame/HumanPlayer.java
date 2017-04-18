package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class HumanPlayer extends Player {
	private Solution suggestion;

	public HumanPlayer(String playerName, Color color, int row, int column) {
		super(playerName, color, row, column);
		// TODO Auto-generated constructor stub
	}
	
	public void makeMove(Set<BoardCell> targets){
		turnOver = true;
	}

	@Override
	public Solution createSuggestion(BoardCell location, ArrayList<Card> players, ArrayList<Card> weapons, Map<Character, String> legend) {
		System.out.println("Made it----");
		GuessDialog suggestionBox = new GuessDialog(true);
		suggestion = suggestionBox.getSolution();
		return suggestionBox.getSolution();
	}

	public Solution getSuggestion() {
		return suggestion;
	}
	
}
