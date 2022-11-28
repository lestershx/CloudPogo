package org.project;

import processing.core.PVector;

import java.awt.*;
import java.util.ArrayList;

public class Player extends AbstractObservable{
  private static Player instance;
  private final float width = 10f;
  private final float height = 20f;
  private final float jumpHeight = -8.05f;
  private final Color color = new Color(0xF3A245);
  private final float broadcastRange = 150f;
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
    window.fill(color.getRGB());
    window.rect(position.x, position.y, width, height);
  }

  @Override
  public void move() {
    this.position = this.position.add(this.direction.mult(this.velocity));
    if (outOfBounds()) {
      if (position.y >= window.height - height) {
        this.position.y = window.height - height;
      }
      this.direction = new PVector(0f, 0f);
    }
  }

  public void jump() {
    if (position.y >= window.height - height) {
      this.direction.add(new PVector(0f,jumpHeight));
    }
  }

  @Override
  public void gravity() {
    if (position.y < window.height - height) {
      this.direction = direction.add(new PVector(0f,0.5f));
    }
  }

  public void setDirection(int direction) {
    switch (direction) {
      case 0: // Go left
        this.direction.x = 1f;
        break;
      case 1: // Go right
        this.direction.x = -1f;
        break;
      case 2: //Stay Put
        this.direction.x = 0f;
    }
  }

  private boolean outOfBounds() {
    return (this.position.x >= window.width - width
        || this.position.x < 0)
        || (this.position.y > window.height - height
        || this.position.y <= 0);
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
    for(AbstractObserver o : observers) {
      if (o.getPosition().x < position.x + broadcastRange
          && o.getPosition().x > position.x - broadcastRange
          && o.getPosition().y < position.y + broadcastRange
          && o.getPosition().y > position.y - broadcastRange) {
        o.update(this.position);
      }
    }
  }
}
