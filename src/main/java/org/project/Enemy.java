package org.project;

import processing.core.PVector;

public abstract class Enemy extends AbstractObserver{
  protected float size = 10;
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
    if (position.y >= window.height - size) {
      this.position.y = window.height - size;
    }
  }

  @Override
  public abstract void update(PVector playerPos);

  @Override
  public void gravity() {
    if (position.y < window.height - size) {
      this.direction = direction.add(new PVector(0f,0.5f));
    }
  }
}
