package cs3500.pa05.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * allows the user to set the maximum number of events for a day
 */
public class MaxEventsDialog extends Stage {

  private int maxEvents;
  private final TextField maxEventsTextField;

  /**
   * allows the user to modify the maximum number of events in a day
   */
  public MaxEventsDialog() {
    super();

    setResizable(false);

    maxEventsTextField = new TextField();
    Button confirmButton = new Button("Confirm");
    confirmButton.setOnAction(event -> {
      if (validateInput()) {
        maxEvents = Integer.parseInt(maxEventsTextField.getText());
        close();
      } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a valid number.");
        alert.showAndWait();
      }
    });

    GridPane gridPane = new GridPane();
    gridPane.setPadding(new Insets(10));
    gridPane.setHgap(10);
    gridPane.setVgap(10);
    gridPane.add(new Label("Maximum Events:"), 0, 0);
    gridPane.add(maxEventsTextField, 1, 0);
    gridPane.add(confirmButton, 1, 1);

    Scene scene = new Scene(gridPane);
    setScene(scene);
    setTitle("Set Maximum Events");
  }

  /**
   * helps validate the maximum number of events in a day
   *
   * @return boolean representing whether the input is valid
   */
  private boolean validateInput() {
    String input = maxEventsTextField.getText();
    try {
      int value = Integer.parseInt(input);
      return value >= 0;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * returns the maximum number of events in a day
   *
   * @return integer representing the maximum number of events in a day
   */
  public int getMaxEvents() {
    return maxEvents;
  }
}