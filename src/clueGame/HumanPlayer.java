package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class HumanPlayer extends Player {

	public HumanPlayer(String playerName, Color color, int row, int column) {
		super(playerName, color, row, column);
		// TODO Auto-generated constructor stub
	}
	
	public void makeMove(Set<BoardCell> targets){
		turnOver = false;
	}

	@Override
	public Solution createSuggestion(BoardCell location, ArrayList<Card> players, ArrayList<Card> weapons, Map<Character, String> legend) {
		// TODO show dialog for making a suggestion
		
		GuessDialog suggestionBox = new GuessDialog(true);
		
		return suggestionBox.getSolution();
	}
	
}
