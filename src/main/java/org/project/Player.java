package org.project;

import processing.core.PImage;
import processing.core.PVector;
import processing.core.*;

import java.awt.*;
import java.util.ArrayList;

public class Player extends AbstractObservable{
  private static Player instance;
  private final float width = 35f;
  private final float height = 35f;
  private PImage sprite;
  public float leftBoundaryValue;

  public float rightBoundaryValue;

  public float topBoundaryValue;

  public float bottomBoundaryValue;

  private int jumpNum;

  private final Color color = new Color(0xF3A245);
  private final float broadcastRange = 150f;

  private Player(Window window) {
    this.window = window;
    position = new PVector(window.width/2, window.height/2);
    direction = new PVector(0f,0f).normalize();
    velocity = 1f;
    observers = new ArrayList<>();
    jumpNum = 0;
    sprite = window.loadImage("images/character_sprite.png");
  }

  public static Player getInstance(Window window) {
    if (instance == null) {
      instance = new Player(window);
    }
    return instance;
  }

  @Override
  public void draw() {
//    window.fill(color.getRGB());
//    window.rect(position.x-width/2, position.y-height/2, width, height);
    window.image(sprite, position.x-width/2, position.y-height/2, width, height);
  }

  @Override
  public void move() {
    this.position = this.position.add(this.direction.mult(this.velocity));
    if(Math.abs(this.direction.x) > 0.05f) {
      this.direction.x = this.direction.x * 0.98f;
    } else {
      setDirection(2);
    }
    if (outOfBoundsX()) {
      if(this.position.x >= window.width) {
        this.position.x = window.width;
      } else if(this.position.x <= 0){
        this.position.x = 0;
      }
    }
    if (outOfBoundsY()) {
      window.endGame();
    }

    leftBoundaryValue = this.position.x - width/2;
    rightBoundaryValue = this.position.x + width/2;
    topBoundaryValue = this.position.y - height/2;
    bottomBoundaryValue = this.position.y + height/2;
  }

  public void jump(int bypass) {
    if (bypass == 1 || position.y >= window.height - height) {
//      this.position.y -= 30f;
      this.direction.y = 0;
      this.direction.y -=15f;
//      this.direction.add(new PVector(0f,-12.00f));
    }
  }

  public void gravity() {
    if (position.y < window.height - height) {
      this.direction = direction.add(new PVector(0f,0.5f));
    }
  }

  public void setDirection(int direction) {
    switch (direction) {
      case 0: // Go right
        if(this.direction.x < 4) {
          this.direction.x += 1.5f;
        }
        break;
      case 1: // Go left
        if(this.direction.x > -4) {
          this.direction.x -= 1.5f;
        }
        break;
      case 2: //Stay Put
        this.direction.x = 0f;
    }
  }

  private boolean outOfBoundsX() {
    return (this.rightBoundaryValue >= window.width
        || this.leftBoundaryValue < 0);
  }
  private boolean outOfBoundsY() {
    return (this.bottomBoundaryValue > window.height);
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
        o.update(this);
      }
    }
  }
}
