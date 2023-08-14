package cs3500.pa05.view;

import cs3500.pa05.controller.Controller;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * Represents a simple welcome GUI cs3500.pa05.view.
 */
public class GuiWeekView implements View {

  FXMLLoader loader;

  /**
   * constructor for GuiWeekView
   *
    * @param controller the controller
   */
  public GuiWeekView(Controller controller) {
    // look up and store the layout
    this.loader = new FXMLLoader();
    this.loader.setLocation(getClass().getClassLoader().getResource("schedule.fxml"));
    this.loader.setController(controller);
  }

  /**
   * helps load the main interface of the CalendarApp that displays the week and the events
   * and tasks of that week
   *
   * @return the scene corresponding to the main interface of the app that needs to be displayed
   */
  @Override
  public Scene load() {
    // load the layout
    try {
      return this.loader.load();

    } catch (IOException exc) {
      throw new IllegalStateException("Unable to load layout.");
    }
  }
}
