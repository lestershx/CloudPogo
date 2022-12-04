package org.project;

public class UpCloud extends Cloud {

  private float startingHeight;

  private final float floatRange = 275;

  public UpCloud(Window window) {
    super(window);
    direction.add(0f, -0.75f);
    startingHeight = position.y;
  }

  public void checkHeight() {
    if (position.y <= startingHeight - floatRange || position.y <= 100) {
      direction.add(0f,0.75f);
    } else if (position.y >= startingHeight || position.y >= window.height - 40) {
      direction.add(0f, -0.75f);
    }
  }

  @Override
  public void move(){
    checkHeight();
    super.move();
  }
}
