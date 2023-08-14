package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * to test the methods in the enum WeekDay
 */
class WeekDayTest {

  /**
   * to test the method getNameOfDay
   */
  @Test
  void getNameOfDay() {
    WeekDay weekDay = WeekDay.MONDAY;
    assertEquals("Monday", weekDay.getNameOfDay());
  }
}