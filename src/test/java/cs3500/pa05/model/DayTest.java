package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * to test the methods in the day class
 */
class DayTest {
  private Day day;
  private List<AbstractEvent> events;

  @BeforeEach
  void setUp() {
    day = new Day();
    events = new ArrayList<>();
  }

  /**
   * to test the method add event
   */
  @Test
  void addEvent_shouldAddEventToList() {
    Event event = new Event("Event 1", "Description 1", new Time(9, 0), new Time(1, 30));
    day.addEvent(event);

    assertTrue(day.getEvents().contains(event));
  }

  /**
   * to test the method remove event
   */
  @Test
  void removeEvent_shouldRemoveEventFromList() {
    Event event = new Event("Event 1", "Description 1", new Time(9, 0), new Time(1, 30));
    day.addEvent(event);

    day.removeEvent(event);

    assertFalse(day.getEvents().contains(event));
  }

  /**
   * to test the method setEventsList
   */
  @Test
  void setEvents_shouldSetEventsList() {
    Event event1 = new Event("Event 1", "Description 1", new Time(9, 0), new Time(1, 30));
    Event event2 = new Event("Event 2", "Description 2", new Time(14, 0), new Time(2, 0));

    events.add(event1);
    events.add(event2);

    day.setEvents(events);

    assertEquals(events, day.getEvents());
  }

  /**
   * to test the method getNumberOfEvents
   */
  @Test
  void getNumberOfEvents_shouldReturnCorrectCount() {
    Event event1 = new Event("Event 1", "Description 1", new Time(9, 0), new Time(1, 30));
    Event event2 = new Event("Event 2", "Description 2", new Time(14, 0), new Time(2, 0));

    day.addEvent(event1);
    day.addEvent(event2);

    int numberOfEvents = day.getNumberOfEvents();

    assertEquals(2, numberOfEvents);
  }

  /**
   * to test the method addTask
   */
  @Test
  void addTask_shouldAddTaskToList() {
    Task task = new Task("Task 1", "Description 1");
    day.addEvent(task);

    assertTrue(day.getEvents().contains(task));
  }

  /**
   * to test the method removeTask
   */
  @Test
  void removeTask_shouldRemoveTaskFromList() {
    Task task = new Task("Task 1", "Description 1");
    day.addEvent(task);

    day.removeEvent(task);

    assertFalse(day.getEvents().contains(task));
  }

  /**
   * to test the method getNumberOfTasks
   */
  @Test
  void getNumberOfTasks_shouldReturnCorrectCount() {
    Task task1 = new Task("Task 1", "Description 1");
    Task task2 = new Task("Task 2", "Description 2");

    day.addEvent(task1);
    day.addEvent(task2);

    int numberOfTasks = day.getNumberOfEvents();

    assertEquals(2, numberOfTasks);
  }

  /**
   * to test the method toString
   */
  @Test
  void toString_shouldReturnStringRep() {
    String expected = "Day{events=[AbstractEvent{name='Task 1', description='Description 1'}]}";

    Task task1 = new Task("Task 1", "Description 1");
    day.addEvent(task1);
    assertEquals(expected.replaceAll("\n", "\r\n"), day.toString());
  }

  /**
   * to test the json creator
   */

  @Test
  void jsonCreator() {
    ArrayList<AbstractEvent> events = new ArrayList<>();
    Day day = new Day(events);
    String expected = "Day{events=[]}";
    assertEquals(expected.replaceAll("\n", "\r\n"), day.toString());
  }
}
