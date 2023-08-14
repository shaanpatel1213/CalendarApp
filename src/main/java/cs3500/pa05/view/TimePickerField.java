package cs3500.pa05.view;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 * allows the user to enter the time
 */
public class TimePickerField {
  int startHour;
  int startMinutes;
  TextField hourField;
  TextField minutesField;

  /**
   * constructor for the time picker field
   */
  public TimePickerField() {
    hourField = new TextField();
    minutesField = new TextField();
  }

  /**
   * helps validate the input that the user has put and
   * makes sure that the user inputs a valid time
   */
  public void parseInput() {
    startHour = Integer.parseInt(hourField.getText());
    startMinutes = Integer.parseInt(minutesField.getText());

    if (startHour < 0 || startHour > 23 || startMinutes < 0 || startMinutes > 59) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Invalid Input");
      alert.setHeaderText(null);
      alert.setContentText("Please enter a valid number.");
      alert.showAndWait();
      hourField.clear();
      minutesField.clear();
      parseInput();
    }
  }

  /**
   * to return the hour that the user has entered
   *
   * @return the hour when an abstract event starts
   */
  public int getStartHour() {
    return startHour;
  }

  /**
   * to return the minute that the user has entered
   *
   * @return the minute when an abstract event starts
   */
  public int getStartMinutes() {
    return startMinutes;
  }

  /**
   * to get the text field where the user will enter the starting hour of an abstract event
   *
   * @return the text field
   */
  public TextField getHourField() {
    return hourField;
  }

  /**
   * to get the text field where the user will enter the starting minute of an abstract event
   *
   * @return the text field
   */
  public TextField getMinutesField() {
    return minutesField;
  }
}
