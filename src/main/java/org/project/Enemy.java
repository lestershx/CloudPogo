package org.project;

import processing.core.PImage;
import processing.core.PVector;

public abstract class Enemy extends AbstractObserver{
  protected float width;

  protected float height;

  protected PImage cloudSprite;

  private float leftBoundaryValue;

  private float rightBoundaryValue;

  private float topBoundaryValue;

  private float bottomBoundaryValue;

  public Enemy(Window window) {
    this.window = window;
    this.width = (float)Math.random() * 70 * (4-window.gameDifficulty) / 3 + 50;
    this.height = (float)Math.random() * 20 + 20;
    this.position = new PVector(window.width + width/2, (1/window.gameDifficulty)
        * (window.height - (float)Math.random() * 2 * height));
    PVector newVector = new PVector(0,window.height).sub(this.getPosition()).normalize();
    direction = newVector;
    cloudSprite = window.loadImage("images/cloud.png");
  }

  @Override
  public void draw() {
//    window.rect(position.x - width/2, position.y - height/2, width, height);
    window.image(cloudSprite, position.x - width/2, position.y - height/2,width, height);
//    window.rect(position.x - width/2, position.y - height/2, width, height, 8f);
  }

  @Override
  public void move() {
    this.position = this.position.add(this.direction.mult(this.velocity));
    if (position.y >= window.height - height) {
      this.position.y = window.height - height;
    }
    leftBoundaryValue = this.position.x - width/2;
    rightBoundaryValue = this.position.x + width/2;
    topBoundaryValue = this.position.y - height/2;
    bottomBoundaryValue = this.position.y + height/2;
  }

  @Override
  public void update(Player player) {
    if((player.rightBoundaryValue > this.leftBoundaryValue
        && player.rightBoundaryValue < this.rightBoundaryValue)
        || (player.leftBoundaryValue > this.leftBoundaryValue
        && player.leftBoundaryValue < this.rightBoundaryValue)) {
      if(Math.abs(bottomBoundaryValue - player.bottomBoundaryValue) < height && player.direction.y > 0) {
        player.jump(1);
      }
    }
  };

  @Override
  public void gravity() {
    if (position.y < window.height - height) {
      this.direction = direction.add(new PVector(0f,0.5f));
    }
  }
}
