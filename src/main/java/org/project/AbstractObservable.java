package org.project;

import java.util.ArrayList;

/**
 * AbstractObservable contains method stubs to register observers to an ArrayList of observers,
 * unregister them, and notify all observers in the list.
 *
 * @author Buck Sin and Lester Shun
 * @version 1.0
 */
public abstract class AbstractObservable extends Character {
  ArrayList<AbstractObserver> observers;

  /**
   * Registers observer to observers ArrayList.
   *
   * @param observer A single AbstractObserver object to add.
   */
  public abstract void registerObserver(AbstractObserver observer);

  /**
   * Unregisters observer to observers ArrayList.
   *
   * @param observer A single AbstractObserver object to remove.
   */
  public abstract void unregisterObserver(AbstractObserver observer);

  /**
   * Notify all observers of some update.
   */
  public abstract void notifyObservers();
}
