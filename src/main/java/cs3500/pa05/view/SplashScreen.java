package cs3500.pa05.view;

import cs3500.pa05.controller.Controller;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * helps make the splash screen appear on the app
 */
public class SplashScreen implements View {
  FXMLLoader loader;

  /**
   * constructor for the splash screen
   *
   * @param controller the controller
   */
  public SplashScreen(Controller controller) {
    // look up and store the layout
    this.loader = new FXMLLoader();
    this.loader.setLocation(getClass().getClassLoader().getResource("SplashScreen.fxml"));
    this.loader.setController(controller);
  }

  /**
   * helps load the Splash Screen of the CalendarApp that displays the App Name
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
