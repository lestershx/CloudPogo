package org.project;

import processing.core.PImage;
import processing.core.PVector;

public abstract class Cloud extends AbstractObserver{
  protected float width;

  protected float height;

  protected PImage cloudSprite;
  private float speedMultiplier;

  private float displacement;
  private boolean bounce;

  private boolean exited;

  private PVector imagePosition;

  public Cloud(Window window) {
    this.window = window;
    this.width = (float)Math.random() * 70 * (4-window.gameDifficulty) / 3 + 50;
    this.height = (float)Math.random() * 20 + 20;
    this.speedMultiplier = 1 + (float) (0.5 * window.gameDifficulty/3);
    this.position = new PVector(window.width + width/2,
        (window.height * 0.9f - (float)Math.random() * 3 * height));
    PVector newVector = new PVector(0,window.height).sub(position).normalize();
    newVector.mult(speedMultiplier);
    this.direction = newVector;
    this.direction.x *= this.speedMultiplier;
    cloudSprite = window.loadImage("images/cloud.png");
    this.imagePosition = this.position;
  }

  @Override
  public void draw() {
    if(bounce) {
      position.y += displacement;
      displacement *= 0.8;
      if(displacement < 0.01) {
        displacement = 0;
        this.bounce = false;
      }
    }
    window.image(cloudSprite, imagePosition.x - width/2, imagePosition.y - height/2, width, height);

  }

  @Override
  public void move() {
    this.position = this.position.add(this.direction);
    if (position.y >= window.height - height) {
      this.position.y = window.height - height;
    }
    if(this.position.x < -width && this.exited == false) {
      this.exited = true;
      removeCloud();
    }
    leftBoundaryValue = this.position.x - width/2;
    rightBoundaryValue = this.position.x + width/2;
    topBoundaryValue = this.position.y - height/2;
    bottomBoundaryValue = this.position.y + height/2;
  }

  public void removeCloud() {
    this.window.addToRemoveQueue(this);
  }

  public void toggleBounce() {
    this.bounce = true;
    this.displacement = height/10;
  }

  @Override
  public void update(Player player) {
    if((player.rightBoundaryValue > this.leftBoundaryValue
        && player.rightBoundaryValue < this.rightBoundaryValue)
        || (player.leftBoundaryValue > this.leftBoundaryValue
        && player.leftBoundaryValue < this.rightBoundaryValue)) {
      if(Math.abs(bottomBoundaryValue - player.bottomBoundaryValue) < height && player.direction.y > 0) {
        player.jump();
        toggleBounce();
      }
    }
  }
}
