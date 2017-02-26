package experiment;

import java.util.Set;

public class IntBoard {
private BoardCell[][] grid;

public IntBoard() {
	grid = new BoardCell[4][4];
	
}
public void calcAdjacencies(){
	
}
public void calcTargets(BoardCell p, int length){
	
}
public Set<BoardCell> getTargets(){
	return null;
}
public Set<BoardCell> getAdjList(BoardCell b){
	return null;
}
public BoardCell getCell(int i, int j){
	return grid[i][j];
}
}
