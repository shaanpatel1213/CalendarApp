package cs3500.pa05.view;

import javafx.scene.Scene;

/**
 * to create the view of the main CalendarApp interface where the user can view and utilize
 * all the functionality
 */
public interface View {
  /**
   * Loads a scene from a schedule GUI layout.
   *
   * @return the layout
   * @throws IllegalStateException if view fails
   */
  Scene load() throws IllegalStateException;
}
