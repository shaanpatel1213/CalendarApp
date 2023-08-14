package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * to test the methods in the time class
 */
class TimeTest {
  Time time1;
  Time time2;

  @BeforeEach
  void setUp() {
    time1 = new Time(5, 30);
    time2 = new Time(6, 31);
  }

  /**
   * to test the getHour method
   */
  @Test
  void getHour() {
    assertEquals(5, time1.getHour());
  }

  /**
   * to test the getMinutes method
   */
  @Test
  void getMinutes() {
    assertEquals(30, time1.getMinutes());
  }

  /**
   * to test the setHour method
   */
  @Test
  void setHour() {
    time1.setHour(6);
    assertEquals(6, time1.getHour());
  }

  /**
   * to test the method setMinutes
   */
  @Test
  void setMinutes() {
    time1.setMinutes(50);
    assertEquals(50, time1.getMinutes());
  }

  /**
   * to test timeDifferenceInMinutes
   */
  @Test
  void timeDifferenceInMinutes() {
    assertEquals(61, Time.timeDifferenceInMinutes(time1.getHour(), time1.getMinutes(),
        time2.getHour(), time2.getMinutes()));
  }

  /**
   * to test the method toString
   */
  @Test
  void testToString() {
    String expected = "5:30";
    assertEquals(expected, time1.toString());
    time1.setMinutes(2);
    expected = "5:02";
    assertEquals(expected, time1.toString());
  }
}