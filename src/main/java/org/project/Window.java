package org.project;

import processing.event.KeyEvent;
import processing.core.*;
import java.util.ArrayList;
import java.util.Random;


public class Window extends PApplet{
  private Player player;
  private Walkers walker;
  private ArrayList<Enemy> enemies;
  private int gameState;
  public MongoConnection connection;
  private Random randomizer = new Random();
  private PFont f;
  public int score;

  /**
   * Sets the size of the applet window.
   */
  public void settings() {
    size(640, 360);
  }

  public void setup() {
    player = Player.getInstance(this);
    player.registerDeathListener(new PlayerDeathEventListener(this));
    connection = new MongoConnection();
    enemies = new ArrayList<>();
    float enemyStartX = random(width / 2f, width);
    walker = new Walkers(enemyStartX, this);
    enemies.add(walker);
    gameState = 1;
    f = createFont("Arial",16,true);
    score = 0;
  }

  private void spawnEnemy() {
    walker = new Walkers(width, this);
    enemies.add(walker);
  }

  public void draw() {
    if(gameState == 1) {
      player.draw();
      background(0);
      player.draw();
      player.move();
      for (Enemy e: enemies) {
        e.draw();
        e.move();
      }
//      player.move();
//    walker.move();
      if (randomizer.nextInt(50) % 50 == 0) {
        spawnEnemy();
      }
      score++;
      textFont(f,16);                  // STEP 3 Specify font to be used
      fill(255);                         // STEP 4 Specify font color
      text("Score: " + score,550,20);
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
        endGame();
        break;
      case RIGHT:
        player.setDirection(0);
        break;
      case LEFT:
        player.setDirection(1);
        break;
      case UP:
        player.setDirection(2);
        break;
//        default:
//          break;
//      }
//    }
    }
  }

  public void endGame() {
    gameState = 0;
    player.playerDeath();
    connection.connect(this.score);
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