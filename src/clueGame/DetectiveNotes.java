package clueGame;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotes extends JDialog{

	private PeoplePanel people;
	private Board board = Board.getInstance();

	public DetectiveNotes() {
		board.setConfigFiles("ClueCSV.csv", "Legend.txt", "ThreePlayers.txt", "Weapons.txt");
//		board.setConfigFiles("CR_ClueLayout.csv", "CR_ClueLegend.txt", "ThreePlayers.txt", "Weapons.txt");
		board.initialize();
		board.initializeGameplay();
		
		setTitle("Detective Notes");
		setSize(400, 400);
		setLayout(new GridLayout(2,3));
		
		people = new PeoplePanel();
		
		add(people);
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
			setLayout(new FlowLayout());

		}

	}

}
