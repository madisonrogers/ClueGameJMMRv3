package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class GUI extends JFrame {
	public static final int FRAME_WIDTH = 630;
	public static final int FRAME_HEIGHT = 800;
	private static JMenuBar menuBar;
	private DetectiveNotes dialog;
	
	public GUI(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		
		menuBar();
		setJMenuBar(menuBar);
		
		Board board = Board.getInstance();
		board.setConfigFiles("ClueCSV.csv", "Legend.txt", "ThreePlayers.txt", "Weapons.txt");
//		board.setConfigFiles("CR_ClueLayout.csv", "CR_ClueLegend.txt", "ThreePlayers.txt", "Weapons.txt");
		board.initialize();
		board.initializeGameplay();
		// TODO: add splash screen display
		add(board, BorderLayout.CENTER);
		// make instance of class
		ControlGUI infoPanel = new ControlGUI();
		add(infoPanel, BorderLayout.SOUTH); // add to frame FIXME: CHANGE TO BOTTOM WHEN WE IMPLEMENT THE REST		
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

	
	public static void main(String[] args){
		GUI gui = new GUI();
		gui.setVisible(true);
		JOptionPane.showMessageDialog(gui, "You are ...", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
	}
}
