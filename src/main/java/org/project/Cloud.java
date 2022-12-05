package org.project;

import processing.core.PImage;
import processing.core.PVector;

/**
 * Cloud is the standard platforms that the player must jump on to avoid
 * falling to the bottom. Clouds are spawned off-screen and advance to the left
 * side of the window to provide a side-scrolling look.
 *
 * @author Buck Sin and Lester Shun
 * @version 1.0
 */
public class Cloud extends AbstractObserver {
  protected float width;
  protected float height;
  protected final PImage cloudSprite;
  private float speedMultiplier;
  private float displacement;
  private boolean bounce;
  private boolean exited;

  /**
   * Constructor for Cloud object. Width, height, and positions are randomized.
   * The speed of the cloud will gradually grow faster as game difficulty increases.
   *
   * @param window PApplet Window that contains this object.
   */
  public Cloud(Window window) {
    this.window = window;
    this.width = (float) Math.random() * 70 * (4 - window.gameDifficulty) / 3 + 50;
    this.height = (float) Math.random() * 20 + 20;
    this.speedMultiplier = 1 + (float) (0.5 * window.gameDifficulty / 3);
    this.position = new PVector(window.width + width / 2,
        (window.height * 0.9f - (float) Math.random() * 3 * height));
    PVector newVector = new PVector(0, window.height).sub(position).normalize();
    newVector.mult(speedMultiplier);
    this.direction = newVector;
    this.direction.x *= this.speedMultiplier;
    cloudSprite = window.loadImage("images/cloud.png");
  }

  /**
   * Draws the Cloud in the window using the given sprite in image folder.
   * If player bounces on the cloud, a bounce animation will play.
   */
  @Override
  public void draw() {
    if (bounce) {
      position.y += displacement;
      displacement *= 0.8;
      if (displacement < 0.01) {
        displacement = 0;
        this.bounce = false;
      }
    }
    window.image(cloudSprite,
        position.x - width / 2, position.y - height / 2, width, height);
  }

  /**
   * Moves Cloud towards the left of Window and updates the new boundary values.
   * Once the Cloud moves past a certain threshold, it will be removed from
   * the sprites ArrayList in Window, and observers ArrayList in Player.
   */
  @Override
  public void move() {
    this.position = this.position.add(this.direction);
    if (position.y >= window.height - height) {
      this.position.y = window.height - height;
    }
    if (this.position.x < -width && this.exited == false) {
      this.exited = true;
      removeCloud();
    }
    leftBoundaryValue = this.position.x - width / 2;
    rightBoundaryValue = this.position.x + width / 2;
    topBoundaryValue = this.position.y - height / 2;
    bottomBoundaryValue = this.position.y + height / 2;
  }

  /**
   * sets the X-position of the cloud object.
   * @param xPosition represents a horizontal coordinate
   */
  public void setX(float xPosition) {
    this.position.x = xPosition;
  }

  /**
   * Adds Cloud to the Window's removeSpriteQueue.
   */
  public void removeCloud() {
    this.window.addToRemoveQueue(this);
  }

  /**
   * Toggles when to play bounce animation.
   */
  public void toggleBounce() {
    this.bounce = true;
    this.displacement = height / 10;
  }

  /**
   * Once Player's boundary touches the Cloud boundary. The cloud will send a response update
   * by prompting player to jump again and toggling the bouce animation.
   *
   * @param player Player object.
   */
  @Override
  public void update(Player player) {
    if ((player.rightBoundaryValue > this.leftBoundaryValue
        && player.rightBoundaryValue < this.rightBoundaryValue)
        || (player.leftBoundaryValue > this.leftBoundaryValue
        && player.leftBoundaryValue < this.rightBoundaryValue)) {
      if (Math.abs(bottomBoundaryValue - player.bottomBoundaryValue)
          < height && player.direction.y > 0) {
        player.jump();
        toggleBounce();
      }
    }
  }
}
