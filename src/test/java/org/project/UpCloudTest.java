package org.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import processing.core.PVector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the methods of UpCloud class
 * NOTE: Before testing, comment out lines 18, 31 and 53 in Player,
 * and lines 17, 41, 59 and 60 in Cloud as PImage interferes with testing.
 */
public class UpCloudTest {
  Window window;
  UpCloud upCloud;
  float startingHeight;
  PVector startingPosition;
  PVector startingDirection;
  float floatRange = 275f;

  @BeforeEach
  void setUp() {
    window = new Window();
    upCloud = new UpCloud(window);
    upCloud.setPosition(new PVector(window.width / 2f, window.height / 2f + 100));
    upCloud.setDirection(new PVector(-1, -0.75f));
    startingHeight = upCloud.getStartingHeight();
    startingPosition = upCloud.getPosition().copy();
    startingDirection = upCloud.getDirection().copy();
  }

  /**
   * Should change direction when hitting the top bound
   */
  @Test
  void testCheckHeightTopOfRange() {
    float topLimit = startingHeight - floatRange;
    assertEquals(startingDirection, upCloud.getDirection());
    upCloud.setPosition(new PVector(window.width / 2f - 50, topLimit - 5));
    upCloud.checkHeight();
    assertEquals(new PVector(startingDirection.x, startingDirection.y + 0.75f), upCloud.getDirection());
  }

  /**
   * Should change direction when 100 pixels from top of window
   */
  @Test
  void testCheckHeightTopOfWindow() {
    assertEquals(startingDirection, upCloud.getDirection());
    upCloud.setPosition(new PVector(window.width / 2f - 50, 100));
    upCloud.checkHeight();
    assertEquals(new PVector(startingDirection.x, startingDirection.y + 0.75f), upCloud.getDirection());
  }
}
