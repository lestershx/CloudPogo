package org.project;
import java.io.IOException;


interface OnEventListener {
  void onEvent();
}

public class PlayerDeathEventListener implements OnEventListener {

  private Window window;

  public PlayerDeathEventListener(Window gameWindow) {
    window = gameWindow;
  }

  @Override
  public void onEvent() {

  }
}
