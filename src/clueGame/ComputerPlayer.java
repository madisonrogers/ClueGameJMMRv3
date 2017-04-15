package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	// keep track of the computer player's last visited room to make movement smart
	private char lastRoom; 
	
	public ComputerPlayer(String playerName, Color color, int row, int column) {
		super(playerName, color, row, column);
		lastRoom = 'W';
	}
	
	public void makeMove(Set<BoardCell> targets){
		BoardCell endLocation = selectTarget(targets);
		this.setLocation(endLocation);
		turnOver = true;
	}
	
	public Solution movedToRoom(BoardCell location, ArrayList<Card> players, ArrayList<Card> weapons, Map<Character, String> legend){
		return createSuggestion(location, players, weapons, legend);
	}
	
	// smart location picker from targets
	public BoardCell selectTarget(Set<BoardCell> targets){
		for (BoardCell cell : targets){
			if (cell.isDoorway() && cell.getInitial() != lastRoom){
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
		// TODO finish this function so it creates correct accusations
		Solution accusation = new Solution("", "", "");
		return accusation;
	}

	// smart suggestion creator
	public Solution createSuggestion(BoardCell location, ArrayList<Card> players, ArrayList<Card> weapons, Map<Character, String> legend){
		ArrayList<Card> validSneople = new ArrayList<Card>(players);
		ArrayList<Card> validWeapons = new ArrayList<Card>(weapons); 

		for (int i = 0; i < seenPeople.size(); i++){
			for (int j = 0; j < validSneople.size(); j++){
				if (validSneople.get(j).getCardName().equals(seenPeople.get(i).getCardName())){
					validSneople.remove(validSneople.get(j));
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
		return new Solution(validSneople.get(new Random().nextInt(validSneople.size())).getCardName(),
				legend.get(location.getInitial()), 
				validWeapons.get(new Random().nextInt(validWeapons.size())).getCardName());
	}

	public char getLastRoom() {
		return lastRoom;
	}
	
	public void setLastRoom(char lastRoom) {
		this.lastRoom = lastRoom;
	}
}
