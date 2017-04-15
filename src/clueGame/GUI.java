package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GUI extends JFrame {
	public static final int FRAME_WIDTH = 780;
	public static final int FRAME_HEIGHT = 800;
	private static JMenuBar menuBar;
	
	private int currentPlayerIndex;
	private int dieRoll;
	private boolean playerTurnOver;
	
	private DetectiveNotes detectiveNotes;
	
	public static Board board;
	private ControlGUI infoPanel;	
	private HandPanel myCards;
	
	public GUI(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		menuBar();
		setJMenuBar(menuBar);
		
		currentPlayerIndex = 0;
		playerTurnOver = true;
		
		board = Board.getInstance();
		board.setConfigFiles("ClueCSV.csv", "Legend.txt", "ThreePlayers.txt", "Weapons.txt");
//		board.setConfigFiles("CR_ClueLayout.csv", "CR_ClueLegend.txt", "ThreePlayers.txt", "Weapons.txt");
		board.initialize();
		board.initializeGameplay();
		
		add(board, BorderLayout.CENTER);
		
		// instance of ControlGUI class
		infoPanel = new ControlGUI();
		add(infoPanel, BorderLayout.SOUTH);	
		
		// instance of HandPanel class
		myCards = new HandPanel(board.getHumanPlayer().getHand());
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
				detectiveNotes = new DetectiveNotes();
				detectiveNotes.setVisible(true);
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
		playerTurnOver = false;
		Card suggestionResult = null;
		Solution suggestion = null;
		
		dieRoll = new Random().nextInt(6) + 1;
		
		suggestion = board.runGame(playerIndex, dieRoll);
		
		if (suggestion != null){
			suggestionResult = board.handleSuggestion(playerIndex, suggestion);
			infoPanel.updateInfoPanel(dieRoll, suggestion, suggestionResult);
		} else {
			infoPanel.updateInfoPanel(dieRoll); 
		}

		repaint();
		
		currentPlayerIndex = ++currentPlayerIndex % board.getPlayers().size();
		
		playerTurnOver = false;
		
	}

	public static void main(String[] args){
		GUI gui = new GUI();
		gui.setVisible(true);
		
		// Splash screen on startup
		Player human = board.getHumanPlayer();
		JOptionPane.showMessageDialog(gui, "You are " + human.getPlayerName() + ", press Next Player to begin play", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
	}
	
	// Put this in here to access board when NextPlayer button is pressed
	public class ControlGUI extends JPanel {
		JTextField rollField;
		JTextField guessField;
		JTextField resultField;
		
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
			rollField = new JTextField(5);
			rollField.setEditable(false);
			diePanel.add(roll);
			diePanel.add(rollField);
			diePanel.setBorder(new TitledBorder (new EtchedBorder(), "Die"));

			// diePanel
			JPanel guessPanel = new JPanel();
			JLabel guess = new JLabel("Guess");
			roll.setHorizontalAlignment(JLabel.RIGHT);
			guessField = new JTextField(25);
			guessField.setEditable(false);
			guessPanel.add(guess);
			guessPanel.add(guessField);
			guessPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));

			// diePanel
			JPanel resultPanel = new JPanel();
			JLabel response = new JLabel("Response");
			roll.setHorizontalAlignment(JLabel.RIGHT);
			resultField = new JTextField(15);
			resultField.setEditable(false);
			resultPanel.add(response);
			resultPanel.add(resultField);
			resultPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));

			panel.add(diePanel);
			panel.add(guessPanel);
			panel.add(resultPanel);
			return panel;
		}	
		
		public void updateInfoPanel(Integer dieRoll, Solution guess, Card result) {
			rollField.setText(dieRoll.toString());
			// only update when can make a guess
			guessField.setText(guess.toString());
			resultField.setText(result.getCardName());
		}
		
		// overloaded update function
		public void updateInfoPanel(Integer dieRoll) {
			rollField.setText(dieRoll.toString());
		}
		
		private class ButtonListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!playerTurnOver){
//					JDialog playerNotOver = new JDialog();
//					playerNotOver.setLayout(new GridLayout(2,1));
//					playerNotOver.setSize(300, 100);
//					playerNotOver.setTitle("Error");
//					JLabel error = new JLabel("You didn't finish your turn!");
//					error.setSize(30,30);
//					JButton ok = new JButton("OK!");
//					
//					class ExitItemListener implements ActionListener {
//						public void actionPerformed(ActionEvent e)
//						{
//							playerNotOver.setVisible(false);
//						}
//					}
//					ok.addActionListener(new ExitItemListener());
//					
//					playerNotOver.add(error);
//					playerNotOver.add(ok);
//					playerNotOver.setVisible(true);
					JOptionPane.showMessageDialog(null, "The current player's turn isn't over!", "Turn not over", JOptionPane.INFORMATION_MESSAGE);
				} else {
					runGame(currentPlayerIndex);
				}
			}
		}
	}
}
