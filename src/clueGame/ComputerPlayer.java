package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	private char lastRoom;
	public ComputerPlayer(String playerName, Color color, int row, int column) {
		super(playerName, color, row, column);
		lastRoom = 'W';
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
	public Solution makeAccusation(){
		//		String randomPlayer = players.get(new Random().nextInt(players.size())).getPlayerName();
		//		String randomRoom = rooms.get(new Random().nextInt(rooms.size()));
		//		String randomWeapon = weapons.get(new Random().nextInt(weapons.size()));
		Solution accusation = new Solution("", "", "");
		return accusation;
	}

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
		//		System.out.println(seenWeapons);
		//		System.out.println(validWeapons);
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
