package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GuessDialog extends JDialog{
	
	private Board board = Board.getInstance();
	private Room room;
	private RoomGuess roomGuess;
	private Person person;
	private Weapon weapon;
	private Buttons buttons;
	private boolean suggestion;
	
	public Solution getSolution()
	{
		String room;
		String person = this.person.getName();
		String weapon = this.weapon.getName();
		
		if(suggestion)
		{
			room = this.room.getName();
		}
		else
		{
			room = roomGuess.getName();
		}
		
		Solution solution = new Solution(person, room, weapon);
		
		return solution;
		
	}

	public GuessDialog(boolean suggestion) {
		// TODO Auto-generated constructor stub
		setModal(true);
		setTitle("Make a Guess");
		setSize(300,300);
		setLayout(new GridLayout(4,1));
		
		this.suggestion = suggestion;
		
		if(suggestion)
		{
			room = new Room();	
			add(room);
		}
		else
		{
			roomGuess = new RoomGuess();
			add(roomGuess);
		}
		
		person = new Person();
		weapon = new Weapon();
		buttons = new Buttons();
		
		//add(room);
		add(person);
		add(weapon);
		add(buttons);
		
		setVisible(true);
	}
	
	public class Room extends JPanel
	{	
		public Room()
		{
			setLayout(new GridLayout(1,1));
			setBorder(new TitledBorder(new EtchedBorder(), "Your Room"));
			JLabel currentRoom = new JLabel();
			
			currentRoom.setText(board.getLegend().get(board.getCellAt(board.getHumanPlayer().getRow(), board.getHumanPlayer().getColumn()).getInitial()));
		
			add(currentRoom);
		}
	}
	
	public class RoomGuess extends JPanel {
		private JComboBox<String> guess = new JComboBox<String>();
		private ArrayList<String> rooms = board.getRooms();

		public RoomGuess() {
		
			for(String room : rooms)
			{
				guess.addItem(room);
			}
			
			add(guess);
			setBorder(new TitledBorder(new EtchedBorder(), "Room"));
		}
	}
	
	public class Person extends JPanel{
		private JComboBox<String> guess = new JComboBox<String>();
		private ArrayList<Player> players = board.getPlayers();

		public Person() {
			// TODO Auto-generated constructor stub
			for(Player player : players)
			{
				guess.addItem(player.getPlayerName());
			}
			
			add(guess);
			setBorder(new TitledBorder(new EtchedBorder(), "Person"));
		}
	}
	
	public class Weapon extends JPanel {
		private JComboBox<String> guess = new JComboBox<String>();
		private ArrayList<String> weapons = board.getWeapons();

		public Weapon() {
			for(String weapon : weapons)
			{
				guess.addItem(weapon);
			}
			
			add(guess);
			setBorder(new TitledBorder(new EtchedBorder(), "Weapon"));
		}
	}
	
	public class Buttons extends JPanel
	{
		public Buttons()
		{
			setLayout(new GridLayout(1,2));
			
			JButton submit = new JButton("Submit");
			JButton cancel = new JButton("Cancel");
			
			cancel.addActionListener(new CancelButtonListener());
			
			add(submit);
			add(cancel);
		}
		
		
	}
	
	public class SubmitButtonListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class CancelButtonListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			setVisible(false);
		}
		
	}
}
