package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class HandPanel extends JPanel {
	private JPanel people;
	private JPanel room;
	private JPanel weapon;
	private JTextArea peopleCards;
	private JTextArea roomCards;
	private JTextArea weaponCards;
	private ArrayList<Card> peopleList;
	private ArrayList<Card> roomList;
	private ArrayList<Card> weaponList;

	public static final int TEXT_FIELD_LENGTH = 10;

	public HandPanel(ArrayList<Card> hand){
		peopleList = new ArrayList<Card>();
		roomList = new ArrayList<Card>();
		weaponList = new ArrayList<Card>();

		for (Card card : hand){
			if (card.getType() == CardType.PERSON){
				peopleList.add(card);
			}
			if (card.getType() == CardType.ROOM){
				roomList.add(card);
			}
			if (card.getType() == CardType.WEAPON){
				weaponList.add(card);
			}
		}

		setLayout(new GridLayout(3, 1));
		setBorder(new TitledBorder (new EtchedBorder(), "My Cards"));
		people = new JPanel();
		room = new JPanel();
		weapon = new JPanel();

		add(people);
		add(room);
		add(weapon);

		loadCards();
		updateCards();
	}

	public void loadCards(){
		// people cards
		peopleCards = new JTextArea(peopleList.size(), TEXT_FIELD_LENGTH);
		peopleCards.setEditable(false);
		peopleCards.setLineWrap(true);
		people.add(peopleCards);
		people.setBorder(new TitledBorder (new EtchedBorder(), "People"));

		// room cards
		roomCards = new JTextArea(roomList.size(), TEXT_FIELD_LENGTH);
		roomCards.setEditable(false);
		roomCards.setLineWrap(true);
		room.add(roomCards);
		room.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));

		// weapon cards
		weaponCards = new JTextArea(weaponList.size(), TEXT_FIELD_LENGTH);
		weaponCards.setEditable(false);
		weaponCards.setLineWrap(true);
		weapon.add(weaponCards);
		weapon.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
	}

	public void updateCards(){
		peopleCards.setText("");
		roomCards.setText("");
		weaponCards.setText("");

		for (Card card : peopleList){
			if (peopleCards.getText().equals("")){
				peopleCards.setText(card.getCardName());
			} else peopleCards.setText(peopleCards.getText() + "\n" + card.getCardName());			
		}
		for (Card card : roomList){
			if (roomCards.getText().equals("")){
				roomCards.setText(card.getCardName());
			} else roomCards.setText(roomCards.getText() + "\n" + card.getCardName());
		}
		for (Card card : weaponList){
			if (weaponCards.getText().equals("")){
				weaponCards.setText(card.getCardName());
			} else weaponCards.setText(weaponCards.getText() + "\n" + card.getCardName());
		}
	}
}
