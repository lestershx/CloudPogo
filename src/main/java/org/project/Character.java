package org.project;

import processing.core.PVector;

public abstract class Character implements Drawable{
  protected PVector position;
  protected PVector direction;
  protected float velocity;
  protected Window window;

  public void fall() {
    this.direction = new PVector(0f,1f).normalize();
  }
}
