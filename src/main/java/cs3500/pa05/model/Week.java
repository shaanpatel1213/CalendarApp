package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * to represent a week in the CalendarApp
 */
public class Week {
  private String name;
  private Map<WeekDay, Day> days;
  private int maximumEvents;
  private int maximumTasks;

  /**
   * to create a week with no events or tasks in any day
   *
   * @param name the name of the week
   */
  public Week(String name) {
    this.name = name;
    this.days = new HashMap<>();
    this.days.put(WeekDay.MONDAY, new Day());
    this.days.put(WeekDay.TUESDAY, new Day());
    this.days.put(WeekDay.WEDNESDAY, new Day());
    this.days.put(WeekDay.THURSDAY, new Day());
    this.days.put(WeekDay.FRIDAY, new Day());
    this.days.put(WeekDay.SATURDAY, new Day());
    this.days.put(WeekDay.SUNDAY, new Day());
    this.maximumEvents = 1000;
    this.maximumTasks = 1000;
  }

  /**
   * to create a json representation of a week object
   *
   * @param name      the name of the week, that is, the days representing the week
   * @param days      the days in the week as an arraylist
   * @param maxEvents the maximum events allowed in the week
   * @param maxTasks  the maximum tasks allowed in the week
   */
  @JsonCreator
  public Week(@JsonProperty("name") String name,
              @JsonProperty("days") ArrayList<Day> days,
              @JsonProperty("maximumEvents") int maxEvents,
              @JsonProperty("maximumTasks") int maxTasks) {
    this.name = name;
    this.days = new HashMap<>();
    this.days.put(WeekDay.MONDAY, days.get(0));
    this.days.put(WeekDay.TUESDAY, days.get(1));
    this.days.put(WeekDay.WEDNESDAY, days.get(2));
    this.days.put(WeekDay.THURSDAY, days.get(3));
    this.days.put(WeekDay.FRIDAY, days.get(4));
    this.days.put(WeekDay.SATURDAY, days.get(5));
    this.days.put(WeekDay.SUNDAY, days.get(6));
    this.maximumEvents = maxEvents;
    this.maximumTasks = maxTasks;
  }

  /**
   * need this getter it is used by jackson to get all the days and put then in a list
   *
   * @return a list of all the days in the week
   */
  public ArrayList<Day> getDays() {
    ArrayList<Day> list = new ArrayList<>();
    for (WeekDay day : WeekDay.values()) {
      list.add(days.get(day));
    }
    return list;
  }


  /**
   * to set the days of this week to the given days
   *
   * @param days the days of this week as a hashmap between weekday and the day object
   *             representing the information for a given day of the week
   */
  public void setDays(Map<WeekDay, Day> days) {
    this.days = days;
  }

  /**
   * to return the maximum events for this week
   *
   * @return the maximum events for this week
   */
  public int getMaximumEvents() {
    return maximumEvents;
  }

  /**
   * to return the maximum tasks for this week
   *
   * @return the maximum tasks for this week
   */
  public int getMaximumTasks() {
    return maximumTasks;
  }

  /**
   * to set the name for this week
   *
   * @param name the given name for this week
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * to get all the keys out of all the key value pairs in the days hashmap of this week
   *
   * @return all the keys of the key value pairs
   */
  @JsonIgnore
  public Set<WeekDay> getKeySet() {
    return days.keySet();
  }

  /**
   * to return the names for all the days of the week as a list of string
   *
   * @return a list of string representing all the days of the week
   */
  @JsonIgnore
  public static List<String> getListOfDaysName() {
    List<String> toReturn = new ArrayList<>();
    for (WeekDay weekDay : WeekDay.values()) {
      toReturn.add(weekDay.getNameOfDay());
    }
    return toReturn;
  }

  /**
   * to return the tasks for the given day as a list
   *
   * @param day the given day of the week
   * @return the tasks for the given day as a list
   */
  public List<AbstractEvent> getTasksForDay(WeekDay day) {
    List<AbstractEvent> toReturn = new ArrayList<>();
    toReturn.addAll(days.get(day).getEvents());
    return toReturn;
  }

  /**
   * to return the name of this week
   *
   * @return the name of this week as a string
   */
  public String getName() {
    return name;
  }

  /**
   * makes a list of all the tasks in the week and returns them
   *
   * @return a list of all the tasks in the week
   */
  public List<Task> taskQueue() {
    ArrayList<Task> tasks = new ArrayList<>();
    for (WeekDay day : WeekDay.values()) {
      for (AbstractEvent event : days.get(day).getEvents()) {
        if (event instanceof Task) {
          Task task = (Task) event;
          tasks.add(task);
        }
      }
    }
    return tasks;
  }

  /**
   * makes a list of all the events in the week and returns them
   *
   * @return a list of all the events in the week
   */
  public List<Event> eventQueue() {
    ArrayList<Event> events = new ArrayList<>();
    for (WeekDay day : WeekDay.values()) {
      for (AbstractEvent event : days.get(day).getEvents()) {
        if (event instanceof Event) {
          Event task = (Event) event;
          events.add(task);
        }
      }
    }
    return events;
  }

  /**
   * to add a given event to a given day
   *
   * @param day   the given day
   * @param event the given event
   */
  public void add(WeekDay day, AbstractEvent event) {
    Day thisDay = days.get(day);
    thisDay.addEvent(event);
  }

  /**
   * to remove a given event from a given day
   *
   * @param day   the given day
   * @param event the given event
   */
  public void remove(WeekDay day, AbstractEvent event) {
    Day thisDay = days.get(day);
    thisDay.removeEvent(event);
  }

  /**
   * to set the maximum number of events for a week
   *
   * @param maximumEvents the given maximum number of events
   */
  public void setMaximumEvents(int maximumEvents) {
    this.maximumEvents = maximumEvents;
  }

  /**
   * to set the maximum number of tasks for a week
   *
   * @param maximumTasks the given maximum number of tasks
   */
  public void setMaximumTasks(int maximumTasks) {
    this.maximumTasks = maximumTasks;
  }

  /**
   * checks whether the number of tasks in this week are greater than the maximum tasks
   *
   * @return boolean representing the answer to the above question
   */
  public boolean tasksGreaterThanMaximum() {
    return this.taskQueue().size() > maximumTasks;
  }

  /**
   * checks whether the number of events in this week are greater than the maximum events
   *
   * @return boolean representing the answer to the above question
   */
  public boolean eventsGreaterThanMaximum() {
    return this.eventQueue().size() > maximumEvents;
  }

  /**
   * a string representation of the week object
   *
   * @return a string representation of the week object
   */
  @Override
  public String toString() {
    return "Week{"
        + "days=" + days + '}';
  }
}
