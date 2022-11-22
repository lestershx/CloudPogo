package org.project;

import java.util.ArrayList;

public abstract class AbstractObservable extends Character{
  ArrayList<AbstractObserver> observers;

  public abstract void registerObserver(AbstractObserver observer);
  public abstract void unregisterObserver(AbstractObserver observer);
  public abstract void notifyObservers();
}
