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
 * allows the user to set the maximum number of tasks for a day
 */
public class MaxTasksDialog extends Stage {

  private int maxTasks;
  private final TextField maxTasksTextField;

  /**
   * allows the user to modify the maximum number of tasks in a day
   */
  public MaxTasksDialog() {
    super();

    setResizable(false);

    maxTasksTextField = new TextField();
    Button confirmButton = new Button("Confirm");
    confirmButton.setOnAction(event -> {
      if (validateInput()) {
        maxTasks = Integer.parseInt(maxTasksTextField.getText());
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
    gridPane.add(new Label("Maximum Tasks:"), 0, 0);
    gridPane.add(maxTasksTextField, 1, 0);
    gridPane.add(confirmButton, 1, 1);

    Scene scene = new Scene(gridPane);
    setScene(scene);
    setTitle("Set Maximum Tasks");
  }

  /**
   * helps validate the maximum number of tasks in a day
   *
   * @return boolean representing whether the input is valid
   */
  private boolean validateInput() {
    String input = maxTasksTextField.getText();
    try {
      int value = Integer.parseInt(input);
      return value >= 0;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * returns the maximum number of tasks in a day
   *
   * @return integer representing the maximum number of tasks in a day
   */
  public int getMaxTasks() {
    return maxTasks;
  }
}