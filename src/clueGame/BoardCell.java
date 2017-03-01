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
		case 'L':
			door = DoorDirection.LEFT;
		case 'D':
			door = DoorDirection.DOWN;
		case 'U':
			door = DoorDirection.UP;
	    default:
	    	door = DoorDirection.NONE;
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
	return false;
}
public boolean isDoorway() {
	return false;
}
public boolean isRoom() {
	return false;
}

public BoardCell getDoorDirection() {
	return null;
}


public char getInitial() {
	return 'c';
}
}
