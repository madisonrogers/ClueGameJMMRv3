package clueGame;

import java.awt.Color;
import java.util.ArrayList;
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
	
	public Solution createSuggestion(BoardCell location, ArrayList<Player> players, ArrayList<String> weapons){
		return new Solution("", "", "");
	}
	
	public char getLastRoom() {
		return lastRoom;
	}
	public void setLastRoom(char lastRoom) {
		this.lastRoom = lastRoom;
	}
}
