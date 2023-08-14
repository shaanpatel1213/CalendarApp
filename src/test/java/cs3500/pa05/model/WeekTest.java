package cs3500.pa05.model;

import static cs3500.pa05.model.WeekDay.MONDAY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * to test the methods in the week class
 */
class WeekTest {
  Week basicWeek;

  @BeforeEach
  void setUp() {
    basicWeek = new Week("weekName");
  }

  /**
   * to test the method setDays
   */
  @Test
  void setDays() {
    HashMap<WeekDay, Day> map = new HashMap<>();
    map.put(MONDAY, new Day());
    basicWeek.setDays(map);
    String expected = "Week{days={MONDAY=Day{events=[]}}}";
    assertEquals(expected.replaceAll("\n", "\r\n"), basicWeek.toString());
  }

  /**
   * to test the method getMaximumEvents
   */
  @Test
  void getMaximumEvents() {
    assertEquals(1000, basicWeek.getMaximumEvents());
  }

  /**
   * to test the method getMaximumTasks
   */
  @Test
  void getMaximumTasks() {
    assertEquals(1000, basicWeek.getMaximumTasks());
  }

  /**
   * to test the method setName
   */
  @Test
  void setName() {
    basicWeek.setName("new name");
    assertEquals("new name", basicWeek.getName());

  }

  /**
   * to test the method getKeySet
   */
  @Test
  void getKeySet() {
    Set<WeekDay> weekDaySet = new HashSet<>();
    weekDaySet.add(MONDAY);
    weekDaySet.add(WeekDay.TUESDAY);
    weekDaySet.add(WeekDay.WEDNESDAY);
    weekDaySet.add(WeekDay.THURSDAY);
    weekDaySet.add(WeekDay.FRIDAY);
    weekDaySet.add(WeekDay.SATURDAY);
    weekDaySet.add(WeekDay.SUNDAY);
    assertEquals(weekDaySet, basicWeek.getKeySet());
  }

  /**
   * to test the method getListOfDaysName
   */
  @Test
  void getListOfDaysName() {
    List<String> daysOfWeek = new ArrayList<>(Arrays.asList("Monday", "Tuesday", "Wednesday",
        "Thursday", "Friday", "Saturday", "Sunday"));
    assertEquals(daysOfWeek, Week.getListOfDaysName());
  }

  /**
   * to test the method getTasksForDay
   */
  @Test
  void getTasksForDay() {
    HashMap<WeekDay, Day> map = new HashMap<>();
    Day day = new Day();
    day.addEvent(new Task("drink", "hydrate"));
    map.put(MONDAY, day);
    basicWeek.setDays(map);
    List<AbstractEvent> tasks = new ArrayList<>();
    tasks.add(new Task("drink", "hydrate"));
    assertEquals(tasks.get(0).toString(),
        basicWeek.getTasksForDay(MONDAY).get(0).toString());
  }

  /**
   * to test the method getName
   */
  @Test
  void getName() {
    assertEquals("weekName", basicWeek.getName());
  }

  /**
   * to test the method taskQueue
   */
  @Test
  void taskQueue() {
    Day day = new Day();
    day.addEvent(new Task("drink", "hydrate"));
    day.addEvent(new Event("drink", "don't drink", new Time(0, 0),
        new Time(1, 1)));
    HashMap<WeekDay, Day> map = new HashMap<>();
    map.put(MONDAY, day);
    map.put(WeekDay.TUESDAY, new Day());
    map.put(WeekDay.WEDNESDAY, new Day());
    map.put(WeekDay.THURSDAY, new Day());
    map.put(WeekDay.FRIDAY, new Day());
    map.put(WeekDay.SATURDAY, new Day());
    map.put(WeekDay.SUNDAY, new Day());
    basicWeek.setDays(map);
    assertEquals("AbstractEvent{name='drink', description='hydrate'}",
        basicWeek.taskQueue().get(0).toString());
  }

  /**
   * to test the method eventQueue
   */
  @Test
  void eventQueue() {
    Day day = new Day();
    day.addEvent(new Event("drink", "hydrate", new Time(0, 0),
        new Time(1, 0)));
    day.addEvent(new Task("drink", "hydrate"));
    HashMap<WeekDay, Day> map = new HashMap<>();
    map.put(MONDAY, day);
    map.put(WeekDay.TUESDAY, new Day());
    map.put(WeekDay.WEDNESDAY, new Day());
    map.put(WeekDay.THURSDAY, new Day());
    map.put(WeekDay.FRIDAY, new Day());
    map.put(WeekDay.SATURDAY, new Day());
    map.put(WeekDay.SUNDAY, new Day());
    basicWeek.setDays(map);
    assertEquals("AbstractEvent{name='drink', description='hydrate'}",
        basicWeek.eventQueue().get(0).toString());
  }

  /**
   * to test the method add
   */
  @Test
  void add() {
    basicWeek.add(MONDAY, new Task("drink", "hydrate"));
    assertEquals("AbstractEvent{name='drink', description='hydrate'}",
        basicWeek.taskQueue().get(0).toString());
  }

  /**
   * to test the method remove
   */
  @Test
  void remove() {
    Task task = new Task("drink", "hydrate");
    basicWeek.add(MONDAY, task);
    basicWeek.remove(MONDAY, task);
    assertEquals(0,
        basicWeek.taskQueue().size());
  }

  /**
   * to test the method setMaximumEvents
   */
  @Test
  void setMaximumEvents() {
    basicWeek.setMaximumEvents(10);
    assertEquals(10, basicWeek.getMaximumEvents());
  }

  /**
   * to test setMaximumTasks
   */
  @Test
  void setMaximumTasks() {
    basicWeek.setMaximumTasks(10);
    assertEquals(10, basicWeek.getMaximumTasks());
  }

  /**
   * to test tasksGreaterThanMaximum
   */
  @Test
  void tasksGreaterThanMaximum() {
    assertFalse(basicWeek.tasksGreaterThanMaximum());
    basicWeek.add(MONDAY, new Task("d", "D"));
    basicWeek.setMaximumTasks(0);
    assertTrue(basicWeek.tasksGreaterThanMaximum());
  }

  /**
   * to test eventsGreaterThanMaximum
   */
  @Test
  void eventsGreaterThanMaximum() {
    assertFalse(basicWeek.eventsGreaterThanMaximum());
    basicWeek.add(MONDAY, new Event("d", "D", new Time(0, 0),
        new Time(1, 1)));
    basicWeek.setMaximumEvents(0);
    assertTrue(basicWeek.eventsGreaterThanMaximum());
  }

  /**
   * to test toString
   */
  @Test
  void testToString() {
    assertTrue(basicWeek.toString().contains("MONDAY"));
    assertTrue(basicWeek.toString().contains("TUESDAY"));
    assertTrue(basicWeek.toString().contains("WEDNESDAY"));
    assertTrue(basicWeek.toString().contains("THURSDAY"));
    assertTrue(basicWeek.toString().contains("FRIDAY"));
    assertTrue(basicWeek.toString().contains("SATURDAY"));
    assertTrue(basicWeek.toString().contains("SUNDAY"));

    ArrayList<Day> days = new ArrayList<>();
    days.add(new Day());
    days.add(new Day());
    days.add(new Day());
    days.add(new Day());
    days.add(new Day());
    days.add(new Day());
    days.add(new Day());
    Week advancedWeek = new Week("what", days, 0, 0);
    assertTrue(advancedWeek.toString().contains("MONDAY"));
    assertTrue(advancedWeek.toString().contains("TUESDAY"));
    assertTrue(advancedWeek.toString().contains("WEDNESDAY"));
    assertTrue(advancedWeek.toString().contains("THURSDAY"));
    assertTrue(advancedWeek.toString().contains("FRIDAY"));
    assertTrue(advancedWeek.toString().contains("SATURDAY"));
    assertTrue(advancedWeek.toString().contains("SUNDAY"));
  }

  /**
   * to test getDays
   */
  @Test
  void testGetDays() {
    ArrayList<Day> expected = new ArrayList<>();
    for (int i = 0; i < 7; i++) {
      expected.add(new Day());
    }
    assertEquals(expected.toString(), basicWeek.getDays().toString());
  }
}