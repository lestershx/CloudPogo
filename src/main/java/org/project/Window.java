package org.project;

import processing.event.KeyEvent;
import processing.core.*;
import java.util.ArrayList;
import java.util.Collections;
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
  public float gameDifficulty;

  private boolean showScore;

  public ArrayList<Integer> scoreList;

  /**
   * Sets the size of the applet window.
   */
  public void settings() {
    size(640, 640);
  }

  public void setup() {
    characters = new ArrayList<>();
    player = Player.getInstance(this);
    player.registerDeathListener(new PlayerDeathEventListener(this));
    connection = new MongoConnection(this);
    enemyTimer = 0;
//    enemies = new ArrayList<>();
    gameState = 0;
    f = createFont("Arial",16,true);
    score = 0;
    gameDifficulty = 1;
    scoreList = new ArrayList<Integer>();
    showScore = false;
  }

  private void spawnEnemy(int enemyTimer) {
//    int randomEnemy = randomizer.nextInt();
    if (enemyTimer % 150 != 0) {
      Walkers walker = new Walkers(this);
      characters.add(walker);
//      enemies.add(walker);
      player.registerObserver(walker);
    } else {
      Jumpers jumper = new Jumpers(this);
      characters.add(jumper);
//      enemies.add(jumper);
      player.registerObserver(jumper);
    }
  }

  public void draw() {
    // game intro state
    if(gameState == 0) {
      background(0);
      player.draw();
      enemyTimer++;
      if (enemyTimer % 100 == 0) {
        spawnEnemy(enemyTimer);
      }
      for (Character c : characters) {
        c.draw();
        c.move();
      }
      textFont(f, 22);
      fill(255);
      text("CLOUD POGO",width/2 - 160f,height/4);
      text("By Lester and Buck",width/2 - 160f,height/4 + 30f);
      text("Dont hit the ground,\nbounce off the clouds",width/2 - 160f,height/3 + 20f);
      text("Press any directional\nkey to start >>",width/2 - 160f,height/3 + 82f);
    }
    // game in play state
    if(gameState == 1) {
      background(0);
      player.notifyObservers();
      player.draw();
      player.move();
      player.gravity();
      for (Character c : characters) {
        c.draw();
        c.move();
      }
      if (enemyTimer % 100 == 0) {
        spawnEnemy(enemyTimer);
      }
      enemyTimer++;
      if(enemyTimer % 1000 == 0) {
        if(gameDifficulty < 3) {
          gameDifficulty+=0.1;
        }
      }
      score++;
      textFont(f,16);
      fill(255);
      text("Score: " + score,550,20);
    }
    //game in pause state
    if(gameState == -1) {
      player.draw();
      for (Character c : characters) {
        c.draw();
      }
      text("Paused",width/2,height/2);
      text("Score: " + score,550,20);
    }
    //game in end state
    if(gameState == -2) {
      for (Character c : characters) {
        c.draw();
//        c.move();
      }
      textFont(f, 24);
      fill(255);
      text("GAME OVER BRO :(",width/2 - 100f,height/5);
      if(showScore) {
        textFont(f, 18);
        text("Top Scores",width/2 - 50,170);
        text("1: " + scoreList.get(0),220,200);
        text("2: " + scoreList.get(1),220,220);
        text("3: " + scoreList.get(2),220,240);
        text("4: " + scoreList.get(3),220,260);
        text("5: " + scoreList.get(4),220,280);
        text("6: " + scoreList.get(5),220,300);
        text("7: " + scoreList.get(6),220,320);
        text("8: " + scoreList.get(7),220,340);
        text("9: " + scoreList.get(8),220,360);
        text("10:" + scoreList.get(9),220,380);
      }
    }
  }

  /**
   * Handles arrowkey inputs for player movement.
   *
   * @param key **keypress event object**
   */
  public void keyPressed(KeyEvent key) {
    if(gameState == 0) {
      gameState = 1;
    }
    switch (key.getKeyCode()) {
      case ENTER:
//        endGame();
        if(gameState >= 0) {
          gameState = -1;
        } else if(gameState == -1){
          gameState = 1;
        }
        break;
      case RIGHT:
        player.setDirection(0);
        break;
      case LEFT:
        player.setDirection(1);
        break;
      case ' ':
        player.jump(0);
        break;
      case UP:
        player.jump(0);
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
    gameState = -2;
    player.playerDeath();
    connection.connect(this.score);
  }

  public void addScoreToList(Integer num) {
    this.scoreList.add(num);
  }

  public void sortScoreList() {
    Collections.sort(this.scoreList, Collections.reverseOrder());
    System.out.println("scores sorted");
    showScore = true;
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