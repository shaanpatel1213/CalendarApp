package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * to test the methods in event class
 */
class EventTest {
  Time startTime;
  Time duration;
  Event event;

  @BeforeEach
  void setUp() {
    startTime = new Time(5, 30);
    duration = new Time(1, 1);
    event = new Event("Drink water", "keep myself hydrated",
        startTime, duration);
  }

  /**
   * to test the getStartTime method
   */
  @Test
  void getStartTime() {
    assertEquals(startTime, event.getStartTime());
  }

  /**
   * to test the getDuration method
   */
  @Test
  void getDuration() {
    assertEquals(duration, event.getDuration());
  }

  /**
   * to test the setStartTime method
   */
  @Test
  void setStartTime() {
    Time newTime = new Time(4, 30);
    event.setStartTime(newTime);
    assertEquals(newTime, event.getStartTime());
  }

  /**
   * to test the setDuration method
   */
  @Test
  void setDuration() {
    Time newDuration = new Time(4, 30);
    event.setDuration(newDuration);
    assertEquals(newDuration, event.getDuration());
  }
}