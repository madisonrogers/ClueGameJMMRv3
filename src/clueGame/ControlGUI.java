package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

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
		JTextField guessField = new JTextField(30);
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
}
