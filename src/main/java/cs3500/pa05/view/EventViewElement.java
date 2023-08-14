package cs3500.pa05.view;

import cs3500.pa05.model.Event;
import cs3500.pa05.model.Week;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * helps display an event inside the week view of the CalendarApp
 */
public class EventViewElement implements ViewElement {


  Event event;
  Week week;

  /**
   * constructor for event view element
   *
   * @param week the week in which the event exists
   * @param event the event which we wish to display
   */
  public EventViewElement(Week week, Event event) {
    this.week = week;
    this.event = event;
  }

  /**
   * helps display an event inside the week view of the CalendarApp
   *
   * @param width the width between the lines of the dialog box displaying the event
   * @return a layout with the details of the required event
   */
  @Override
  public Node load(int width) {
    Label nameLabel = new Label(event.getName());
    nameLabel.setFont(new Font(20));
    Label desLabel = new Label(event.getDescription());
    desLabel.setFont(new Font(15));
    Label startLabel = new Label("Start Time: " + event.getStartTime().toString());
    startLabel.setFont(new Font(15));
    Label lengthLabel = new Label("Duration: " + event.getDuration().toString());
    lengthLabel.setFont(new Font(15));
    VBox eventBox = new VBox();
    eventBox.getChildren().add(nameLabel);
    eventBox.getChildren().add(desLabel);
    for (Hyperlink link : event.parseUrl()) {
      eventBox.getChildren().add(link);
    }
    eventBox.getChildren().add(startLabel);
    eventBox.getChildren().add(lengthLabel);
    eventBox.setPrefWidth(width);
    eventBox.setPadding(new Insets(0, 0, 20, 0));
    HBox newTaskLayout = new HBox();
    newTaskLayout.getChildren().add(eventBox);
    newTaskLayout.setAlignment(Pos.CENTER);
    newTaskLayout.setOnMouseClicked(e -> EventDialog.showDialog(event));
    return newTaskLayout;
  }
}
