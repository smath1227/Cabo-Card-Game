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
 * This class models each individual player for each game of CABO.
 */

public class Player {
  private Hand hand;
  private boolean isComputer;
  private int label;
  private String name;

  /**
   * Constructs a new Player object with the given values and initializes the hand
   * @param name - the new player's identifier
   * @param label - the new player's label, assumed to be 0-3
   * @param isComputer - true if this is a computer player, false if this is a human
   */
  public Player(String name, int label, boolean isComputer) {
    this.name = name;
    this.label = label;
    this.isComputer = isComputer;
    this.hand = new Hand();
  }

  /**
   * Accesses a shallow-copy reference of this player's hand
   * @return a reference to this player's hand
   */
  public Hand getHand() {
    return this.hand;
  }

  /**
   * Accesses the name of this Player
   * @return this player's identifier
   */
  public String getName() {
    return this.name;
  }

  /**
   * Accesses the label (0-3) of this Player
   * @return this player's label
   */
  public int getLabel() {
    return this.label;
  }

  /**
   * Reports whether this is a computer player
   * @return true if this is a computer player, false if this is a human
   */
  public boolean isComputer() {
    return this.isComputer;
  }

  /**
   * Adds a card to this player's hand
   * @param card - the card to add to this player's hand
   */
  public void addCardToHand(BaseCard card) {
    hand.addCard(card);
  }
}
