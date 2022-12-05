package org.project;

/**
 * UpCloud is a subclass of Cloud that inherits all of Cloud's behaviours.
 * However also has additional behaviours such as moving up and down within a floatRange
 * as it is advancing past the screen.
 *
 * @author Buck Sin and Lester Shun
 * @version 1.0
 */
public class UpCloud extends Cloud {

  private final float startingHeight;
  private final float floatRange = 275;

  /**
   * Constructor of UpCloud that adds a new upwards y-component to the current direction.
   *
   * @param window PApplet Window that contains this object.
   */
  public UpCloud(Window window) {
    super(window);
    direction.add(0f, -0.75f);
    startingHeight = position.y;
  }

  /**
   * Check the current of UpCloud object. If it is past the top or bottom threshold of floatRange,
   * The y-component of direction will reverse.
   */
  public void checkHeight() {
    if (position.y <= startingHeight - floatRange || position.y <= 100) {
      direction.add(0f, 0.75f);
    } else if (position.y >= startingHeight || position.y >= window.height - 40) {
      direction.add(0f, -0.75f);
    }
  }

  /**
   * Moves similar to Cloud superclass, but will also check the current height with each move().
   */
  @Override
  public void move() {
    checkHeight();
    super.move();
  }

  /**
   * Getter method for the UpCloud's starting height.
   * Used mainly for testing purposes.
   *
   * @return cloud's starting (spawn) height as a float
   */
  public float getStartingHeight() {
    return startingHeight;
  }
}
