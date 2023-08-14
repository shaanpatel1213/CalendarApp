package cs3500.pa05;

import cs3500.pa05.controller.Controller;
import cs3500.pa05.controller.ControllerSchedule;
import cs3500.pa05.controller.ControllerSplashScreen;
import cs3500.pa05.view.GuiWeekView;
import cs3500.pa05.view.SplashScreen;
import cs3500.pa05.view.View;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * the entry point for the CalendarApp
 */
public class CalendarApp extends Application {

  Controller scheduleController = new ControllerSchedule();
  Controller splashScreenController = new ControllerSplashScreen();

  /**
   * implementation of the start method required when we extend from Application
   * sets up the view and controller and shows the stage
   *
   * @param stage the primary stage for this application, onto which
   *              the application scene can be set.
   *              Applications may create other stages, if needed, but they will not be
   *              primary stages.
   */
  @Override
  public void start(Stage stage) {
    View splashScreen = new SplashScreen(splashScreenController);
    stage.setScene(splashScreen.load());
    stage.show();
    splashScreenController.run(stage);
    View view = new GuiWeekView(scheduleController);
    // Load the new scene in a separate thread
    Task<Scene> sceneLoadingTask = new Task<>() {
      @Override
      protected Scene call() throws Exception {
        // Simulate some time-consuming operation
        Thread.sleep(2000);

        // Create and return the new scene
        return view.load();
      }
    };
    sceneLoadingTask.setOnSucceeded(event -> {
      Scene newScene = sceneLoadingTask.getValue();
      stage.setScene(newScene);
      scheduleController.run(stage);
      stage.show();
    });

    new Thread(sceneLoadingTask).start();

  }

  /**
   * calls the launch method to start the JavaFX lifecycle
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    launch();
  }
}
