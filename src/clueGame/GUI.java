package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.ControlGUI.ButtonListener;

public class GUI extends JFrame {
	public static final int FRAME_WIDTH = 780;
	public static final int FRAME_HEIGHT = 800;
	private static JMenuBar menuBar;
	private DetectiveNotes dialog;
	public static Board board;
	private int currentPlayerIndex;
	
	public GUI(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		
		menuBar();
		setJMenuBar(menuBar);
		
		currentPlayerIndex = 0;
		
		board = Board.getInstance();
		board.setConfigFiles("ClueCSV.csv", "Legend.txt", "ThreePlayers.txt", "Weapons.txt");
//		board.setConfigFiles("CR_ClueLayout.csv", "CR_ClueLegend.txt", "ThreePlayers.txt", "Weapons.txt");
		board.initialize();
		board.initializeGameplay();
		// TODO: add splash screen display
		add(board, BorderLayout.CENTER);
		// make instance of class
		ControlGUI infoPanel = new ControlGUI();
		add(infoPanel, BorderLayout.SOUTH); // add to frame FIXME: CHANGE TO BOTTOM WHEN WE IMPLEMENT THE REST	
		
		HandPanel myCards = new HandPanel(board.getHumanPlayer().getHand());
		add(myCards, BorderLayout.EAST);
	}
	
	public void menuBar(){
		menuBar = new JMenuBar();
		menuBar.add(createNotesMenu());
		menuBar.add(createExitItem());
	}
	
	private JMenuItem createNotesMenu()
	{
		JMenuItem notes = new JMenuItem("Notes"); 
		notes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog = new DetectiveNotes();
				dialog.setVisible(true);
				System.out.println("Print me");				
			}
		});
		return notes;
	}
	
	private JMenuItem createExitItem()
	{
		JMenuItem item = new JMenuItem("Exit");
		class ExitItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		}
		item.addActionListener(new ExitItemListener());

		return item;
	}
	
	public void runGame(int playerIndex){
		Player activePlayer = board.getPlayers().get(playerIndex);
		int dieRoll = new Random().nextInt(6) + 1;
		board.calcTargets(activePlayer.getColumn(), activePlayer.getRow(), dieRoll);

		activePlayer.makeMove(board.getTargets());
		repaint();

		if (board.getCellAt(activePlayer.getColumn(), activePlayer.getRow()).isRoom()){
			Solution suggestion = activePlayer.movedToRoom(board.getCellAt(activePlayer.getColumn(), activePlayer.getRow()), board.getPlayerCards(), board.getWeaponCards(), board.getLegend());
			board.handleSuggestion(board.getPlayers().indexOf(activePlayer), suggestion);
		}
		currentPlayerIndex = ++currentPlayerIndex % board.getPlayers().size();
	}
	
	public Player getNextPlayer(){
		
	}

	public static void main(String[] args){
		GUI gui = new GUI();
		gui.setVisible(true);
		
		// Splash screen on startup
		Player human = board.getHumanPlayer();
		JOptionPane.showMessageDialog(gui, "You are " + human.getPlayerName() + ", press Next Player to begin play", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public class ControlGUI extends JPanel {
		
		public ControlGUI() {
			setLayout(new BorderLayout());
			JPanel infoPanel = createInfoPanel();// fix this hoe
			add(infoPanel, BorderLayout.NORTH);		
		}

		public JPanel createInfoPanel(){
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			JPanel topPanel = topPanel();
			panel.add(topPanel, BorderLayout.NORTH);
			JPanel bottomPanel = bottomPanel();
			panel.add(bottomPanel, BorderLayout.SOUTH);
			return panel;
		}

		public JPanel topPanel(){
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(1, 3));

			JPanel turnPanel = new JPanel();
			JLabel whoseTurn = new JLabel("Whose turn?");
			whoseTurn.setHorizontalAlignment(JLabel.RIGHT);
			JTextField turnField = new JTextField(10);
			turnField.setEditable(false);
			turnPanel.add(whoseTurn);
			turnPanel.add(turnField);

			JButton nextButton = new JButton("Next player");
			nextButton.addActionListener(new ButtonListener());
			JButton accuseButton = new JButton("Make an Accusation");

			panel.add(turnPanel);
			panel.add(nextButton);
			panel.add(accuseButton);
			return panel;
		}

		public JPanel bottomPanel(){
			JPanel panel = new JPanel();

			// diePanel
			JPanel diePanel = new JPanel();
			JLabel roll = new JLabel("Roll");
			roll.setHorizontalAlignment(JLabel.RIGHT);
			JTextField rollField = new JTextField(5);
			rollField.setEditable(false);
			diePanel.add(roll);
			diePanel.add(rollField);
			diePanel.setBorder(new TitledBorder (new EtchedBorder(), "Die"));

			// diePanel
			JPanel guessPanel = new JPanel();
			JLabel guess = new JLabel("Guess");
			roll.setHorizontalAlignment(JLabel.RIGHT);
			JTextField guessField = new JTextField(25);
			guessField.setEditable(false);
			guessPanel.add(guess);
			guessPanel.add(guessField);
			guessPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));

			// diePanel
			JPanel resultPanel = new JPanel();
			JLabel response = new JLabel("Response");
			roll.setHorizontalAlignment(JLabel.RIGHT);
			JTextField resultField = new JTextField(15);
			resultField.setEditable(false);
			resultPanel.add(response);
			resultPanel.add(resultField);
			resultPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));

			panel.add(diePanel);
			panel.add(guessPanel);
			panel.add(resultPanel);
			return panel;
		}	
		
		private class ButtonListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// gotta check for proper turn error thing
				runGame(currentPlayerIndex);
			}
		}
	}
}
