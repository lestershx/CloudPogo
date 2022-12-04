package org.project;

import processing.event.KeyEvent;
import processing.core.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Window extends PApplet{
  private Player player;
  private ArrayList<Character> sprites;
  private ArrayList<Character> removeSpriteQueue;
  private int cloudTimer;
  private int gameState;
  public MongoConnection connection;
  private Random randomizer = new Random();
  private PFont f;
  private PImage background;
  public int score;
  public float gameDifficulty;

  private boolean showScore;

  public ArrayList<Integer> scoreList;

  /**
   * Sets the size of the applet window.
   */
  public void settings() {
    size(1249, 576);
  }

  public void setup() {
    background = loadImage("images/background.png");
    sprites = new ArrayList<>();
    removeSpriteQueue = new ArrayList<>();
    player = Player.getInstance(this);
    connection = MongoConnection.getInstance(this);
    cloudTimer = 0;
    gameState = 0;
    f = createFont("Arial",16,true);
    score = 0;
    gameDifficulty = 1;
    scoreList = new ArrayList<Integer>();
    showScore = false;
  }

  private void spawnCloud(int cloudTimer) {
    int randomInt = randomizer.nextInt();
    if (cloudTimer % 150 != 0 && randomInt % 2 == 0) {
      UpCloud up = new UpCloud(this);
      sprites.add(up);
      player.registerObserver(up);
    } else if (cloudTimer % 150 != 0 && randomInt % 2 != 0){
      MovingCloud mCloud = new MovingCloud(this);
      sprites.add(mCloud);
      player.registerObserver(mCloud);
    } else {
      NormalCloud nCloud = new NormalCloud(this);
      sprites.add(nCloud);
      player.registerObserver(nCloud);
    }
  }

  public void addToRemoveQueue(Character c) {
    removeSpriteQueue.add(c);
  }

  private void removeCloud() {
    if(removeSpriteQueue.size() > 0) {
      for(Character c : removeSpriteQueue) {
        sprites.remove(c);
        player.unregisterObserver((AbstractObserver) c);
      }
    }
  }

  /**
   *
   * NOTE: Side scrolling background implemented using code found on Processing forum:
   * https://forum.processing.org/two/discussion/20079/how-can-i-make-an-endless-scrolling-background.html
   */
  public void draw() {
    removeCloud();

    // Side scrolling background
    background(0);
    image(background, 0, 0);
    int x = frameCount % background.width;
    copy(background, x, 0, background.width, height, 0, 0, background.width, height);
    int x2 = background.width - x;
    if (x2 < width) {
      copy(background, 0, 0, background.width, height, x2, 0, background.width, height);
    }

    // game intro state
    if(gameState == 0) {
      player.draw();
      cloudTimer++;
      if (cloudTimer % 150 == 0) {
        spawnCloud(cloudTimer);
      }
      for (Character c : sprites) {
        c.draw();
        c.move();
      }
      textFont(f, 22);
      fill(255);
      text("CLOUD POGO",width/2 - 170f,height/5);
      text("By Lester and Buck",width/2 - 170f,height/4 + 25f);
      text("Dont hit the ground,\nbounce off the clouds",width/2 - 170f,height/3 + 10f);
      text("Press any directional\nkey to start >>",width/2 - 170f,height/3 + 70f);
    }
    // game in play state
    if(gameState == 1) {
      player.notifyObservers();
      player.draw();
      player.move();
      player.gravity();
      for (Character c : sprites) {
        c.draw();
        c.move();
      }
      if (cloudTimer % 100 == 0) {
        spawnCloud(cloudTimer);
      }
      cloudTimer++;
      if(cloudTimer % 1000 == 0) {
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
      for (Character c : sprites) {
        c.draw();
      }
      text("Paused",width/2,height/2);
      text("Score: " + score,550,20);
    }
    //game in end state
    if(gameState == -2) {
      for (Character c : sprites) {
        c.draw();
      }
      textFont(f, 24);
      fill(255);
      text("GAME OVER BRO :(",width/2 - 100f,height/6);
      text("Your score: " + score,width/2 - 100f,height/6 + 40);
      if(showScore) {
        textFont(f, 18);
        text("Top Scores",width/2 - 50,170);
        text("1: " + scoreList.get(0),width/2 - 50,200);
        text("2: " + scoreList.get(1),width/2 - 50,220);
        text("3: " + scoreList.get(2),width/2 - 50,240);
        text("4: " + scoreList.get(3),width/2 - 50,260);
        text("5: " + scoreList.get(4),width/2 - 50,280);
        text("6: " + scoreList.get(5),width/2 - 50,300);
        text("7: " + scoreList.get(6),width/2 - 50,320);
        text("8: " + scoreList.get(7),width/2 - 50,340);
        text("9: " + scoreList.get(8),width/2 - 50,360);
        text("10:" + scoreList.get(9),width/2 - 50,380);
      }
      text("Press [SPACEBAR] to play again!",width/2f - 100f,420);
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
        if (gameState == -2) {
          Player.resetSingleton();
          setup();
        }
      default:
        break;
    }
  }

  public void endGame() {
    gameState = -2;
    connection.connect(this.score);
  }

  public void addScoreToList(Integer num) {
    this.scoreList.add(num);
  }

  public void sortScoreList() {
    Collections.sort(this.scoreList, Collections.reverseOrder());
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