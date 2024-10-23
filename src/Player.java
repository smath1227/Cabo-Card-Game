import javax.smartcardio.Card;

public class Player {
  private Hand hand;
  private boolean isComputer;
  private int label;
  private String name;

  public Player(String name, int label, boolean isComputer) {
    this.name = name;
    this.label = label;
    this.isComputer = isComputer;
    this.hand = new Hand();
  }

  public Hand getHand() {
    return this.hand;
  }

  public String getName() {
    return this.name;
  }

  public int getLabel() {
    return this.label;
  }

  public boolean isComputer() {
    return this.isComputer;
  }

  public void addCardToHand(BaseCard card) {
    hand.addCard(card);
  }
}
