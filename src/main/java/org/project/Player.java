package org.project;

import processing.core.PVector;

import java.awt.*;
import java.util.ArrayList;

public class Player extends AbstractObservable{
  private float width = 10;
  private float height = 20;
  private static Player instance;
  private final Color color = new Color(0xF3A245);

  private Player(Window window) {
    position = new PVector(0 + width, window.height - height);
    this.window = window;
    direction = 0;
    velocity = 10;
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

  }

  @Override
  public void registerObserver(AbstractObserver observer) {

  }

  @Override
  public void unregisterObserver(AbstractObserver observer) {

  }

  @Override
  public void notifyObservers() {

  }
}
