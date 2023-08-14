package cs3500.pa05.view;

import cs3500.pa05.model.Event;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * allows a user to view the details of an event in a dialog box
 */
public class EventDialog {

  /**
   * allows a user to view the details of an event in a dialog box
   *
   * @param event the event which we wish to display
   */
  public static void showDialog(Event event) {
    Stage dialogStage = new Stage();
    dialogStage.initModality(Modality.APPLICATION_MODAL);
    dialogStage.setTitle("Event Details");

    GridPane gridPane = new GridPane();
    gridPane.setPadding(new Insets(10));
    gridPane.setHgap(10);
    gridPane.setVgap(10);

    // Create labels and text fields for task details
    Label nameLabel = new Label("Name: " + event.getName());

    Label descriptionLabel = new Label("Description: " + event.getDescription());

    // Add labels, links and text fields to the grid pane
    int theRow = 0;
    gridPane.add(nameLabel, 0, theRow++);
    gridPane.add(descriptionLabel, 0, theRow++);
    for (Hyperlink link : event.parseUrl()) {
      gridPane.add(link, 0, theRow++);
    }
    Label dayOfWeekLabel = new Label("Day of Week: " + event.getDay());
    Label startLabel = new Label("Start Time: " + event.getStartTime().toString());
    Label durationLabel = new Label("Duration: " + event.getDuration().toString());
    gridPane.add(dayOfWeekLabel, 0, theRow++);
    gridPane.add(startLabel, 0, theRow++);
    gridPane.add(durationLabel, 0, theRow++);

    // Create a button for closing the dialog
    Button closeButton = new Button("Close");
    closeButton.setOnAction(e -> dialogStage.close());

    // Add the close button to the grid pane
    gridPane.add(closeButton, 0, theRow, 2, 1);
    GridPane.setMargin(closeButton, new Insets(10, 0, 0, 0));

    // Set the grid pane as the root node of the dialog scene
    dialogStage.setScene(new Scene(gridPane));
    dialogStage.showAndWait();
  }
}
