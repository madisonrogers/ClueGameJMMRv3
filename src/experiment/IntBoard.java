package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class IntBoard {
private BoardCell[][] grid;
private Map<BoardCell, Set<BoardCell>> adjMatrix;
//private Set<BoardCell> keys;

public IntBoard() {
	grid = new BoardCell[4][];
	for (int i = 0; i < 4; i++){
	grid[i] = new BoardCell[4];
	}
	adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
	//keys = new HashSet<BoardCell>();
}
public void calcAdjacencies(){
	//if new spot valid, add to set and map 
	Set<BoardCell> set = new HashSet<BoardCell>();
	for (int i= 0; i < 4; i++) {
		for (int j = 0; j < 4; j++) {
			BoardCell current = new BoardCell();
			current.setCol(i);
			current.setRow(j);
			set.add(current);
		}
	}

	
	for (int i = 0; i < 4; i++){//rows
		for (int j = 0; j < 4; j++){//col
			BoardCell temp = new BoardCell();
			temp.setCol(j);
			temp.setRow(i);
			Set<BoardCell> list = new HashSet();
			
			if ((i-1) >= 0){//test left
				
				BoardCell temp2 = new BoardCell();
				temp2.setCol(j);
				temp2.setRow(i-1);
				list.add(temp2);
				//adjMatrix.get(temp).add(temp2);
			}
			if ((i+1) < 4){//test right
				
				BoardCell temp2 = new BoardCell();
				temp2.setCol(j);
				temp2.setRow(i+1);
				list.add(temp2);
				//adjMatrix.get(temp).add(temp2);
			}
			if ((j-1) >= 0){//test up
				
				BoardCell temp2 = new BoardCell();
				temp2.setCol(j-1);
				temp2.setRow(i);
				list.add(temp2);
				//adjMatrix.get(temp).add(temp2);
			}
			if ((j+1) < 4){//test down
			
				BoardCell temp2 = new BoardCell();
				temp2.setCol(j+1);
				temp2.setRow(i);
				list.add(temp2);
				//adjMatrix.get(temp).add(temp2);
			}
			adjMatrix.put(temp, list);
		}
	}
	
	
/*	BoardCell position = new BoardCell();
	Set<BoardCell> adjs = new HashSet<BoardCell>();
	BoardCell temp = new BoardCell();
	for (int i = 0; i < 4; i++) {
		for (int j = 0; j < 4; j++) {
			position.setRow(i);
			position.setCol(j);
			if(i > 0) {
				temp.setRow(i-1);
				temp.setCol(j);
				adjs.add(temp);
			}
			if(i < 4) {
				temp.setRow(i+1);
				temp.setCol(j);
				adjs.add(temp);
			}
			if(j >0) {
				temp.setRow(i);
				temp.setCol(j-1);
				adjs.add(temp);
				}
			if(j <4) {
				temp.setRow(i);
				temp.setCol(j+1);
				adjs.add(temp);
			}
		adjMatrix.put(position, adjs);
		adjs.clear();
		}
	}*/

}
public void calcTargets(BoardCell p, int length){
	
}
public Set<BoardCell> getTargets(){
	return null;
}
public Set<BoardCell> getAdjList(BoardCell b){
	for (BoardCell key: adjMatrix.keySet()){
		if ((key.getCol() == b.getCol()) && (key.getRow() == b.getRow())){
			return adjMatrix.get(key);
		}
	}
	
	//System.out.println(adjMatrix.get(b));
	return adjMatrix.get(b);

}
public BoardCell getCell(int i, int j, int x, int z){
	BoardCell b = new BoardCell();
	b.setCol(j);
	b.setRow(i);
	BoardCell a = new BoardCell();
	a.setCol(z);
	a.setRow(x);
	for (BoardCell key: adjMatrix.keySet()){
		if ((key.getCol() == a.getCol()) && (key.getRow() == a.getRow())){
			for (BoardCell cell: adjMatrix.get(key)){
				if ((cell.getCol() == b.getCol()) && (cell.getRow() == b.getRow())){
					return cell;
				}
			}
		}
	}
	return null;
}
}
