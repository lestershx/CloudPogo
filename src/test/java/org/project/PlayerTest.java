package org.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import processing.core.PVector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the methods of Player class
 * NOTE: Before testing, comment out lines 18, 31 and 53 in Player,
 * and lines 11, 32 and 46 in Cloud as PImage interferes with testing.
 */
class PlayerTest {
  Window window;
  Player player;

  @BeforeEach
  void setUp() {
    window = new Window();
    Player.resetSingleton();
    player = Player.getInstance(window);
  }

  @Test
  void setDirectionRight() {
    assertEquals(new PVector(0f,0f).normalize(), player.getDirection());
    player.setDirection(0);
    assertEquals(new PVector(1.5f, 0f), player.getDirection());
  }

  @Test
  void setDirectionLeft() {
    assertEquals(new PVector(0f,0f).normalize(), player.getDirection());
    player.setDirection(1);
    assertEquals(new PVector(-1.5f, 0f), player.getDirection());
  }

  @Test
  void testMoveRight() {
    assertEquals(new PVector(window.width / 2f, window.height / 2f), player.getPosition());
    player.setDirection(0);
    player.move();
    assertEquals(new PVector(window.width / 2f + 1.5f, window.height / 2f), player.getPosition());
  }

  @Test
  void testMoveRightOutOfBounds() {
    PVector atRightBorder = new PVector(window.width, player.getPosition().y);
    player.setDirection(0);
    for (int i = 1; i < 100; i++) {
      player.move();
    }
    assertEquals(atRightBorder, player.getPosition());
  }

  @Test
  void testMoveLeft() {
    assertEquals(new PVector(window.width / 2f, window.height / 2f), player.getPosition());
    player.setDirection(1);
    player.move();
    assertEquals(new PVector(window.width / 2f - 1.5f, window.height / 2f), player.getPosition());
  }

  @Test
  void testMoveLeftOutOfBounds() {
    PVector atLeftBorder = new PVector(0, player.getPosition().y);
    player.setDirection(1);
    for (int i = 1; i < 100; i++) {
      player.move();
    }
    assertEquals(atLeftBorder, player.getPosition());
  }

  @Test
  void testJump() {
    assertEquals(new PVector(0f, 0f), player.getDirection());
    player.jump();
    assertEquals(new PVector(0f, -15f), player.getDirection());
  }

  @Test
  void testGravity() {
    assertEquals(new PVector(0f, 0f), player.getDirection());
    player.gravity();
    assertEquals(new PVector(0f, 0.5f), player.getDirection());
  }

  @Test
  void testJumpAndGravity() {
    assertEquals(new PVector(0f, 0f), player.getDirection());
    player.jump();
    player.gravity();
    assertEquals(new PVector(0f, -14.5f), player.getDirection());
  }

  @Test
  void testAddObserver() {
    NormalCloud nCloud1 = new NormalCloud(window);
    player.registerObserver(nCloud1);
    assertEquals(1, player.observers.size());
    NormalCloud nCloud2 = new NormalCloud(window);
    player.registerObserver(nCloud2);
    assertEquals(2, player.observers.size());
  }

  @Test
  void testRemoveObserver() {
    NormalCloud nCloud1 = new NormalCloud(window);
    player.registerObserver(nCloud1);
    NormalCloud nCloud2 = new NormalCloud(window);
    player.registerObserver(nCloud2);
    assertEquals(2, player.observers.size());
    player.unregisterObserver(nCloud1);
    assertEquals(1, player.observers.size());
    player.unregisterObserver(nCloud2);
    assertEquals(0, player.observers.size());
  }
}