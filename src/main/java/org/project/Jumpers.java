package org.project;

import processing.core.PVector;

import java.awt.*;

public class Jumpers extends Enemy{

  private final Color color = new Color(0x45DFF3);

  public Jumpers(Window window) {
    super(window);
    velocity = 1.0005f;
  }

  @Override
  public void draw() {
    window.fill(color.getRGB());
    super.draw();
  }

  public void jump() {
    if (position.y >= window.height - size) {
      this.direction.add(new PVector(0f,-5.50f));
    }
  }

  @Override
  public void update(PVector position) {
    jump();
  }
}
