package org.project;

/**
 * AbstractObserver contains method stubs to have some update behaviour for each Observer.
 *
 * @author Buck Sin and Lester Shun
 * @version 1.0
 */
public abstract class AbstractObserver extends Character {

  /**
   * Undefined update behaviour for an AbstractObserver object.
   *
   * @param player Player object.
   */
  public abstract void update(Player player);
}
