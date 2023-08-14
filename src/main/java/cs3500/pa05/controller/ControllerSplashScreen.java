package cs3500.pa05.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * to display a splash screen to the CalendarApp user
 */
public class ControllerSplashScreen implements Controller {
  @FXML
  ProgressBar progressBar = new ProgressBar();

  private static final Duration DURATION = Duration.seconds(1.5);

  /**
   * helps display the splash screen and its progress bar before the user enters the app
   *
   * @param stage the stage
   */
  @Override
  public void run(Stage stage) {
    Timeline timeline = new Timeline();
    KeyValue keyValue = new KeyValue(progressBar.progressProperty(), 1.0);
    KeyFrame keyFrame = new KeyFrame(DURATION, keyValue);
    timeline.getKeyFrames().add(keyFrame);
    timeline.setCycleCount(1);
    timeline.play();
  }
}
