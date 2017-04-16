package clueGame;

import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GuessDialog extends JDialog{
	
	private Board board = Board.getInstance();
	private Room room;
	
	public GuessDialog() {
		// TODO Auto-generated constructor stub
		setModal(true);
		setTitle("Make a Guess");
		
		room = new Room();
		
		add(room);
	}
	
	public class Room extends JPanel
	{	
		public Room()
		{
			setLayout(new GridLayout(1,2));
			JLabel roomLabel = new JLabel("Your room");
			JTextArea currentRoom = new JTextArea();
			
			currentRoom.setText(board.getCellAt(board.getHumanPlayer().getColumn(), board.getHumanPlayer().getRow()).toString());
			
			add(roomLabel);
			add(currentRoom);
		}
	}
}
