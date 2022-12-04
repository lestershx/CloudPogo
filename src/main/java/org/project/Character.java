package org.project;

import processing.core.PVector;

/**
 * Character is an abstract class that represents anything that will be drawn in window,
 * as well as having common variables such as a position, direction, velocity, a window,
 * and left,right,top, and bottom boundary values.
 *
 * @author Buck Sin and Lester Shun
 * @version 1.0
 */
public abstract class Character implements Drawable {
  protected PVector position;
  protected PVector direction;
  protected float velocity;
  protected Window window;
  protected float leftBoundaryValue;
  protected float rightBoundaryValue;
  protected float topBoundaryValue;
  protected float bottomBoundaryValue;

  /**
   * Getter method for AbstractCharacter's current position.
   *
   * @return current position as PVector.
   */
  public PVector getPosition() {
    return position;
  }

  /**
   * Getter method for AbstractCharacter's current direction.
   *
   * @return current direction as PVector.
   */
  public PVector getDirection() {
    return direction;
  }
}
