package org.project;

import java.io.IOException;

interface OnEventListener {
  void onEvent();
}

class B {
  private OnEventListener dListener;

  public void registerOnDeathListener(OnEventListener dListener) {
    this.dListener = dListener;
  }


}


public class PlayerDeathEventListener implements OnEventListener {

  private Window window;

  public PlayerDeathEventListener(Window gameWindow) {
    window = gameWindow;
  }

  @Override
  public void onEvent() {
    System.out.println("Performing callback after synchromous task");
    System.out.println("Game Over Bro");
    try{
      window.getConnection().contactServer("TEST");
    }catch(IOException | InterruptedException | ClassNotFoundException e){
      System.out.println(e);
    }
  }
}
