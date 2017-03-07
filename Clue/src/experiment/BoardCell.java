package experiment;

public class BoardCell {
private int row;
private int col;
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

}
