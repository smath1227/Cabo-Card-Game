import java.util.ArrayList;

public class Hand extends Deck {
  private final int HAND_SIZE = 4;

  public Hand() {
    super(new ArrayList<BaseCard>());
  }

  @Override
  public void addCard(BaseCard card) {
    if (this.size() >= HAND_SIZE) {
      throw new IllegalStateException("Hand is already full");
    }
    super.addCard(card);
  }

  public void setFaceUp(int index, boolean faceUp) {
    cardList.get(index).setFaceUp(faceUp);
  }

  public int getRankAtIndex(int index) {
    return cardList.get(index).getRank();
  }

  public int calcHand() {
    int val = 0;
    for (BaseCard c : cardList) {
      val += c.getRank();
    }
    return val;
  }

  public void draw(int y) {
    for (BaseCard c : cardList) {
      c.draw((50 + 60 * cardList.indexOf(c)), y);
    }
  }

  public int indexOfMouseOver() {
    for (BaseCard c : cardList) {
      if (c.isMouseOver()) {
        return cardList.indexOf(c);
      }
    }
    return -1;
  }

  public BaseCard swap(BaseCard newCard, int index) {
    BaseCard old = cardList.get(index);
    cardList.set(index, newCard);
    return old;
  }

  public void switchCards(int myIndex, Hand otherHand, int otherIndex) {
    BaseCard myCard = cardList.get(myIndex);
    BaseCard otherCard = otherHand.cardList.get(otherIndex);
    cardList.set(otherIndex, myCard);
    otherHand.cardList.set(myIndex, otherCard);
  }

}
