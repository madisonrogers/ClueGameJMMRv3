package clueGame;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotes extends JDialog{

	private PeoplePanel people;
	private RoomsPanel rooms;
	private WeaponsPanel weapons;
	private PersonGuess personGuess;
	private RoomGuess roomGuess;
	private WeaponGuess weaponGuess;
	private Board board = Board.getInstance();

	public DetectiveNotes() {
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE); // FIXME: make the notes save -> this doesnt work properly

		setTitle("Detective Notes");
		setSize(500, 500);
		setLayout(new GridLayout(3,2));

		people = new PeoplePanel();
		rooms = new RoomsPanel();
		weapons = new WeaponsPanel();
		personGuess = new PersonGuess();
		roomGuess = new RoomGuess();
		weaponGuess = new WeaponGuess();

		add(people);
		add(personGuess);
		add(rooms);
		add(roomGuess);
		add(weapons);
		add(weaponGuess);
	}
	
	public class PersonGuess extends JPanel{
		private JComboBox<String> guess = new JComboBox<String>();
		private ArrayList<Player> players = board.getPlayers();

		public PersonGuess() {
			super();
			// TODO Auto-generated constructor stub
			for(Player player : players)
			{
				guess.addItem(player.getPlayerName());
			}
			
			add(guess);
			setBorder(new TitledBorder(new EtchedBorder(), "Person Guess"));
		}
		
	}
	
	public class RoomGuess extends JPanel {
		private JComboBox<String> guess = new JComboBox<String>();
		private ArrayList<String> rooms = board.getRooms();

		public RoomGuess() {
			super();
			// TODO Auto-generated constructor stub
			for(String room : rooms)
			{
				guess.addItem(room);
			}
			
			add(guess);
			setBorder(new TitledBorder(new EtchedBorder(), "Room Guess"));
		}
	}
	
	public class WeaponGuess extends JPanel {
		private JComboBox<String> guess = new JComboBox<String>();
		private ArrayList<String> weapons = board.getWeapons();

		public WeaponGuess() {
			super();
			// TODO Auto-generated constructor stub
			for(String weapon : weapons)
			{
				guess.addItem(weapon);
			}
			
			add(guess);
			setBorder(new TitledBorder(new EtchedBorder(), "Weapon Guess"));
		}
	}

	public class PeoplePanel extends JPanel {
		private ArrayList<Player> players = board.getPlayers();
		private JCheckBox player1, player2, player3, player4, player5, player6;
		private ArrayList<JCheckBox> checkBoxes;

		public PeoplePanel() {
			super();

			checkBoxes = new ArrayList<JCheckBox>();

			player1 = new JCheckBox();
			player2 = new JCheckBox();
			player3 = new JCheckBox();
			player4 = new JCheckBox();
			player5 = new JCheckBox();
			player6 = new JCheckBox();

			for(int i = 0; i < players.size(); i++)
			{
				switch(i)
				{
				case 0:
					player1.setText(players.get(i).getPlayerName());
					checkBoxes.add(player1);
					break;
				case 1:
					player2.setText(players.get(i).getPlayerName());
					checkBoxes.add(player2);
					break;	
				case 2:
					player3.setText(players.get(i).getPlayerName());
					checkBoxes.add(player3);
					break;
				case 3:
					player4.setText(players.get(i).getPlayerName());
					checkBoxes.add(player4);
					break;
				case 4:
					player5.setText(players.get(i).getPlayerName());
					checkBoxes.add(player5);
					break;
				case 5:
					player6.setText(players.get(i).getPlayerName());
					checkBoxes.add(player6);
					break;
				default:
					break;
				}
			}			

			// Add all the check boxes to the panel
			for(JCheckBox checkBox : checkBoxes)
			{
				add(checkBox);
			}

			// set the border and layout format 
			setBorder(new TitledBorder(new EtchedBorder(), "People"));
			setLayout(new GridLayout(0,2));

		}
	}
	
	public class RoomsPanel extends JPanel
	{
		private ArrayList<String> rooms = board.getRooms();
		private JCheckBox room1, room2, room3, room4, room5, room6, room7, room8, room9;
		private ArrayList<JCheckBox> roomCheckBoxes;
		
		public RoomsPanel() {
			super();
			// TODO Auto-generated constructor stub
			
			roomCheckBoxes = new ArrayList<JCheckBox>();
			
			room1 = new JCheckBox();
			room2 = new JCheckBox();
			room3 = new JCheckBox();
			room4 = new JCheckBox();
			room5 = new JCheckBox();
			room6 = new JCheckBox();
			room7 = new JCheckBox();
			room8 = new JCheckBox();
			room9 = new JCheckBox();
			
			for(int i = 0; i < rooms.size(); i++)
			{
				switch(i)
				{
				case 0:
					room1.setText(rooms.get(i));
					roomCheckBoxes.add(room1);
					break;
				case 1:
					room2.setText(rooms.get(i));
					roomCheckBoxes.add(room2);
					break;	
				case 2:
					room3.setText(rooms.get(i));
					roomCheckBoxes.add(room3);
					break;
				case 3:
					room4.setText(rooms.get(i));
					roomCheckBoxes.add(room4);
					break;
				case 4:
					room5.setText(rooms.get(i));
					roomCheckBoxes.add(room5);
					break;
				case 5:
					room6.setText(rooms.get(i));
					roomCheckBoxes.add(room6);
					break;
				case 6:
					room7.setText(rooms.get(i));
					roomCheckBoxes.add(room7);
					break;
				case 7:
					room8.setText(rooms.get(i));
					roomCheckBoxes.add(room8);
					break;
				case 8:
					room9.setText(rooms.get(i));
					roomCheckBoxes.add(room9);
					break;
				default:
					break;
				}
			}

			// Add all the check boxes to the panel
			for(JCheckBox checkBox : roomCheckBoxes)
			{
				add(checkBox);
			}

			// set the border and layout format 
			setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
			setLayout(new GridLayout(0,2));
		}
		
	}

	public class WeaponsPanel extends JPanel{

		private ArrayList<String> weapons = board.getWeapons();
		private JCheckBox weapon1, weapon2, weapon3, weapon4;
		private ArrayList<JCheckBox> weaponCheckBoxes;

		public WeaponsPanel() {
			super();

			weaponCheckBoxes = new ArrayList<JCheckBox>();

			weapon1 = new JCheckBox();
			weapon2 = new JCheckBox();
			weapon3 = new JCheckBox();
			weapon4 = new JCheckBox();

			for(int i = 0; i < weapons.size(); i++)
			{
				switch(i)
				{
				case 0:
					weapon1.setText(weapons.get(i));
					weaponCheckBoxes.add(weapon1);
					break;
				case 1:
					weapon2.setText(weapons.get(i));
					weaponCheckBoxes.add(weapon2);
					break;	
				case 2:
					weapon3.setText(weapons.get(i));
					weaponCheckBoxes.add(weapon3);
					break;
				case 3:
					weapon4.setText(weapons.get(i));
					weaponCheckBoxes.add(weapon4);
					break;
				default:
					break;
				}
			}

			// Add all the check boxes to the panel
			for(JCheckBox checkBox : weaponCheckBoxes)
			{
				add(checkBox);
			}

			// set the border and layout format 
			setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
			setLayout(new GridLayout(0,2));
		}

	}

}
