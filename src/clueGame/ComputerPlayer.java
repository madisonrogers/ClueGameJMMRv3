package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	// keep track of the computer player's last visited room to make movement smart
	//private char lastRoom; 
	
	public ComputerPlayer(String playerName, Color color, int row, int column) {
		super(playerName, color, row, column);
		lastRoom = 'W';
	}
	
	public void makeMove(Set<BoardCell> targets){
		BoardCell endLocation = selectTarget(targets);
		for (BoardCell target : targets){
			target.setHighlight(false);
		}
		this.setLocation(endLocation);
		turnOver = true;
	}
	
	// smart location picker from targets
	public BoardCell selectTarget(Set<BoardCell> targets){
		for (BoardCell cell : targets){
			if (cell.isDoorway() && (cell.getInitial() != lastRoom)){
				return cell;
			}
		}
		int random = new Random().nextInt(targets.size());
		int i = 0;
		for (BoardCell cell : targets){
			if (i == random)
				return cell;
			i++;
		}
		return null;
	}
	
	// this should override the player accusation method
	public Solution makeAccusation(){
		turnOver = true;
		return accusation;
	}

	// smart suggestion creator
	public Solution createSuggestion(BoardCell location, ArrayList<Card> players, ArrayList<Card> weapons, Map<Character, String> legend){
		ArrayList<Card> validPeople = new ArrayList<Card>(players);
		ArrayList<Card> validWeapons = new ArrayList<Card>(weapons); 

		for (int i = 0; i < seenPeople.size(); i++){
			for (int j = 0; j < validPeople.size(); j++){
				if (validPeople.get(j).getCardName().equals(seenPeople.get(i).getCardName())){
					validPeople.remove(validPeople.get(j));
					break;
				}
			}
		}
		for (int i = 0; i < seenWeapons.size(); i++){
			for (int j = 0; j < validWeapons.size(); j++){
				if (validWeapons.get(j).getCardName().equals(seenWeapons.get(i).getCardName())){
					validWeapons.remove(validWeapons.get(j));
					break;
				}
			}
		}
		
//		System.out.println(validPeople);
//		System.out.println(validWeapons);
//		System.out.println(legend.get(location.getInitial()));
		
		
		return new Solution(validPeople.get(new Random().nextInt(validPeople.size())).getCardName(),
				legend.get(location.getInitial()), 
				validWeapons.get(new Random().nextInt(validWeapons.size())).getCardName());
	}
}
