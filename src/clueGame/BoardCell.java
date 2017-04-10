package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class BoardCell {
	private int row;
	private int col;
	private char initial;
	private DoorDirection door;
	public void setDoor(DoorDirection door) {
		this.door = door;
	}

	public static final int CELL_SIZE = 30;

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
			if (door == DoorDirection.NONE){
				return true;
			}
			else {
				return false;
			}
		}
		else{
			return false;
		}	
	}
//	
	public Graphics draw(Graphics g){
		g.setColor(Color.BLACK);
		g.drawRect(col*CELL_SIZE, row*CELL_SIZE, CELL_SIZE, CELL_SIZE); 
		g.setColor(Color.gray);
		if (!this.isWalkway()) g.fillRect(col*CELL_SIZE, row*CELL_SIZE, CELL_SIZE, CELL_SIZE);
		g.setColor(Color.WHITE);
		if (this.isDoorway()){
			switch(door){
			case UP:
				g.fillRect(col*CELL_SIZE, row*CELL_SIZE, CELL_SIZE, CELL_SIZE/6);
				break;
			case DOWN:
				g.fillRect(col*CELL_SIZE, row*CELL_SIZE + (CELL_SIZE - CELL_SIZE/6), CELL_SIZE, CELL_SIZE/6);
				break;
			case LEFT:
				g.fillRect(col*CELL_SIZE, row*CELL_SIZE, CELL_SIZE/6, CELL_SIZE);
				break;
			case RIGHT:
				g.fillRect(col*CELL_SIZE + (CELL_SIZE - CELL_SIZE/6), row*CELL_SIZE/6, CELL_SIZE, CELL_SIZE);
				break;
			}
		}
		return g;
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
	
	public void setInitial(char initial) {
		this.initial = initial;
	}

	@Override
	public String toString() {
		return "Cell at (" + row + ", " + col + ")";
	}
}
