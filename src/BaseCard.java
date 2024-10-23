import java.io.File;
import processing.core.PImage;
import processing.core.PApplet;
import processing.core.PConstants;
import java.util.ArrayList;
import java.util.Collections;

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

  public int getRank() {
    if (this.rank == 13 && this.suit.equals("Diamonds")) {
      return -1;
    }
    return this.rank;
  }

  public void setFaceUp(boolean faceUp) {
    this.faceUp = faceUp;
  }

  public static void setProcessing(processing.core.PApplet processing) {
    BaseCard.processing = processing;
  }

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

  @Override
  public String toString() {
    return this.suit + " " + this.rank;
  }

  public boolean isMouseOver() {
    return (processing.mouseX <= this.x + WIDTH
        && processing.mouseX >= this.x)
        && (processing.mouseY <= this.y
        && processing.mouseY >= this.y - HEIGHT);
  }
}

