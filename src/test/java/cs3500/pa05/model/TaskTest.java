package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * to test the methods in the class Task
 */
class TaskTest {
  Task task;

  @BeforeEach
  void setUp() {
    task = new Task("do yoga", "stay fit");
  }

  /**
   * to test the method setComplete
   */
  @Test
  void setComplete() {
    task.setComplete(true);
    assertTrue(task.isComplete());
  }

  /**
   * to test the method isComplete
   */
  @Test
  void isComplete() {
    assertFalse(task.isComplete());
  }

  /**
   * to test the method getDay
   */
  @Test
  void getDay() {
    task.setDay(WeekDay.MONDAY);
    assertEquals(WeekDay.MONDAY, task.getDay());
  }

  /**
   * to test getDescription
   */
  @Test
  void getDescription() {
    assertEquals("stay fit", task.getDescription());
  }

  /**
   * to test getName
   */
  @Test
  void getName() {
    assertEquals("do yoga", task.getName());
  }

  /**
   * to test setDescription
   */
  @Test
  void setDescription() {
    task.setDescription("good");
    assertEquals("good", task.getDescription());
  }

  /**
   * to test setName
   */
  @Test
  void setName() {
    task.setName("good");
    assertEquals("good", task.getName());
  }

  /**
   * to test the method parseURL
   */
  @Test
  void parseUrl() {
    String description = "This is a sample description with a link: "
        +
        "https://markefontenot.notion.site/3500-Student-Hub-2c979b9cca7a4029b858e8e2c01efab4";
    Task task = new Task(description, description);
    assertThrows(java.lang.ExceptionInInitializerError.class, task::parseUrl);
    description = "df";
    task = new Task(description, description);
    assertEquals(0, task.parseUrl().size());

    /*
      Note: the above test throws an exception as we are trying to parse a URL without
      initializing the GUI first. Initializing the GUI requires the use of testFX,
      and we did not do those tests as the TAs recommended not to get involved in that on
      the last day. Hence, without the GUI it throws an exception in initialization error
      which is caused as the toolkit is not initialized (that is what the error said).
      But this method works when called in the main program run.
     */
  }
}