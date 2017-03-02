package clueGame;

public class BoardCell {
private int row;
private int col;
private char initial;
private DoorDirection door;

public BoardCell(int row, int col, String str) {
	super();
	this.row = row;
	this.col = col;
	door = DoorDirection.NONE;
	if (str.length() == 2){
		char c = str.charAt(1);
		switch (c){
		case 'R':
			door = DoorDirection.RIGHT;
			break;
		case 'L':
			door = DoorDirection.LEFT;
			break;
		case 'D':
			door = DoorDirection.DOWN;
			break;
		case 'U':
			door = DoorDirection.UP;
			break;
	    default:
	    	door = DoorDirection.NONE;
	    	break;
		}
	}
	initial = str.charAt(0);
}

public int getRow() {
	return row;
}
public int getCol() {
	return col;
}
public DoorDirection getDoorDirection() {
	return door;
}
public char getInitial() {
	return initial;
}


public void setRow(int row) {
	this.row = row;
}
public void setCol(int col) {
	this.col = col;
}


@Override
public String toString() {
	return "BoardCell [row=" + row + ", col=" + col + "]";
}


public void setInitial(char initial) {
	this.initial = initial;
}


public boolean isWalkway() {
	if (initial == 'W'){
		return true;
	}
	else{
		return false;
	}
}
public boolean isDoorway() {
	if (door != DoorDirection.NONE){
		return true;
	}
	else{
		return false;
	}
}
public boolean isRoom() { //should a door be a room?
	if ((initial != 'W') && (initial != 'X')){
		return true;
	}
	else{
		return false;
	}
}


}
