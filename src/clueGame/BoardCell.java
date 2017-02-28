package clueGame;

public class BoardCell {
private int row;
private int col;
private char initial;

public int getRow() {
	return row;
}
public void setRow(int row) {
	this.row = row;
}
public int getCol() {
	return col;
}
@Override
public String toString() {
	return "BoardCell [row=" + row + ", col=" + col + "]";
}
public void setCol(int col) {
	this.col = col;
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
