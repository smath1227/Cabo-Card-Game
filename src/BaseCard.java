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
 * This class models a singular card in a full deck
 */

import java.io.File;

public class BaseCard {

  // instance fields
  private int x;
  private int y;
  private final int WIDTH = 50;
  private final int HEIGHT = 70;
  protected String suit;
  protected int rank;
  protected boolean faceUp;
  private static processing.core.PImage cardBack;
  private processing.core.PImage cardImage;
  protected static processing.core.PApplet processing;

  /**
   * Constructs a new BaseCard with the specified rank and suit. The card is initialized to be
   * face down by default. You may assume that the provided rank and suit are valid.
   * This method should also initialize the cardImage, and initialize cardBack if that has not yet
   * been done by any other constructor call.
   * @param rank - the rank of the card (e.g., 1 for Ace, 13 for King).
   * @param suit - the suit of the card (e.g., "Hearts", "Diamonds").
   * @throws IllegalStateException - if the Processing environment has not been set before creating
   * the card.
   */
  public BaseCard(int rank, String suit) {

    if (processing == null) {
      throw new IllegalStateException("Processing environment not created");
    }

    this.rank = rank;
    this.suit = suit;
    this.faceUp = false;
    this.cardImage =
        processing.loadImage("images"+File.separator+rank+"_of_"+suit.toLowerCase()+".png");
    cardBack =
        processing.loadImage("images"+File.separator+"back.png");
  }

  /**
   * Returns the rank of the card directly, or -1 if the card is the King of Diamonds
   * @return the rank of the card, or -1 for the King of Diamonds
   */
  public int getRank() {
    if (this.rank == 13 && this.suit.equals("Diamonds")) {
      return -1;
    }
    return this.rank;
  }

  /**
   * Sets the face-up status of the card.
   * @param faceUp - if true, set the card face-up; if false, set it face-down.
   */
  public void setFaceUp(boolean faceUp) {
    this.faceUp = faceUp;
  }

  /**
   * Sets the Processing environment to be used for drawing and interacting with cards.
   * This method must be called before creating any BaseCard objects.
   * @param processing - the Processing PApplet environment.
   */
  public static void setProcessing(processing.core.PApplet processing) {
    BaseCard.processing = processing;
  }

  /**
   * Draws the card on the PApplet at the specified position.
   * @param xPosition - the x-coordinate to draw the card.
   * @param yPosition - the y-coordinate to draw the card.
   */
  public void draw (int xPosition, int yPosition) {
    processing.fill(255);
    processing.rect(xPosition, yPosition, WIDTH, HEIGHT);
    this.x = xPosition;
    this.y = yPosition;
    if (this.faceUp) {
      processing.image(cardImage, x, y, WIDTH, HEIGHT);
    }
    else {
      processing.image(cardBack, x, y, WIDTH, HEIGHT);
    }
  }

  /**
   * Returns a string representation of the card, showing its suit and rank.
   * @return a string in the format "Suit Rank" (e.g., "Hearts 10").
   */
  @Override
  public String toString() {
    return this.suit + " " + this.rank;
  }

  /**
   * Checks if the mouse is currently over this card. Use PApplet's mouseX and mouseY fields
   * to determine where the mouse is; the (x,y) coordinates of this card's upper left corner
   * were set when it was last drawn.
   * @return true if the card is under the mouse's current position, false otherwise.
   */
  public boolean isMouseOver() {
    return (processing.mouseX <= this.x + WIDTH
        && processing.mouseX >= this.x)
        && (processing.mouseY >= this.y
        && processing.mouseY <= this.y + HEIGHT);
  }
}

