package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * to represent an event in the CalendarApp
 */
public class Event extends AbstractEvent {
  Time startTime;
  Time duration;

  /**
   * to return the start time of the event
   *
   * @return the start time of the event
   */
  public Time getStartTime() {
    return startTime;
  }

  /**
   * to return the duration of the event
   *
   * @return the duration of the event
   */
  public Time getDuration() {
    return duration;
  }

  /**
   * to create a json object out of the event
   *
   * @param name the name of the event
   * @param description the description of the event
   * @param startTime the time the event starts
   * @param duration the duration of the event
   */
  @JsonCreator
  public Event(@JsonProperty("name") String name,
               @JsonProperty("description") String description,
               @JsonProperty("StartTime") Time startTime,
               @JsonProperty("duration") Time duration) {
    super(name, description);
    this.startTime = startTime;
    this.duration = duration;
  }

  /**
   * setter method to set the start time of an event to the given time
   *
   * @param startTime the given start time of the event
   */
  public void setStartTime(Time startTime) {
    this.startTime = startTime;
  }

  /**
   * setter method to set the duration of an event to a given duration
   *
   * @param duration the given duration of the event
   */
  public void setDuration(Time duration) {
    this.duration = duration;
  }
}