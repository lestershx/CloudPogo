package org.project;

import java.util.ArrayList;
import processing.core.PImage;
import processing.core.PVector;

/**
 * Player is the player controllable character of the game, as a Singleton object.
 * The player can jump left and right, and will be pulled down by gravity.
 *
 * @author Buck Sin and Lester Shun
 * @version 1.0
 */
public class Player extends AbstractObservable {
  private static Player instance;
  private final float width = 35f;
  private final float height = 35f;
  private final PImage sprite;

  /**
   * Constructor for Player object that determines starting position, direction, and velocity.
   *
   * @param window PApplet Window that contains this object.
   */
  private Player(Window window) {
    this.window = window;
    position = new PVector(window.width / 2f, window.height / 5f);
    direction = new PVector(0f, 0f).normalize();
    velocity = 1f;
    observers = new ArrayList<>();
    sprite = window.loadImage("images/character_sprite.png");
  }

  /**
   * Returns a single instance of Player to ensure there will only be one Player object
   * at a time (Singleton).
   *
   * @param window PApplet Window that contains this object.
   * @return Player as a singleton object.
   */
  public static Player getInstance(Window window) {
    if (instance == null) {
      instance = new Player(window);
    }
    return instance;
  }

  /**
   * Draws the Player in the window using the given sprite in image folder.
   */
  @Override
  public void draw() {
    window.image(sprite, position.x - width / 2, position.y - height / 2, width, height);
  }

  /**
   * Moves Player position based on the current direction, multiplied by the velocity.
   * Also checks for when the Player moves out of bounds. If the Player is out of bounds on
   * the x axis, it will not allow the Player to move further. If the Player touches the
   * bottom bound (window.height) the game will end.
   */
  @Override
  public void move() {
    this.position = this.position.add(this.direction.mult(this.velocity));
    if (Math.abs(this.direction.x) > 0.05f) {
      this.direction.x = this.direction.x * 0.98f;
    } else {
      setDirection(2);
    }
    if (outOfBoundsX()) {
      if (this.position.x >= window.width) {
        this.position.x = window.width;
      } else if (this.position.x <= 0) {
        this.position.x = 0;
      }
    }
    if (outOfBoundsY()) {
      window.endGame();
    }
    leftBoundaryValue = this.position.x - width /  2;
    rightBoundaryValue = this.position.x + width / 2;
    topBoundaryValue = this.position.y - height / 2;
    bottomBoundaryValue = this.position.y + height / 2;
  }

  /**
   * Player will jump upwards by a certain amount.
   */
  public void jump() {
    this.direction.y = 0;
    this.direction.y -= 15f;
  }

  /**
   * Constant downward force applied to the Player.
   */
  public void gravity() {
    this.direction = direction.add(new PVector(0f, 0.5f));
  }

  /**
   * Sets the direction of the Player.
   *
   * @param direction Direction input from the player as an int.
   */
  public void setDirection(int direction) {
    switch (direction) {
      case 0: // Go right
        if (this.direction.x < 4) {
          this.direction.x += 1.5f;
        }
        break;
      case 1: // Go left
        if (this.direction.x > -4) {
          this.direction.x -= 1.5f;
        }
        break;
      case 2: //Stay Put
        this.direction.x = 0f;
        break;
      default:
        break;
    }
  }

  /**
   * Checks if the Player's current x position is the x bounds of window.
   *
   * @return true if within (0, window.width], false otherwise.
   */
  private boolean outOfBoundsX() {
    return (this.rightBoundaryValue >= window.width
        || this.leftBoundaryValue < 0);
  }

  /**
   * Checks if Player's current y position is past the window's bottom border (window.height).
   *
   * @return true if y position is larger than window height.
   */
  private boolean outOfBoundsY() {
    return (this.bottomBoundaryValue > window.height);
  }

  /**
   * Registers Observers (clouds) to observer ArrayList (found in AbstractObservable).
   *
   * @param observer extension of AbstractObserver (usually of superclass Cloud).
   */
  @Override
  public void registerObserver(AbstractObserver observer) {
    observers.add(observer);
  }

  /**
   * Unregisters Observers (clouds) to observer ArrayList (found in AbstractObservable).
   *
   * @param observer extension of AbstractObserver (usually of superclass Cloud).
   */
  @Override
  public void unregisterObserver(AbstractObserver observer) {
    observers.remove(observer);
  }

  /**
   * Notify all observers when Player is within the established range to update.
   */
  @Override
  public void notifyObservers() {
    float broadcastRange = 80f;
    for (AbstractObserver o : observers) {
      if (o.position.x < position.x + broadcastRange
          && o.position.x > position.x - broadcastRange
          && o.position.y < position.y + broadcastRange
          && o.position.y > position.y - broadcastRange) {
        o.update(this);
      }
    }
  }

  /**
   * Method that resets the singleton instance.
   * Used to reset Player character after game over and also J-Unit testing.
   */
  public static void resetSingleton() {
    instance = null;
  }
}
