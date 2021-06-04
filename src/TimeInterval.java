import java.time.LocalDate;
import java.time.LocalTime;

public class TimeInterval extends MyCalendar {
    private LocalTime start;
    private LocalTime end;
    private LocalDate eventDate;

    public TimeInterval(LocalDate eventDate, LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
        this.eventDate = eventDate;
    }

    public boolean timeConflict() {
        for (Event s : events) {
            if (((eventDate.equals(s.getStartDate())) && ((((start.compareTo(s.getStartTime())) >= 0) && ((start.compareTo(end)) < 0)) ||
                    ((end.compareTo(s.getStartTime())) > 0) && (end.compareTo(s.getEndTime())) <= 0))) {
                System.out.println("there is a conflict");
                return true;
            }
        }
        return false;
    }
}
