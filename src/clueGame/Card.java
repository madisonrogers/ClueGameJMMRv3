package clueGame;

public class Card {
	private String cardName;
	private CardType type;
	public Card(String cardName, CardType type) {
		super();
		this.cardName = cardName;
		this.type = type;
	}
	public boolean equals(){
		return false; // TODO: finish
	}
	@Override
	public String toString() {
		return "[Card=" + cardName + ", Type=" + type + "]";
	}
	public CardType getType() {
		return type;
	}
	public String getCardName() {
		return cardName;
	}
}
