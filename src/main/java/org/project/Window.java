package org.project;

import processing.event.KeyEvent;
import processing.core.*;
import java.util.ArrayList;
import java.util.Random;


public class Window extends PApplet{
  private Player player;
  private ArrayList<Enemy> enemies;
  private ArrayList<Character> characters;
  private int enemyTimer;
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
    characters = new ArrayList<>();
    player = Player.getInstance(this);
    characters.add(player);
    player.registerDeathListener(new PlayerDeathEventListener(this));
    connection = new MongoConnection();
    enemyTimer = 0;
//    enemies = new ArrayList<>();
    gameState = 1;
    f = createFont("Arial",16,true);
    score = 0;
  }

  private void spawnEnemy() {
    int enemyRandomizer = randomizer.nextInt();
    if (enemyRandomizer % 2 == 0) {
      Walkers walker = new Walkers(this);
      characters.add(walker);
//      enemies.add(walker);
      player.registerObserver(walker);
    } else if (enemyRandomizer % 3 == 0) {
      Jumpers jumper = new Jumpers(this);
      characters.add(jumper);
//      enemies.add(jumper);
      player.registerObserver(jumper);
    } else if (enemyRandomizer % 5 == 0) {
      Shooters shooter = new Shooters(this);
      characters.add(shooter);
      player.registerObserver(shooter);
    }
  }

  public void draw() {
    if(gameState == 1) {
//      player.draw();
      background(0);
//      player.draw();
//      player.move();
      player.notifyObservers();
//      for (Enemy e: enemies) {
//        e.draw();
//        e.move();
//      }
      for (Character c : characters) {
        c.draw();
        c.move();
        c.gravity();
      }
//      player.gravity();
      if (enemyTimer % 80 == 0) {
        spawnEnemy();
      }
      enemyTimer++;
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
        player.jump();
        break;
      case DOWN:
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