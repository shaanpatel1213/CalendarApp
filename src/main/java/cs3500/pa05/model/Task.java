package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * to represent a task in the CalendarApp
 */
public class Task extends AbstractEvent {

  private boolean complete;

  /**
   * to create a json object corresponding to a task object
   *
   * @param name the name of the task
   * @param description the task description
   */
  @JsonCreator
  public Task(
      @JsonProperty("name") String name, @JsonProperty("description") String description) {
    super(name, description);
    this.complete = false;
  }

  /**
   * to set the completion status of a task to a given completion status
   *
   * @param complete the given completion status for a task
   */
  public void setComplete(boolean complete) {
    this.complete = complete;
  }

  /**
   * to find if the given task is complete
   *
   * @return the completion status of this task
   */
  public boolean isComplete() {
    return complete;
  }
}
