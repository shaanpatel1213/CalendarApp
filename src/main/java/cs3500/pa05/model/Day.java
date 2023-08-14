package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * to represent a day in the week of a CalendarApp
 */

public class Day {
  private List<AbstractEvent> events;

  /**
   * constructor that creates a day with no events
   */
  public Day() {
    this.events = new ArrayList<>();
  }

  /**
   * to create a json object out of the day class
   *
   * @param events the events corresponding to the day
   */
  @JsonCreator
  public Day(@JsonProperty("events") ArrayList<AbstractEvent> events) {
    this.events = events;
  }

  /**
   * to add the given event to the list of events of this day
   *
   * @param event the event we wish to add
   */
  public void addEvent(AbstractEvent event) {
    events.add(event);
  }

  /**
   * to remove a given event from the list of events of this day
   *
   * @param event the event we wish to remove
   */
  public void removeEvent(AbstractEvent event) {
    events.remove(event);
  }

  /**
   * to represent a day object as a string
   *
   * @return string representing the day object
   */
  @Override
  public String toString() {
    return "Day{"
        + "events=" + events
        + '}';
  }

  /**
   * setter method to set the events of this day to the given list of events
   *
   * @param events the given list of events
   */
  public void setEvents(List<AbstractEvent> events) {
    this.events = events;
  }

  /**
   * to return the number of events planned for this day
   *
   * @return the number of events
   */
  @JsonIgnore
  public int getNumberOfEvents() {
    return this.events.size();
  }

  /**
   * to return the events for this day
   *
   * @return list of events corresponding to this day
   */
  public List<AbstractEvent> getEvents() {
    return events;
  }
}