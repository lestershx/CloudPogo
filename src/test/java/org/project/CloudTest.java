package org.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import processing.core.PVector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the methods of Cloud class
 * NOTE: Before testing, comment out lines 18, 31 and 53 in Player,
 * and lines 17, 41, 59 and 60 in Cloud as PImage interferes with testing.
 */
public class CloudTest {
  Window window;
  Cloud cloud;

  @BeforeEach
  void setUp() {
    window = new Window();
    cloud = new Cloud(window);
  }

  @Test
  void testMove() {

  }
}
