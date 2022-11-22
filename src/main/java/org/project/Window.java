package org.project;

import processing.core.PApplet;
import processing.core.PVector;
import processing.event.KeyEvent;

import java.util.ArrayList;
import java.util.Random;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Window extends PApplet{
  private Player player;
  private Walkers walker;
  private ArrayList<Enemy> enemies;
  private SocketClientSync connection;

  private Random randomizer = new Random();

  /**
   * Sets the size of the applet window.
   */
  public void settings() {
    size(640, 360);
  }

  public void setup() {
    player = Player.getInstance(this);
    connection = new SocketClientSync();
    enemies = new ArrayList<>();
    float enemyStartX = random(width / 2f, width);
    walker = new Walkers(enemyStartX, this);
    enemies.add(walker);
  }

  private void spawnEnemy() {
    walker = new Walkers(width, this);
    enemies.add(walker);
  }

  public void draw() {
    player.draw();
    background(0);
    player.draw();
    player.move();
    for (Enemy e: enemies) {
      e.draw();
      e.move();
    }
    player.move();
//    walker.move();
    if (randomizer.nextInt(50) % 50 == 0) {
      spawnEnemy();
    }
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
      switch (key.getKeyCode()) {
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
        case ENTER:
          System.out.println("server connection test");
          try{
            connection.contactServer("hello");
          }catch(IOException | InterruptedException | ClassNotFoundException e){
            System.out.println(e);
          }
          break;
//        default:
//          break;
//      }
//    }
  }

  SocketClientSync getConnection () {
    return connection;
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