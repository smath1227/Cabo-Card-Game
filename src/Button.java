import processing.core.PApplet; //imports the applet used for drawing

/**
 * The Button class represents a simple interactive button in the Processing environment. It
 * displays a label and can change its appearance when active or inactive. The button's appearance
 * and behavior are managed through the Processing library.
 */
public class Button {
  private boolean active;
  private int height;
  private String label;
  protected static processing.core.PApplet processing;
  private int width;
  private int x;
  private int y;

  /**
   * Constructs a Button with the specified label and position, which is inactive by default.
   *
   * @param label - The text label displayed on the button
   * @param x - The x-coordinate of the top-left corner of the button
   * @param y - The y-coordinate of the top-left corner of the button
   * @param width - The width of the button
   * @param height - The height of the button
   */
  public Button(String label, int x, int y, int width, int height) {

    if (processing == null) {
      throw new IllegalStateException("Processing environment not set");
    }

    this.active = false;
    this.label = label;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  /**
   * Renders the button on the Processing canvas. The button changes color based on
   * its isActive parameter and whether the mouse is currently over it
   */
  public void draw() {
    //set the correct fill and draw the button
    if (active) {
      if (isMouseOver()) {
        processing.fill(150);
      } else {
        processing.fill(200);
      }
    } else {
      processing.fill(255, 51, 51);
    }
    processing.rect(x, y, width, height, 5);

    //set correct fill and draw text
    processing.fill(0);
    processing.textSize(14);
    processing.textAlign(PApplet.CENTER, PApplet.CENTER);
    processing.text(label, x + width / 2, y + height / 2);

  }

  /**
   * Changes the label of this button
   *
   * @return label - the new label for this button
   */
  public String getLabel() {
    return label;
  }

  /**
   * Returns whether the button is currently active.
   *
   * @return true if the button is active, false otherwise.
   */
  public boolean isActive() {
    return active;
  }

  /**
   * Checks if the mouse is currently over this button. Use PApplet's
   * mouseX and mouseY fields to determine where the mouse is.
   *
   * @return true if the button is under the mouse's current position, false otherwise.
   */
  public boolean isMouseOver() {
    return (processing.mouseX <= this.x + width && processing.mouseX >= this.x) && (processing.mouseY <= this.y + height && processing.mouseY >= this.y);
  }

  /**
   * Sets the active state of the button. If true, the button will be rendered as active.
   * If false, it will be rendered as inactive.
   *
   * @param active - the new active state of the button.
   */
  public void setActive(boolean active) {
    this.active = active;
  }

  /**
   * Changes the label of this button
   *
   * @param label - the new label for this button
   */
  public void setLabel(String label) {
    this.label = label;
  }

  /**
   * Sets the Processing environment to be used by the Button class. This must be called before creating any buttons.
   *
   * @param processing - the Processing environment to be used for drawing and interaction.ocessing
   */
  public static void setProcessing(processing.core.PApplet processing) {
    Button.processing = processing;
  }
}
