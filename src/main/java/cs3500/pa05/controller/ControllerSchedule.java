package cs3500.pa05.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.module.SimpleModule;
import cs3500.pa05.model.AbstractEvent;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.Time;
import cs3500.pa05.model.Week;
import cs3500.pa05.model.WeekDay;
import cs3500.pa05.model.json.CalendarRecord;
import cs3500.pa05.model.json.EventDeserializer;
import cs3500.pa05.model.json.JsonUtils;
import cs3500.pa05.view.EventViewElement;
import cs3500.pa05.view.MaxEventsDialog;
import cs3500.pa05.view.MaxTasksDialog;
import cs3500.pa05.view.TaskViewElement;
import cs3500.pa05.view.TimePickerField;
import cs3500.pa05.view.ViewElement;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * an implementation of the controller
 * helps add functionality to the program
 */
public class ControllerSchedule implements Controller {

  @FXML VBox taskLayout = new VBox();
  @FXML Button addEventButton = new Button();
  @FXML Button addTaskButton = new Button();
  @FXML VBox mondayLayout = new VBox();
  @FXML VBox tuesdayLayout = new VBox();
  @FXML VBox wednesdayLayout = new VBox();
  @FXML VBox thursdayLayout = new VBox();
  @FXML VBox fridayLayout = new VBox();
  @FXML VBox saturdayLayout = new VBox();
  @FXML VBox sundayLayout = new VBox();
  @FXML Label weekName = new Label();
  @FXML MenuBar menuBar = new MenuBar();

  Week week;
  private String password = "";
  File file;
  static final String DEFAULT_DATA_FILE = "src/main/resources/data.bujo";
  Stage stage;

  /**
   * runs the CalendarApp and allows the user to use its functionality
   */
  @FXML
  public void run(Stage stage) {
    this.stage = stage;
    this.file = new File(DEFAULT_DATA_FILE);
    setMenuBar();
    week = revertFromFile();
    updateWeek();
    updateTaskQueue();
    updateName();
    handleCreationButtons();
  }

  /**
   * calls all the different update methods
   */
  public void updateEverything() {
    updateWeek();
    updateTaskQueue();
    updateFile();
    updateName();
  }

  /**
   * to update and display the week name, number of events and tasks in the week and the task
   * completion percentage for the week
   */
  private void updateName() {
    int events = week.eventQueue().size();
    int tasks = week.taskQueue().size();
    int taskComplete = 0;
    for (Task task : week.taskQueue()) {
      if (task.isComplete()) {
        taskComplete++;
      }
    }
    double percent;
    if (tasks == 0) {
      percent = 100;
    } else {
      percent = (taskComplete * 100 / tasks);
    }
    weekName.setText(week.getName() + " Events: " + events + "/" + week.getMaximumEvents()
        + " Tasks: " + tasks + "/" + week.getMaximumTasks() + " and " + percent
        + "% of task complete");
    if (week.tasksGreaterThanMaximum() || week.eventsGreaterThanMaximum()) {
      weekName.setText(week.getName() + " Events: " + events + "/" + week.getMaximumEvents()
          + " Tasks: " + tasks + "/" + week.getMaximumTasks()
          + " (One of your days is too busy!)" + " and " + percent + "% of task complete");
    }
  }

  /**
   * helps set up the menu bar and implements shortcuts to open and save and create new tasks, weeks
   * and events
   */
  private void setMenuBar() {
    final String os = System.getProperty("os.name");
    if (os != null && os.startsWith("Mac")) {
      menuBar.useSystemMenuBarProperty().set(true);
    }


    Menu fileMenu = createFileMenu();
    Menu eventMenu = createEventMenu();

    menuBar.getMenus().addAll(fileMenu, eventMenu);

  }

  /**
   * helps the user choose a file from which the information about the week is to be loaded
   */
  private void chooseFile() {
    FileChooser fileChooser = new FileChooser();

    // Set the title of the dialog box
    fileChooser.setTitle("Select File");

    // Show the dialog box and wait for user input
    file = fileChooser.showOpenDialog(stage);
    System.out.println("User selected " + file);
    week = revertFromFile();
  }

  /**
   * creates a new file that only takes certain aspects from the selected file
   *
   * @param nameOfWeek name of the week
   * @param weekFromFile the week from the selected file
   */
  private void createTemplate(String nameOfWeek, Week weekFromFile) {
    //what we save from the week file
    week = new Week(nameOfWeek);
    week.setMaximumTasks(weekFromFile.getMaximumTasks());
    week.setMaximumEvents(weekFromFile.getMaximumEvents());
    // creating a new path
    String pathToNewFile = "src/main/resources/" + "FromTemplate" + file.getName();
    File newFile = new File(pathToNewFile);
    if (newFile.exists()) {
      int numAdd = 1;
      newFile = new File("src/main/resources/" + "FromTemplate" + numAdd + file.getName());
      while (newFile.exists()) {
        numAdd++;
        newFile = new File("src/main/resources/" + "FromTemplate" + numAdd + file.getName());
      }
    }
    try {
      newFile.createNewFile();
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
    file = newFile;
    password = "";
    updateEverything();
  }

  /**
   * a dialog box that lets you choose a file and asks the user what they want to name the
   * week
   */
  private void chooseTemplate() {
    FileChooser fileChooser = new FileChooser();

    // Set the title of the dialog box
    fileChooser.setTitle("Select File");

    // Show the dialog box and wait for user input
    file = fileChooser.showOpenDialog(stage);
    System.out.println("User selected " + file);
    Week weekFromFile = revertFromFile();
    Stage dialogStage = new Stage();

    GridPane gridPane = new GridPane();
    gridPane.setPadding(new Insets(10));
    gridPane.setHgap(10);
    gridPane.setVgap(10);

    Label askForWeekName = new Label("Name of week: ");
    TextField nameOfWeek = new TextField();
    Button close = new Button("Done");
    close.setOnAction(e -> {
      dialogStage.close();
      updateEverything();
      createTemplate(nameOfWeek.getText(), weekFromFile);
    });

    gridPane.addRow(0, askForWeekName, nameOfWeek);
    gridPane.addRow(1, close);

    dialogStage.setScene(new Scene(gridPane));
    dialogStage.showAndWait();
  }

  /**
   * creates the file menu
   *
   * @return the file menu created
   */
  private Menu createFileMenu() {
    MenuItem openMenuItem = new MenuItem("Open");
    openMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+O"));
    openMenuItem.setOnAction(e -> chooseFile());

    MenuItem openTemplateMenuItem = new MenuItem("Open as Template");
    openTemplateMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+SHIFT+T"));
    openTemplateMenuItem.setOnAction(e -> chooseTemplate());

    MenuItem saveMenuItem = new MenuItem("Save");
    saveMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+S"));
    saveMenuItem.setOnAction(e -> {
      updateFile();
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Saved!");
      alert.setHeaderText(null);
      alert.setContentText("Your file data has been saved!");
      alert.showAndWait();
    });

    MenuItem setPassword = new MenuItem("Protect File with Password");
    setPassword.setAccelerator(KeyCombination.keyCombination("SHORTCUT+SHIFT+P"));
    setPassword.setOnAction(e -> setPasswordDialog());
    Menu fileMenu = new Menu("File");
    fileMenu.getItems().addAll(openMenuItem, saveMenuItem, openTemplateMenuItem, setPassword);
    return fileMenu;
  }

  /**
   * create the event menu
   *
   * @return the menu that was created
   */
  private Menu createEventMenu() {

    MenuItem newTaskMenuItem = new MenuItem("Create New Task");
    newTaskMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+T"));
    newTaskMenuItem.setOnAction(e -> addTaskButton.fire());

    MenuItem newEventMenuItem = new MenuItem("Create New Event");
    newEventMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+E"));
    newEventMenuItem.setOnAction(e -> addEventButton.fire());

    MenuItem newWeekMenuItem = new MenuItem("Create New Week");
    newWeekMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+N"));
    newWeekMenuItem.setOnAction(e -> {
      String name = week.getName();
      week = new Week(name);
      updateEverything();
    });

    MenuItem setMaxTasksMenuItem = new MenuItem("Set Maximum Tasks");
    setMaxTasksMenuItem.setOnAction(e -> {
      MaxTasksDialog dialog = new MaxTasksDialog();
      dialog.showAndWait();
      int maxTasks = dialog.getMaxTasks();
      week.setMaximumTasks(maxTasks);
      updateFile();
      updateName();
      System.out.println("Max Tasks: " + maxTasks);
      System.out.println("Max Tasks: " + this.week.getMaximumTasks());
    });

    MenuItem setMaxEventsMenuItem = new MenuItem("Set Maximum Events");
    setMaxEventsMenuItem.setOnAction(e -> {
      MaxEventsDialog dialog = new MaxEventsDialog();
      dialog.showAndWait();
      int maxEvents = dialog.getMaxEvents();
      week.setMaximumEvents(maxEvents);
      updateFile();
      updateName();
      System.out.println("Max Tasks: " + maxEvents);
      System.out.println("Max Tasks: " + this.week.getMaximumEvents());
    });
    Menu eventMenu = new Menu("Data");
    eventMenu.getItems().addAll(newTaskMenuItem, newEventMenuItem, newWeekMenuItem,
        setMaxTasksMenuItem, setMaxEventsMenuItem);
    return eventMenu;
  }

  /**
   * implementation of functionality that adds new events and tasks
   */
  private void handleCreationButtons() {
    addEventButton.setOnAction(e -> openEventDialog());

    addTaskButton.setOnAction(e -> openTaskDialog());
  }

  /**
   * helps a user edit the details of an event in the CalendarApp
   *
   * @param event the event that needs to be edited
   */
  public void eventEditor(Event event) {
    Stage dialogStage = new Stage();
    dialogStage.initModality(Modality.APPLICATION_MODAL);
    dialogStage.setTitle("Event Details");

    GridPane gridPane = new GridPane();
    gridPane.setPadding(new Insets(10));
    gridPane.setHgap(10);
    gridPane.setVgap(10);

    // Create labels and text fields for task details
    VBox name = new VBox();
    Label nameLabel = new Label("Name: ");
    TextField nameField = new TextField(event.getName());
    name.getChildren().add(nameLabel);
    name.getChildren().add(nameField);

    VBox description = new VBox();
    Label descriptionLabel = new Label("Description: ");
    TextField descriptionField = new TextField(event.getDescription());
    description.getChildren().add(descriptionLabel);
    description.getChildren().add(descriptionField);

    VBox dayOfWeek = new VBox();
    Label dayOfWeekLabel = new Label("Day of Week: ");
    ComboBox<String> dayComboBox = new ComboBox<>(FXCollections.observableArrayList(
        Week.getListOfDaysName()));
    dayComboBox.getSelectionModel().select(event.getDay().toString());
    dayOfWeek.getChildren().add(dayOfWeekLabel);
    dayOfWeek.getChildren().add(dayComboBox);

    VBox start = new VBox();
    Label startLabel = new Label("Start Time: " + event.getStartTime().toString());
    TextField startHour = new TextField(String.valueOf(event.getStartTime().getHour()));
    TextField startMin = new TextField(String.valueOf(event.getStartTime().getMinutes()));
    start.getChildren().add(startLabel);
    start.getChildren().add(startHour);
    start.getChildren().add(startMin);

    VBox duration = new VBox();
    Label durationLabel = new Label("Duration: " + event.getDuration().toString());
    TextField durHour = new TextField(String.valueOf(event.getDuration().getHour()));
    TextField durMin = new TextField(String.valueOf(event.getDuration().getMinutes()));
    duration.getChildren().add(durationLabel);
    duration.getChildren().add(durHour);
    duration.getChildren().add(durMin);


    // Add labels, links and text fields to the grid pane
    int theRow = 0;
    gridPane.add(name, 0, theRow++);
    gridPane.add(description, 0, theRow++);
    for (Hyperlink link : event.parseUrl()) {
      gridPane.add(link, 0, theRow++);
    }
    gridPane.add(dayOfWeek, 0, theRow++);
    gridPane.add(start, 0, theRow++);
    gridPane.add(duration, 0, theRow++);

    // Create a button for closing the dialog
    Button closeButton = new Button("Update");
    closeButton.setOnAction(e -> {
      week.remove(event.getDay(), event);
      event.setName(nameField.getText());
      event.setDescription(descriptionField.getText());
      event.setDay(WeekDay.valueOf(dayComboBox.getValue().toUpperCase()));
      event.setStartTime(new Time(Integer.parseInt(startHour.getText()),
          Integer.parseInt(startMin.getText())));
      event.setDuration(new Time(Integer.parseInt(durHour.getText()),
          Integer.parseInt(durMin.getText())));
      week.add(event.getDay(), event);
      dialogStage.close();
      updateEverything();
    });

    // Add the close button to the grid pane
    gridPane.add(closeButton, 0, theRow, 2, 1);
    GridPane.setMargin(closeButton, new Insets(10, 0, 0, 0));

    // Set the grid pane as the root node of the dialog scene
    dialogStage.setScene(new Scene(gridPane));
    dialogStage.showAndWait();
  }

  /**
   * helps a user edit the details of a task in the CalendarApp
   *
   * @param task the task that needs to be edited
   */
  public void taskEditor(Task task) {
    Stage dialogStage = new Stage();
    dialogStage.initModality(Modality.APPLICATION_MODAL);
    dialogStage.setTitle("Event Details");

    GridPane gridPane = new GridPane();
    gridPane.setPadding(new Insets(10));
    gridPane.setHgap(10);
    gridPane.setVgap(10);

    // Create labels and text fields for task details
    VBox name = new VBox();
    Label nameLabel = new Label("Name: ");
    TextField nameField = new TextField(task.getName());
    name.getChildren().add(nameLabel);
    name.getChildren().add(nameField);

    VBox description = new VBox();
    Label descriptionLabel = new Label("Description: ");
    TextField descriptionField = new TextField(task.getDescription());
    description.getChildren().add(descriptionLabel);
    description.getChildren().add(descriptionField);

    VBox dayOfWeek = new VBox();
    Label dayOfWeekLabel = new Label("Day of Week: ");
    ComboBox<String> dayComboBox = new ComboBox<>(FXCollections.observableArrayList(
        Week.getListOfDaysName()));
    dayComboBox.getSelectionModel().select(task.getDay().toString());
    dayOfWeek.getChildren().add(dayOfWeekLabel);
    dayOfWeek.getChildren().add(dayComboBox);

    // Add labels, links and text fields to the grid pane
    int theRow = 0;
    gridPane.add(name, 0, theRow++);
    gridPane.add(description, 0, theRow++);
    for (Hyperlink link : task.parseUrl()) {
      gridPane.add(link, 0, theRow++);
    }
    gridPane.add(dayOfWeek, 0, theRow++);

    // Create a button for closing the dialog
    Button closeButton = new Button("Update");
    closeButton.setOnAction(e -> updateTask(task, nameField.getText(),
        descriptionField.getText(), dayComboBox.getValue(), dialogStage));

    // Add the close button to the grid pane
    gridPane.add(closeButton, 0, theRow, 2, 1);
    GridPane.setMargin(closeButton, new Insets(10, 0, 0, 0));

    // Set the grid pane as the root node of the dialog scene
    dialogStage.setScene(new Scene(gridPane));
    dialogStage.showAndWait();
  }

  private void updateTask(Task task, String name, String description,
                          String weekDay, Stage dialogStage) {
    week.remove(task.getDay(), task);
    task.setName(name);
    task.setDescription(description);
    task.setDay(WeekDay.valueOf(weekDay.toUpperCase()));
    week.add(task.getDay(), task);
    dialogStage.close();
    updateEverything();
  }

  /**
   * opens the dialog box that helps create a new task
   */
  private void openTaskDialog() {
    // Create a new dialog
    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.setTitle("Task Dialog");
    dialog.setHeaderText("Enter Task Details");

    // Create dialog content
    GridPane dialogContent = new GridPane();
    dialogContent.setPadding(new Insets(10));
    dialogContent.setHgap(10);
    dialogContent.setVgap(10);

    // Create input controls
    TextField taskNameField = new TextField();
    TextArea descriptionArea = new TextArea();
    ComboBox<String> dayComboBox = new ComboBox<>(FXCollections.observableArrayList(
        Week.getListOfDaysName()));

    // Add controls to the dialog content
    dialogContent.addRow(0, new Label("Task Name:"), taskNameField);
    dialogContent.addRow(1, new Label("Description:"), descriptionArea);
    dialogContent.addRow(2, new Label("Day:"), dayComboBox);

    // Add buttons to the dialog
    dialog.getDialogPane().setContent(dialogContent);
    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

    // Handle the OK button action
    dialog.setResultConverter(buttonType -> handleCreateTask(buttonType, taskNameField.getText(),
        descriptionArea.getText(), dayComboBox.getValue()));

    // Show the dialog
    dialog.showAndWait();
  }

  private ButtonType handleCreateTask(ButtonType buttonType, String taskName,
                                      String taskDescription, String weekDay) {
    if (buttonType == ButtonType.OK) {
      System.out.println(taskName);
      System.out.printf("Selected %s", weekDay);
      Task newTask = new Task(taskName, taskDescription);
      week.add(WeekDay.valueOf(weekDay.toUpperCase()), newTask);
      newTask.setDay(WeekDay.valueOf(weekDay.toUpperCase()));
      updateEverything();
    }
    return null;
  }

  /**
   * to open a dialog box that allows the user to add an event to the CalendarApp
   */
  private void openEventDialog() {
    // Create a new dialog
    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.setTitle("Event Dialog");
    dialog.setHeaderText("Enter Event Details");

    // Create dialog content
    GridPane dialogContent = new GridPane();
    dialogContent.setPadding(new Insets(10));
    dialogContent.setHgap(10);
    dialogContent.setVgap(10);

    // Create input controls
    TextField eventNameField = new TextField();
    TextArea descriptionArea = new TextArea();
    ComboBox<String> dayComboBox = new ComboBox<>(FXCollections.observableArrayList(
        Week.getListOfDaysName()));

    TimePickerField startTime = new TimePickerField();
    TimePickerField durationTime = new TimePickerField();

    // Add controls to the dialog content
    dialogContent.addRow(0, new Label("Task Name:"), eventNameField);
    dialogContent.addRow(1, new Label("Description:"), descriptionArea);
    dialogContent.addRow(2, new Label("Day:"), dayComboBox);
    dialogContent.addRow(3, new Label("Start Time (HH:MM):"), startTime.getHourField(),
        startTime.getMinutesField());
    dialogContent.addRow(4, new Label("Duration (HH:MM):"), durationTime.getHourField(),
        durationTime.getMinutesField());

    // Add buttons to the dialog
    dialog.getDialogPane().setContent(dialogContent);
    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

    // Handle the OK button action
    dialog.setResultConverter(buttonType -> handleCreateEvent(buttonType, startTime, durationTime,
        eventNameField.getText(), descriptionArea.getText(), dayComboBox.getValue()));

    // Show the dialog
    dialog.showAndWait();
  }

  private ButtonType handleCreateEvent(ButtonType buttonType, TimePickerField startTime,
                                 TimePickerField durationTime, String eventName,
                                       String eventDescription, String weekDay) {
    if (buttonType == ButtonType.OK) {
      startTime.parseInput();
      durationTime.parseInput();
      int startHour = startTime.getStartHour();
      int startMinutes = startTime.getStartMinutes();
      int durationHours = durationTime.getStartHour();
      int durationMinutes = durationTime.getStartMinutes();

      if (durationHours * 60 + durationMinutes + startHour * 60 + startMinutes > 1439) {
        durationHours = 23 - startHour;
        durationMinutes = 59 - startMinutes;
      }

      Time start = new Time(startHour, startMinutes);
      Time duration = new Time(durationHours, durationMinutes);
      System.out.println(start);
      System.out.println(duration);
      System.out.println(eventName);
      System.out.printf("Selected %s", weekDay);
      Event newEvent = new Event(eventName, eventDescription, start, duration);
      week.add(WeekDay.valueOf(weekDay.toUpperCase()), newEvent);
      newEvent.setDay(WeekDay.valueOf(weekDay.toUpperCase()));
      updateEverything();
    }
    return null;
  }

  /**
   * helps update the task queue to include all the tasks created in each day of the week
   */
  private void updateTaskQueue() {
    Node node = taskLayout.getChildren().get(0);
    taskLayout.getChildren().clear();
    taskLayout.getChildren().add(node);

    for (Task task : week.taskQueue()) {
      renderAnEvent(taskLayout, task);
    }

  }

  /**
   * updates the view of the week
   */
  private void updateWeek() {
    for (WeekDay day : week.getKeySet()) {
      switch (day) {
        case MONDAY -> updateDay(day, mondayLayout);
        case TUESDAY -> updateDay(day, tuesdayLayout);
        case WEDNESDAY -> updateDay(day, wednesdayLayout);
        case THURSDAY -> updateDay(day, thursdayLayout);
        case FRIDAY -> updateDay(day, fridayLayout);
        case SATURDAY -> updateDay(day, saturdayLayout);
        default -> updateDay(day, sundayLayout);
      }
    }
  }

  /**
   * helps update the view of a single day in the week
   *
   * @param day the day which we intend to update
   * @param layout the specific vbox corresponding to the day of the week
   */
  private void updateDay(WeekDay day, VBox layout) {
    //System.out.println(week);
    List<AbstractEvent> tasksForDay = week.getTasksForDay(day);
    Node node = layout.getChildren().get(0);
    layout.getChildren().clear();
    layout.getChildren().add(node);
    for (AbstractEvent event : tasksForDay) {
      event.setDay(day);
      renderAnEvent(layout, event);
    }
  }

  /**
   * displays an event on the week view of the CalendarApp
   *
   * @param layout the layout manager upon which all the event details need to be displayed
   * @param event the abstract event we wish to display
   */
  private void renderAnEvent(VBox layout, AbstractEvent event) {
    Button removeButton = new Button("X");
    removeButton.prefWidth(80);
    removeButton.setPadding(new Insets(5, 5, 5, 5));
    removeButton.setOnAction(e -> {
      for (WeekDay thisDay : WeekDay.values()) {
        week.remove(thisDay, event);
      }
      updateEverything();
    });

    HBox buttonBox = new HBox();
    buttonBox.getChildren().add(removeButton);

    Button edit = new Button("E");
    buttonBox.getChildren().add(edit);
    if (event instanceof Task) {
      Task task = (Task) event;
      edit.setOnAction(e -> taskEditor(task));
      ViewElement taskView = new TaskViewElement(task);
      layout.getChildren().add(taskView.load(80));
      Button complete = new Button();
      buttonBox.getChildren().add(complete);
      if (task.isComplete()) {
        complete.setText("D");
      } else {
        complete.setText("ND");
      }
      complete.setOnAction(e -> {
        task.setComplete(!task.isComplete());
        updateEverything();
      });
//      layout.getChildren().add(complete);
    } else if (event instanceof Event) {
      Event e1 = (Event) event;
      edit.setOnAction(e -> eventEditor(e1));
      ViewElement eventView = new EventViewElement(week, e1);
      layout.getChildren().add(eventView.load(100));
    }

    buttonBox.setAlignment(Pos.CENTER_LEFT);
    buttonBox.setPadding(new Insets(10, 0, 0, 10));
    layout.getChildren().add(buttonBox);

  }

  /**
   * helps open the password dialog
   *
   * @param thisPassword the password
   * @param parsedWeek the week to be loaded
   */
  public void openPasswordDialog(String thisPassword, Week parsedWeek) {
    Stage dialogStage = new Stage();
    dialogStage.initModality(Modality.APPLICATION_MODAL);
    dialogStage.setTitle("Password");

    GridPane gridPane = new GridPane();
    gridPane.setPadding(new Insets(10));
    gridPane.setHgap(10);
    gridPane.setVgap(10);

    Label askForPassword = new Label("Enter Password: ");
    PasswordField passwordField = new PasswordField();

    Button open = new Button("Open");

    open.setOnAction(e -> {
      if (thisPassword.equals(passwordField.getText())) {
        System.out.println("Loaded Calendar!");
        dialogStage.close();
        week = parsedWeek;
        password = thisPassword;
      } else {
        askForPassword.setText("Incorrect! Password: ");
      }
      updateEverything();
    });

    gridPane.addRow(0, askForPassword, passwordField);
    gridPane.addRow(1, open);

    dialogStage.setScene(new Scene(gridPane));
    dialogStage.showAndWait();
  }

  /**
   *  helps the user set the password
   */
  public void setPasswordDialog() {
    Stage dialogStage = new Stage();
    dialogStage.initModality(Modality.APPLICATION_MODAL);
    dialogStage.setTitle("Password");

    GridPane dialogContent = new GridPane();
    dialogContent.setPadding(new Insets(10));
    dialogContent.setHgap(10);
    dialogContent.setVgap(10);

    Label askForPassword = new Label("Set Password: ");
    PasswordField passwordField = new PasswordField();

    Button open = new Button("Set Password");
    open.setOnAction(e -> {
      password = passwordField.getText();
      dialogStage.close();
      updateFile();
    });

    dialogContent.addRow(0, askForPassword, passwordField);
    dialogContent.addRow(1, open);
    dialogStage.setScene(new Scene(dialogContent));
    dialogStage.showAndWait();
  }

  /**
   * helps the user to save the required information to a file
   */
  private void updateFile() {
    try {
      file.createNewFile();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    JsonNode json = JsonUtils.serializeRecord(new CalendarRecord(password, this.week));

    ObjectMapper mapper = new ObjectMapper();
    ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
    try {
      writer.writeValue(file, json);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }


    System.out.println("This is what it maps "  + json.toString());
  }

  /**
   * helps show the previously saved data to the user
   *
   * @return a week object containing information about the week's events
   */
  private Week revertFromFile() {
    Week parsedWeek = null;
    String filesPassword = "";
    try {
      ObjectMapper mapper = new ObjectMapper();
      final SimpleModule module = new SimpleModule();
      module.addDeserializer(AbstractEvent.class, new EventDeserializer());
      mapper.registerModule(module);
      JsonParser parser = mapper.getFactory().createParser(file);
      CalendarRecord rec  = parser.readValueAs(CalendarRecord.class);
      System.out.println("read file as " + rec.toString());
      filesPassword = rec.password();
      parsedWeek = rec.week();
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
      e.printStackTrace();
    } catch (JsonParseException e) {
      System.out.println("Parse error");
      e.printStackTrace();
    } catch (IOException e) {
      System.out.println("IOException");
      e.printStackTrace();
    }

    if (filesPassword.equals("")) {
      return parsedWeek;
    } else {
      openPasswordDialog(filesPassword, parsedWeek);
      return week;
    }
  }
}