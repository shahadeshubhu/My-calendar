import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Calendar class where most methods are called
 */
public class MyCalendar {
    List<Event> events;
    List<Event> recurringEvents;

    /**
     * Constructor to create array lists
     */
    public MyCalendar() {
        events = new ArrayList<>();
        recurringEvents = new ArrayList<>();
    }

    /**
     * Creates one time events
     *
     * @param name  event name
     * @param date  event start date
     * @param start event start time
     * @param end   event end time
     */
    public void createEvent(String name, LocalDate date, LocalTime start, LocalTime end) {
        //TimeInterval check = new TimeInterval(date, start, end);
        //if (!check.timeConflict()) {
        Event event = new Event(name, date, date, start, end);
        insert(event);
        System.out.println("Event created");
        //} else {
        //      System.out.println("There is a time conflict");
        //    }
    }

    /**
     * Creates recurring event
     *
     * @param name      event name
     * @param startDate start date for event
     * @param endDate   end date for event
     * @param dayOfWeek day/s of the week whenevent occurs
     * @param start     starting time for recurring event
     * @param end       ending time for recurring event
     */
    public void createRecurringEvent(String name, LocalDate startDate, LocalDate endDate, int dayOfWeek, LocalTime start, LocalTime end) {
        label:
        {
            while (startDate.compareTo(endDate) <= 0) {
                if (startDate.getDayOfWeek().getValue() == dayOfWeek) {
                    // check if there is a conflict
                    //if there is time conflict, break label and print "time conflict."
                    Event recurringEvent = new Event(name, startDate, endDate, start, end);
                    recurringEvents.add(recurringEvent);
                }
                startDate = startDate.plusDays(1);
            }
            for (Event r : recurringEvents) {
                Event event = new Event(r.getEventName(), r.getStartDate(), r.getEndDate(), r.getStartTime(), r.getEndTime());
                insert(event);
            }
            System.out.println("Events created");
        }
    }

    /**
     * Used to inser event in array list in order
     *
     * @param event
     */
    private void insert(Event event) {
        int i;
        for (i = 0; i < events.size(); i++) {
            if (events.get(i).getStartTime().compareTo(event.getStartTime()) <= 0) {
                continue;
            } else
                break;
        }
        events.add(null);
        for (int j = events.size() - 1; j > i; j--) {
            events.set(j, events.get(j - 1));
        }
        events.set(i, event);
    }

    /**
     * Gets all events for given date
     *
     * @param c date for which we want to print events
     */
    public void getEventsForDate(LocalDate c) {
        if (events.isEmpty()) {
            System.out.println("No events in calendar");
        }
        List<Event> eventList = new ArrayList<Event>();
        for (Event s : events) {
            if (s.getStartDate().equals(c)) {
                eventList.add(s);
            }
        }
        if (eventList.isEmpty()) {
            System.out.println("No events for given date");
        } else {
            for (Event e : eventList) {
                System.out.println(e.getEventName() + " : " + e.getStartTime() + "-" + e.getEndTime());
                System.out.println();
            }
        }
    }

    /**
     * prints all one time events
     */
    public void getOneTimeEvents() {
        for (Event s : events) {
            if (!inRecurring(s)) {
                System.out.print(s.getStartDate() + " " + s.getEventName() + "  " + s.getStartTime() + "-" + s.getEndTime());
                System.out.println();
            }
        }
    }

    /**
     * Prints all recurring events
     */
    public void getRecurringEvents() {
        for (Event s : recurringEvents) {
            System.out.print(s.getEventName());
            System.out.println(s.getStartTime() + "-" + s.getEndTime()
                    + " " + s.getStartDate() + " " + s.getEndDate());
            System.out.println();
        }
    }

    /**
     * used for testing. prints all events in array list
     */
    public void getEvents() {
        for (Event s : events) {
            System.out.print(s.getStartDate() + " " + s.getEventName() + " " + s.getStartTime() + "-" + s.getEndTime());
            System.out.println();
        }
    }

    /**
     * Deletes one time event for selected date
     *
     * @param c         date for which one time event is to be deleted
     * @param eventName name of event to be deleted
     */
    public void deleteSelectedEvent(LocalDate c, String eventName) {
        for (Event s : events) {
            if (c.equals(s.getStartDate()) && eventName.equals(s.getEventName()) && !(recurringHas(s))) {
                events.remove(s);
                break;
            }
        }
        System.out.println("Event deleted");
    }

    /**
     * Deletes all one time events for given date
     *
     * @param c given date
     */
    public void deleteEventsForDate(LocalDate c) {
        for (Event s : events) {
            if ((!inRecurring(s))) {
                if (s.getStartDate().equals(c)) {
                    events.remove(s);
                    System.out.println("Event deleted");
                    deleteEventsForDate(c);
                    break;
                }
            }
        }
    }

    /**
     * Deletes all recurring events for given name
     *
     * @param eventName given name
     */
    public void deleteRecurringEvent(String eventName) {
        for (Event s : events) {
            if ((inRecurring(s))) {
                if (s.getEventName().equals(eventName)) {
                    events.remove(s);
                    deleteRecurringEvent(eventName);
                    break;
                }
            }
        }
        deleteFromRecurring(eventName);
        System.out.println("Recurring events deleted");
    }

    /**
     * Deletes events from recurring array list
     *
     * @param eventName
     */
    private void deleteFromRecurring(String eventName) {
        for (Event r : recurringEvents) {
            if (r.getEventName().equals(eventName)) {
                recurringEvents.remove(r);
                deleteFromRecurring(eventName);
                break;
            }
        }
    }

    /**
     * Helper method to check if event is recurring
     *
     * @param e event to be checked
     * @return true or false
     */
    private boolean recurringHas(Event e) {
        for (Event r : recurringEvents) {
            if (e.equals(r)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method
     *
     * @param e
     * @return
     */
    private boolean inRecurring(Event e) {
        for (Event r : recurringEvents) {
            if ((e.getStartDate().equals(r.getStartDate())) && (e.getEventName().equals(r.getEventName())) &&
                    (e.getStartTime().equals(r.getStartTime())) && (e.getEndTime().equals(r.getEndTime()))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Prints date in desired format for given date
     *
     * @param c given date
     */
    public static void printDate(LocalDate c) {
        //  To print a calendar in a specified format.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMM d, yyyy");
        System.out.println(" " + formatter.format(c));
    }

    /**
     * prints monthly calendar with events
     *
     * @param today date
     */
    public void printMonthWithEvents(LocalDate today) {
        Calendar cal = new GregorianCalendar(today.getYear(), today.getMonth().getValue() - 1, today.getDayOfMonth());
        cal.set(Calendar.DAY_OF_MONTH, 1);                  //Set the day of month to 1
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);      //get day of week for 1st of month
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        //print month name and year
        System.out.println(new SimpleDateFormat("      MMMM YYYY").format(cal.getTime()));
        System.out.println(" Su  Mo  Tu  We  Th  Fr  Sa");

        //print initial spaces
        System.out.print("    ".repeat(Math.max(0, dayOfWeek - 1)));

        //print the days of the month starting from 1
        for (int i = 0, dayOfMonth = 1; dayOfMonth <= daysInMonth; i++) {
            for (int j = ((i == 0) ? dayOfWeek - 1 : 0); j < 7 && (dayOfMonth <= daysInMonth); j++) {
                if (checkEventDate(dayOfMonth, today.getMonth())) {
                    System.out.printf("{" + "%2d" + "}", dayOfMonth);

                } else {
                    System.out.printf(" %2d ", dayOfMonth);
                }
                dayOfMonth++;
            }
            System.out.println();
        }
    }

    /**
     * Helper method
     *
     * @param dayOfMonth
     * @return
     */
    private boolean checkEventDate(int dayOfMonth, Month month) {
        for (Event s : events) {
            if (s.getStartDate().getMonth() == month && s.getStartDate().getDayOfMonth() == dayOfMonth) {
                return true;
            }
        }
        return false;
    }
}
