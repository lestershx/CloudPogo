package org.project;

import processing.core.PVector;

public abstract class Character implements Drawable{
  protected PVector position;
  protected int direction;
  protected float velocity;
  protected Window window;
}
