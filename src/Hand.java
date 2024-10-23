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
 * This class represents the cards held by a specific player.
 */

import java.util.ArrayList;

public class Hand extends Deck {
  private final int HAND_SIZE = 4;

  /**
   * Creates a new empty deck for this hand
   */
  public Hand() {
    super(new ArrayList<BaseCard>());
  }

  /**
   * Overrides Deck's addCard() method to prevent this player being dealt more than HAND_SIZE cards
   * @param card - the card to add to this hand
   * @throws IllegalStateException - if the player is already holding the maximum number of cards
   */
  @Override
  public void addCard(BaseCard card) {
    if (this.size() >= HAND_SIZE) {
      throw new IllegalStateException("Hand is already full");
    }
    super.addCard(card);
  }

  /**
   * Changes the face-up value of the card at the given index to the provided value
   * @param index - the index of the card to change
   * @param faceUp - true if this card should be face-up, false if it should be face-down
   */
  public void setFaceUp(int index, boolean faceUp) {
    cardList.get(index).setFaceUp(faceUp);
  }

  /**
   * Accesses the rank of a card at a given index
   * @param index - the index of the card to access
   * @return the rank of the card at that index
   */
  public int getRankAtIndex(int index) {
    return cardList.get(index).getRank();
  }

  /**
   * Determines the total value of the cards in this hand, as a sum of the ranks of each of the
   * cards
   * @return the total value of this Player's hand
   */
  public int calcHand() {
    int val = 0;
    for (BaseCard c : cardList) {
      val += c.getRank();
    }
    return val;
  }

  /**
   * Draws the entire hand at the given y-coordinate.
   * @param y - the y-coordinate of the upper-left corner of all cards in this hand
   */
  public void draw(int y) {
    for (BaseCard c : cardList) {
      c.draw((50 + 60 * cardList.indexOf(c)), y);
    }
  }

  /**
   * Checks if the mouse is currently over any of the cards in this hand, and returns the index
   * of any card which the mouse is over, or -1 if the mouse is not currently over any card
   * in this hand.
   * @return the index of a card in this hand which the mouse is over, or -1 if the mouse is not
   * over any cards in this hand
   */
  public int indexOfMouseOver() {
    for (BaseCard c : cardList) {
      if (c.isMouseOver()) {
        return cardList.indexOf(c);
      }
    }
    return -1;
  }

  /**
   * Replaces the card at the given index (assumed to be between 0 and (HAND_SIZE-1)) with
   * the provided card, and returns the card that was previously at that index.
   * @param newCard - the card to swap into this hand
   * @param index - the index to place the new card at
   * @return the card that was previously at that index
   */
  public BaseCard swap(BaseCard newCard, int index) {
    BaseCard old = cardList.get(index);
    cardList.set(index, newCard);
    return old;
  }

  /**
   * Switches a card in this hand with a card in the other hand.
   * @param myIndex - the index of the card in this hand to switch
   * @param otherHand - the other hand to switch cards with
   * @param otherIndex - the index of the card in the other hand to switch
   */
  public void switchCards(int myIndex, Hand otherHand, int otherIndex) {
    BaseCard myCard = cardList.get(myIndex);
    BaseCard otherCard = otherHand.cardList.get(otherIndex);
    cardList.set(otherIndex, myCard);
    otherHand.cardList.set(myIndex, otherCard);
  }

}
