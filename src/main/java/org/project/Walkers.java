package org.project;

import processing.core.PVector;

import java.awt.*;

public class Walkers extends Enemy{
  private final Color color = new Color(0x00C821);
  public Walkers(Window window) {
    super(window);
    velocity = 1f;
  }

  @Override
  public void draw() {
    window.fill(color.getRGB());
    super.draw();
  }

  @Override
  public void update(PVector position) {

  }
}
