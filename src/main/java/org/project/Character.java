package org.project;

import processing.core.PVector;

public abstract class Character implements Drawable, Collidable {
  protected PVector position;
  protected PVector direction;
  protected float velocity;
  protected Window window;

  protected float leftBoundaryValue;
  protected float rightBoundaryValue;
  protected float topBoundaryValue;
  protected float bottomBoundaryValue;

  public abstract void gravity();

  public PVector getPosition() {
    return position;
  }

  public PVector getDirection() {
    return direction;
  }
}
