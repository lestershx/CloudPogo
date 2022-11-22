package org.project;

import processing.core.PVector;

public abstract class Character implements Drawable{
  private PVector position;
  private PVector direction;
  private float size;
  private Window window;

  public Character() {
    
  }
}
