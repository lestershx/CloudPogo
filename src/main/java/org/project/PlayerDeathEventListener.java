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
//    System.out.println("Performing callback after synchromous task");
//    System.out.println("Server test: input is " + window.score);
//    try{
//      window.connection.connect();
//    }catch(IOException | InterruptedException | ClassNotFoundException e){
//      System.out.println(e);
//    }
  }
}
