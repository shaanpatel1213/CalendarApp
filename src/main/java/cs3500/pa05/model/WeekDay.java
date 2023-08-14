package cs3500.pa05.model;

/**
 * the different days of the week
 */
public enum WeekDay {
  /**
   * Monday
   */
  MONDAY("Monday"),
  /**
   * tuesday
   */
  TUESDAY("Tuesday"),
  /**
   * wednesday
   */
  WEDNESDAY("Wednesday"),
  /**
   * thursday
   */
  THURSDAY("Thursday"),
  /**
   * friday
   */
  FRIDAY("Friday"),
  /**
   * saturday
   */
  SATURDAY("Saturday"),
  /**
   * sunday
   */
  SUNDAY("Sunday");

  private final String nameOfDay;

  WeekDay(String nameOfDay) {
    this.nameOfDay = nameOfDay;
  }

  /**
   * to return the name of a day in the week
   *
   * @return the name of a day as a string
   */
  public String getNameOfDay() {
    return nameOfDay;
  }
}
