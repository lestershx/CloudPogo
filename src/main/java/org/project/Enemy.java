package org.project;

import processing.core.PVector;

public class Enemy extends AbstractObserver{
  private float size = 8;
  public Enemy(float startingX, Window window) {
    this.window = window;
    this.position = new PVector(startingX, window.height - size);
    direction = new PVector(-1f, 0f).normalize();
  }

  @Override
  public void draw() {
    window.circle(position.x, position.y, size);
    window.fill(255);
  }

  @Override
  public void move() {

  }

  @Override
  public void update() {

  }
}
