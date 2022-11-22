package org.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

  @BeforeEach
  void setUp() {
    Window window = new Window();
    Player player = Player.getInstance(window);
  }

  @Test
  void move() {
  }

  @Test
  void setDirection() {

  }
}