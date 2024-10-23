import java.util.ArrayList;
import java.util.Collections;

/**
 * The Deck class represents a deck of playing cards for the game Cabo. It manages a collection of
 * cards, including shuffling, drawing, and adding cards.
 */
public class Deck {
  protected static processing.core.PApplet processing;
  protected ArrayList<BaseCard> cardList;

  public Deck(ArrayList<BaseCard> deck) {
    if (processing == null) {
      throw new IllegalStateException("Processing environment not set");
    }
    this.cardList = deck;
  }

  public static void setProcessing(processing.core.PApplet processing) {
    Deck.processing = processing;
  }

  public BaseCard drawCard() {
    if (this.cardList.isEmpty()) {
      return null;
    }
    return cardList.removeLast();
  }

  public void addCard(BaseCard card) {
    this.cardList.add(card);
  }

  public int size() {
    return this.cardList.size();
  }

  public boolean isEmpty() {
    return this.cardList.isEmpty();
  }

  public void draw(int x, int y, boolean isDiscard) {
    if (isEmpty()) {
      processing.stroke(0);
      processing.fill(0);
      processing.rect(x, y, 50, 70, 7);
      processing.fill(255);
      processing.textSize(12);
      processing.textAlign(processing.CENTER, processing.CENTER);
      processing.text("Empty", x + 25, y + 35);
      return;
    }

    BaseCard topCard = cardList.getLast();

    if (isDiscard) {
      topCard.setFaceUp(true);
      topCard.draw(x, y);
    } else {
      topCard.setFaceUp(false);
      topCard.draw(x, y);
    }
  }


  /**
   * Sets up the deck with CABO cards, including action cards. Initializes the deck with all
   * necessary cards and shuffles them.
   *
   * @return the completed ArrayList of CABO cards
   */
  public static ArrayList<BaseCard> createDeck() {
    ArrayList<BaseCard> cardList = new ArrayList<>();

    // Define the suits
    String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};

    // Cards from 1 (Ace) to 13 (King)
    for (int rank = 1; rank <= 13; ++rank) {
      // Loop through each suit
      for (String suit : suits) {
        if (rank >= 7 && rank <= 12) {
          // Special action cards
          String actionType = "";
          if (rank == 7 || rank == 8) {
            actionType = "peek";
          } else if (rank == 9 || rank == 10) {
            actionType = "spy";
          } else {
            actionType = "switch";
          }
          cardList.add(new ActionCard(rank, suit, actionType));  // Add ActionCard to deck
        } else {
          cardList.add(new BaseCard(rank, suit));  // Add NumberCard to deck
        }
      }
    }
    Collections.shuffle(cardList);
    return cardList;
  }
}
