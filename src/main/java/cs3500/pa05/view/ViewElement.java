package cs3500.pa05.view;

import javafx.scene.Node;

/**
 * to create the display of a particular element such as a task or an event in the CalendarApp
 */
public interface ViewElement {
  /**
   * Loads a scene from a schedule GUI layout.
   *
   * @param width the width of the view element
   * @return the layout
   * @throws IllegalStateException if view element fails
   */
  Node load(int width) throws IllegalStateException;

}