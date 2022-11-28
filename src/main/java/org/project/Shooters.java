package org.project;

import processing.core.PVector;

import java.awt.*;

public class Shooters extends Enemy{

  private final Color color = new Color(0xF32040);
  public Shooters(Window window) {
    super(window);
    velocity = 1.00f;
  }

  @Override
  public void draw() {
    window.fill(color.getRGB());
    super.draw();
  }

  @Override
  public void update(PVector playerPos) {
    Projectiles bullet = new Projectiles(this.position, playerPos);
  }
}
