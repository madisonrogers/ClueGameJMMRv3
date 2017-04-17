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

	private DetectiveNotes detectiveNotes;

	public static Board board;
	private ControlGUI infoPanel;	
	private HandPanel myCards;

	public GUI(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		menuBar();
		setJMenuBar(menuBar);

		currentPlayerIndex = -1;

		board = Board.getInstance();
		board.setConfigFiles("ClueCSV.csv", "Legend.txt", "ThreePlayers.txt", "Weapons.txt");
		//		board.setConfigFiles("CR_ClueLayout.csv", "CR_ClueLegend.txt", "ThreePlayers.txt", "Weapons.txt");
		board.initialize();
		board.initializeGameplay();

		detectiveNotes = new DetectiveNotes();

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
				detectiveNotes.setVisible(true);			
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
		Card suggestionResult = null;
		Solution suggestion = null;

		dieRoll = new Random().nextInt(6) + 1;
		
		infoPanel.updateInfoPanel(dieRoll, suggestion, suggestionResult);
		
		suggestion = board.runGame(playerIndex, dieRoll);

		infoPanel.updateWhoseTurn(board.getPlayers().get(playerIndex).getPlayerName());

		if (suggestion != null){
			suggestionResult = board.handleSuggestion(playerIndex, suggestion);
			if (suggestionResult != null){
				infoPanel.updateInfoPanel(dieRoll, suggestion, suggestionResult);				
			} else {
				infoPanel.updateInfoPanel(dieRoll, suggestion);
			}
		} else {
			infoPanel.updateInfoPanel(dieRoll); 
		}

		repaint();		
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
		JTextField turnField;

		public ControlGUI() {
			setLayout(new BorderLayout());
			JPanel infoPanel = createInfoPanel();
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
			turnField = new JTextField(10);
			turnField.setEditable(false);
			turnPanel.add(whoseTurn);
			turnPanel.add(turnField);

			JButton nextButton = new JButton("Next player");
			nextButton.addActionListener(new NextPlayerButtonListener());
			JButton accuseButton = new JButton("Make an Accusation");
			accuseButton.addActionListener(new MakeAccusationButtonListener());

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
			if (guess != null){
				guessField.setText(guess.toString());
			} else {
				guessField.setText("");
			}
			if (result != null){ // FIXME: make this work
								resultField.setText(result.getCardName());
			} else resultField.setText(""); // should be empty if no one can disprove
		}
		
		public void updateInfoPanel(Integer dieRoll, Solution guess) {
			rollField.setText(dieRoll.toString());
			// only update when can make a guess
			if (guess != null){
				guessField.setText(guess.toString());
			} else {
				guessField.setText("");
			}
			resultField.setText(""); // should be empty if no one can disprove
		}

		// overloaded update function
		public void updateInfoPanel(Integer dieRoll) {
			rollField.setText(dieRoll.toString());
		}

		public void updateWhoseTurn(String player){
			turnField.setText(player);
		}

		private class NextPlayerButtonListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// if current player's turn is over, increment index
				if (currentPlayerIndex >= 0 && !board.getPlayers().get(currentPlayerIndex).isTurnOver()){
					JOptionPane.showMessageDialog(null, "The current player's turn isn't over!", "Turn not over", JOptionPane.INFORMATION_MESSAGE);
				} else {
					currentPlayerIndex = ++currentPlayerIndex % board.getPlayers().size();
					runGame(currentPlayerIndex);
				}
			}
		}

		private class MakeAccusationButtonListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				// If the current player is the human player and their turn is not over, show the dialog box
				if(currentPlayerIndex >= 0){
					Player player = board.getPlayers().get(currentPlayerIndex);

					if(player instanceof HumanPlayer && !player.isTurnOver())
					{
						if(!board.getCellAt(player.getColumn(), player.getRow()).isDoorway())
						{
							JOptionPane.showMessageDialog(null, "You can not make an accusation because you are not in a room.", "Not in a room", JOptionPane.INFORMATION_MESSAGE);
						}
						else
						{
							GuessDialog guess = new GuessDialog();
							guess.setVisible(true);	
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null, "It is not your turn!", "Not your turn", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				}
			}

		}
	}
}
