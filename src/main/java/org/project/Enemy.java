package org.project;

import processing.core.PVector;

public class Enemy extends AbstractObserver{
  private float size = 10;
  public Enemy(Window window) {
    this.window = window;
    this.position = new PVector(window.width, window.height - size);
    direction = new PVector(-1f, 0f).normalize();
  }

  @Override
  public void draw() {
    window.circle(position.x, position.y, size);
  }

  @Override
  public void move() {
    this.position = this.position.add(this.direction.mult(this.velocity));
  }

  @Override
  public void update() {

  }

  @Override
  public void gravity() {

  }
}
