package org.project;

import processing.core.PVector;

public abstract class Character implements Drawable {
  protected PVector position;
  protected PVector direction;
  protected float velocity;
  protected Window window;

  protected float leftBoundaryValue;
  protected float rightBoundaryValue;
  protected float topBoundaryValue;
  protected float bottomBoundaryValue;

  public PVector getPosition() {
    return position;
  }
  public PVector getDirection() {
    return direction;
  }
}
