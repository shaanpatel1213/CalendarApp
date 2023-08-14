package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.Week;

/**
 * a record representing all the information of a given week
 *
 * @param week the given week
 */
public record CalendarRecord(@JsonProperty("password") String password,
                             @JsonProperty("Week") Week week) {
}
