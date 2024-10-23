import processing.core.PApplet;

public class Button {
  private boolean active;
  private int height;
  private String label;
  protected static processing.core.PApplet processing;
  private int width;
  private int x;
  private int y;

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

  public void draw(){
    //set the correct fill and draw the button
    if (active) {
      if (isMouseOver()){
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
    processing.textAlign(PApplet.CENTER,  PApplet.CENTER);
    processing.text(label, x + width/2, y + height/2);

  }

  public String getLabel() {
    return label;
  }

  public boolean isActive() {
    return active;
  }

  public boolean isMouseOver(){
    return (processing.mouseX <= this.x + width
        && processing.mouseX >= this.x)
        && (processing.mouseY <= this.y + height
        && processing.mouseY >= this.y);
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public static void setProcessing(processing.core.PApplet processing) {
    Button.processing = processing;
  }
}
