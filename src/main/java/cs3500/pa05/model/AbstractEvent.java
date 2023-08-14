package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.awt.Desktop;
import java.net.URI;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.Hyperlink;

/**
 * an abstract class representing the common properties of any type of event in the CalendarApp
 */
public abstract class AbstractEvent {
  private String name;
  private String description;
  private WeekDay day;

  /**
   * to create a json object of the event
   *
   * @param name name of the abstract event
   * @param description the description or details of the given abstract event
   */
  @JsonCreator
  public AbstractEvent(@JsonProperty("name") String name,
                       @JsonProperty("description") String description) {
    this.name = name;
    this.description = description;
  }

  /**
   * to return the name of an abstract event
   *
   * @return String representing the name
   */
  public String getName() {
    return name;
  }

  /**
   * setter method to give the name to an abstract event
   *
   * @param name the new name for the abstract event
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * to return the description of an abstract event
   *
   * @return String representing the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * setter method to give the description to an abstract event
   *
   * @param description the new description for the abstract event
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * to set the day of an event to a given day
   *
   * @param day the given day for the event
   */
  public void setDay(WeekDay day) {
    this.day = day;
  }

  /**
   * to return the Weekday of the abstract event
   *
   * @return the day of the abstract event
   */
  public WeekDay getDay() {
    return day;
  }

  /**
   * to represent the abstract event in a string format
   *
   * @return string representing the abstract event
   */
  @Override
  public String toString() {
    return "AbstractEvent{"
        + "name='" + name + '\''
        + ", description='" + description + '\'' + '}';
  }

  /**
   * helps parse URL from the description of an abstract event
   *
   * @return a list of URL parsed from the description
   */
  public ArrayList<Hyperlink> parseUrl() {
    ArrayList<Hyperlink> links = new ArrayList<>();
    String linkPattern =
        "(http|ftp|https):\\/\\/([\\w_-]+(?:(?:\\.[\\w_-]+)+))"
            + "([\\w.,@?^=%&:\\/~+#-]*[\\w@?^=%&\\/~+#-])";
    Pattern pattern = Pattern.compile(linkPattern);
    Matcher matcher = pattern.matcher(description);
    while (matcher.find()) {
      String url = matcher.group();
      Hyperlink link = new Hyperlink(url);
      link.setOnAction(e -> {
        System.out.println("This link is clicked");
        try {
          Desktop desktop = java.awt.Desktop.getDesktop();
          URI uri = new URI(url);
          desktop.browse(uri);
        } catch (Exception j) {
          j.printStackTrace();
        }
      });
      links.add(link);
      System.out.println("Link found: " + url);
    }
    return links;
  }
}
