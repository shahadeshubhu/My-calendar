import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Event class with all accessor methods
 */
public class Event {
    private String eventName;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate endDate;
    private int dayOfWeek;

    /**
     * constructor
     *
     * @param eventName event name
     * @param date      event start date
     * @param endDate   event end date
     * @param startTime event start time
     * @param endTime   event end time
     */
    public Event(String eventName, LocalDate date, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        this.eventName = eventName;
        this.date = date;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Gets start date
     *
     * @return date
     */
    public LocalDate getStartDate() {
        return date;
    }

    /**
     * Gets event name
     *
     * @return name
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * Gets end time for event
     *
     * @return end time
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Gets start time for event
     *
     * @return start time
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Gets end date for event
     *
     * @return end date
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Gets day of the week in which recurring event occurs
     *
     * @return day of the week in int
     */
    public int getDayOfWeek() {
        return dayOfWeek;
    }
}
