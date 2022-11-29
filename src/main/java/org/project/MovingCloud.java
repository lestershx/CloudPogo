package org.project;

import processing.core.PVector;

import java.awt.*;

public class MovingCloud extends Cloud {

  private final Color color = new Color(0x45DFF3);

  public MovingCloud(Window window) {
    super(window);
    velocity = 1.00075f;
  }

  @Override
  public void draw() {
    window.fill(color.getRGB());
    super.draw();
  }
}
