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
 * This class represents a card that has an associated action.
 */

public class ActionCard extends BaseCard {
  private String actionType;

  /**
   * Constructs an ActionCard with the specified rank, suit, and action type.
   * You may assume that the provided action type is valid.
   * @param rank - the rank of the card (e.g., 1 for Ace, 13 for King).
   * @param suit - the suit of the card (e.g., "Hearts", "Diamonds").
   * @param actionType - the type of action associated with this card: "peek", "spy", or "switch"
   */
  public ActionCard (int rank, String suit, String actionType) {
    super(rank, suit);
    this.actionType = actionType;
  }

  /**
   * Gets the type of action associated with this card.
   * @return the action type as a String: "peek", "spy", or "switch".
   */
  public String getActionType() {
    return this.actionType;
  }
}
