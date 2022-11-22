package org.project;

import processing.core.PVector;

import java.awt.*;
import java.util.ArrayList;

public class Player extends AbstractObservable{
  private float width = 10f;
  private float height = 20f;
  private static Player instance;
  private final Color color = new Color(0xF3A245);

  private Player(Window window) {
    this.window = window;
    position = new PVector(0 + width, window.height - height);
    direction = new PVector(1f,0f).normalize();
    velocity = 1f;
    observers = new ArrayList<>();
  }

  public static Player getInstance(Window window) {
    if (instance == null) {
      instance = new Player(window);
    }
    return instance;
  }

  @Override
  public void draw() {
    window.rect(position.x, position.y, width, height);
    window.fill(color.getRGB(),100);
  }

  @Override
  public void move() {
    this.position = this.position.add(this.direction.mult(this.velocity));
  }

  @Override
  public void registerObserver(AbstractObserver observer) {
    observers.add(observer);
  }

  @Override
  public void unregisterObserver(AbstractObserver observer) {
    observers.remove(observer);
  }

  @Override
  public void notifyObservers() {

  }
}
