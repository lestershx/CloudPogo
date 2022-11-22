package org.project;

import processing.core.PVector;

import java.awt.*;

public class Walkers extends Enemy{
  private final Color color = new Color(0x00C821);
  public Walkers(float startingX, Window window) {
    super(startingX, window);
    velocity = 1f;
  }

  @Override
  public void draw() {
    super.draw();
//    window.fill(color.getRGB());
  }

  @Override
  public void move() {
    this.position = this.position.add(this.direction.mult(this.velocity));
  }
}
