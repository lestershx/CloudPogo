package org.project;

interface OnDeathListener {
  void onDeath();
}

class B {
  private OnDeathListener dListener;

  public void registerOnDeathListener(OnDeathListener dListener) {
    this.dListener = dListener;
  }

  public void sendScore() {

  }

}

public class PlayerDeathEventListener {

}
