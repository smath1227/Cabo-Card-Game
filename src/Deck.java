//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    P05 CABO
// Course:   CS 300 Fall 2024
//
// Author:   Sid Mathur
// Email:    smathur24@wisc.edu
// Lecturer: Blerina Gkotse
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    Artur Sobol
// Partner Email:   arsobol@wisc.edu
// Partner Lecturer's Name: Hobbes LeGault
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   _X_ Write-up states that pair programming is allowed for this assignment.
//   _X_ We have both read and understand the course Pair Programming Policy.
//   _X_ We have registered our team prior to the team registration deadline.
//
//////////////////////// ASSISTANCE/HELP CITATIONS ////////////////////////////
//
// Persons:         N/A
// Online Sources:  N/A
//
///////////////////////////////////////////////////////////////////////////////

/**
 * This class represents the various decks which cards are held in.
 */

import java.util.ArrayList;
import java.util.Collections;

/**
 * The Deck class represents a deck of playing cards for the game Cabo. It manages a collection of
 * cards, including shuffling, drawing, and adding cards.
 */
public class Deck {
  protected static processing.core.PApplet processing;
  protected ArrayList<BaseCard> cardList;

  /**
   * Constructs a new Deck based on the provided parameter.
   * NOTE: To create a full deck, pass in the output of createDeck().
   * @param deck - the starting list of cards for this deck; should be either a full deck or
   *             an empty list.
   * @throws IllegalStateException if the Processing environment is not set before creating a deck
   */
  public Deck(ArrayList<BaseCard> deck) {
    if (processing == null) {
      throw new IllegalStateException("Processing environment not set");
    }
    this.cardList = deck;
  }

  /**
   * Sets the Processing environment to be used by the Deck class.
   * This must be called before creating a deck.
   * @param processing - the Processing PApplet environment.
   */
  public static void setProcessing(processing.core.PApplet processing) {
    Deck.processing = processing;
  }

  /**
   * Draws a card from the top (end) of the deck.
   * @return the top card from the deck, or null if the deck is empty.
   */
  public BaseCard drawCard() {
    if (this.cardList.isEmpty()) {
      return null;
    }
    return cardList.removeLast();
  }

  /**
   * Adds a card to the top (end) of the deck.
   * @param card - the card to add to the deck.
   */
  public void addCard(BaseCard card) {
    this.cardList.add(card);
  }

  /**
   * Gets the current number of cards in the Deck.
   * @return the size of the deck.
   */
  public int size() {
    return this.cardList.size();
  }

  /**
   * Checks if the deck is empty.
   * @return true if the deck is empty, false otherwise.
   */
  public boolean isEmpty() {
    return this.cardList.isEmpty();
  }

  /**
   * Draws the top card of the deck onto the Processing canvas at the specified position.
   * If the deck is empty, draws a placeholder indicating the deck is empty.
   * @param x - the x-coordinate to draw the card.
   * @param y - the y-coordinate to draw the card.
   * @param isDiscard - whether the deck is a discard pile, in which case the top card should be
   *                  drawn face-up. Otherwise, the top card should be face-down.
   */
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
