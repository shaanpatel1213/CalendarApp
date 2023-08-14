package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * represents time in hours and minutes for any event
 */
public class Time {
  private int hour;
  private int minutes;

  /**
   * to create a json object corresponding to the time object
   *
   * @param hour hour in the time class
   * @param minutes minutes in the time class
   */
  @JsonCreator
  public Time(@JsonProperty("hour") int hour, @JsonProperty("min") int minutes) {
    this.hour = hour;
    this.minutes = minutes;
  }

  /**
   * to return the hour for this time
   *
   * @return the hour as an integer
   */
  public int getHour() {
    return hour;
  }

  /**
   * to return the minutes for this time
   *
   * @return hte minutes as an integer
   */
  public int getMinutes() {
    return minutes;
  }

  /**
   * to set the hour to the given hour
   *
   * @param hour the given hour
   */
  public void setHour(int hour) {
    this.hour = hour;
  }

  /**
   * to set the minutes to the given minutes
   *
   * @param minutes the given minutes
   */
  public void setMinutes(int minutes) {
    this.minutes = minutes;
  }

  /**
   * helps find the time difference between two given times in minutes
   *
   * @param hour1 the hour for the first time
   * @param minutes1 the minutes for the first time
   * @param hour2 the hour for the second time
   * @param minutes2 the minutes for the second time
   * @return the time difference between two given times in minutes
   */
  public static int timeDifferenceInMinutes(int hour1, int minutes1, int hour2, int minutes2) {
    return Math.abs((hour1 * 60 + minutes1) - (hour2 * 60 + minutes2));
  }

  /**
   * to represent the time as a string
   *
   * @return string representation of time
   */
  @Override
  public String toString() {
    String time;
    if (this.minutes < 10) {
      time = hour + ":0" + minutes;
    } else {
      time = hour + ":" + minutes;
    }
    return time;
  }
}
