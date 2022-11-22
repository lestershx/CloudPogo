package org.project;

import processing.core.PVector;

import java.awt.*;
import java.util.ArrayList;

public class Player extends AbstractObservable{
  private float width = 10f;
  private float height = 20f;

  private float jumpHeight = 200;
  private static Player instance;
  private final Color color = new Color(0xF3A245);

  private PlayerDeathEventListener deathListener;

  private Player(Window window) {
    this.window = window;
    position = new PVector(0 + width, window.height - height);
    direction = new PVector(1f,0f).normalize();
    velocity = 1f;
    observers = new ArrayList<>();
  }

  public void registerDeathListener(PlayerDeathEventListener dlistener) {
    deathListener = dlistener;
  }

  public void playerDeath() {
    System.out.println("GAME OVER BRO");
    deathListener.onEvent();
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
    if (outOfBounds()) {
      this.direction = new PVector(0f, 0f);
    }
  }

//  public void jump() {
//    while (!(position.y < window.height - height - jumpHeight)) {
//      this.direction = new PVector(0f,-1.005f).normalize();
//    }
//  }

  public void setDirection(int direction) {
    switch (direction) {
      case 0:
        this.direction = new PVector(1f,0f).normalize();
        break;
      case 1:
        this.direction = new PVector(-1f,0f).normalize();
        break;
      case 2:
        this.direction = new PVector(0f,-1.005f).normalize();
        break;
    }
  }

  private boolean outOfBounds() {
    if ((this.position.x > window.width
        || this.position.x < 0)
        || (this.position.y > window.height
        || this.position.y <= 0)) {
      return true;
    } else {
      return false;
    }
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
