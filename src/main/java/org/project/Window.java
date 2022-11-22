package org.project;

import processing.core.PApplet;
import processing.event.KeyEvent;

import java.util.ArrayList;

public class Window extends PApplet{
  private Player player;
  private ArrayList<Enemy> enemies;

  /**
   * Sets the size of the applet window.
   */
  public void settings() {
    size(640, 360);
  }

  public void setup() {
    player = Player.getInstance(this);
  }

  public void draw() {
    player.draw();
    background(0);
  }

  /**
   * Handles arrowkey inputs for player movement.
   *
   * @param key **keypress event object**
   */
  public void keyPressed(KeyEvent key) {
//    if (player == null) {
//      return;
//    } else {
//      switch (key.getKeyCode()) {
//        case RIGHT:
//          player.setDirection(0);
//          player.move();
//          break;
//        case LEFT:
//          player.setDirection(1);
//          player.move();
//          break;
//        case DOWN:
//          player.setDirection(2);
//
//          player.move();
//          break;
//        case UP:
//          player.setDirection(3);
//
//          player.move();
//          break;
//        default:
//          break;
//      }
//    }
  }

  /**
   * Main function.
   *
   * @param args arguments from command line
   */
  public static void main(String[] args) {
    String[] appletArgs = new String[]{"window"};
    Window window = new Window();
    PApplet.runSketch(appletArgs, window);
  }
}