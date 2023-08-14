package cs3500.pa05.view;

import cs3500.pa05.model.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * helps display a task inside the week view of the CalendarApp
 */
public class TaskViewElement implements ViewElement {

  Task task;
  VBox taskBox = new VBox();

  /**
   * constructor for the task view element
   *
   * @param task the task we wish to display
   */
  public TaskViewElement(Task task) {
    this.task = task;
  }

  /**
   * helps display a task inside the week view of the CalendarApp
   *
   * @param width the width between the lines of the dialog box displaying the event
   * @return a layout with the details of the required event
   */
  @Override
  public Node load(int width) {
    Label nameLabel = new Label(task.getName());
    nameLabel.setFont(new Font(15));
    Label desLabel = new Label(task.getDescription());
    desLabel.setFont(new Font(10));
    taskBox.getChildren().add(nameLabel);
    taskBox.getChildren().add(desLabel);
    for (Hyperlink link : task.parseUrl()) {
      taskBox.getChildren().add(link);
    }
    taskBox.setPrefWidth(width);
    taskBox.setPadding(new Insets(10, 10, 10, 0));
    HBox newTaskLayout = new HBox();
    newTaskLayout.getChildren().add(taskBox);
    newTaskLayout.setAlignment(Pos.CENTER);
    newTaskLayout.setOnMouseClicked(e -> TaskDialog.showDialog(task));

    return newTaskLayout;
  }

}