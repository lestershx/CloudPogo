package org.project;

import processing.core.PVector;

public abstract class Character implements Drawable {
  protected PVector position;
  protected PVector direction;
  protected float velocity;
  protected Window window;

  public PVector getPosition() {
    return position;
  }
  public PVector getDirection() {
    return direction;
  }
}
