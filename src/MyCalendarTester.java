import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * Main class where program runs
 */
public class MyCalendarTester {
    public static void main(String[] args) {

        // loading events from file pending
        printCalendar();
        LocalDate cal = LocalDate.now();
        MyCalendar calendar = new MyCalendar();
        Scanner userInput = new Scanner(System.in);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        label:
        while (true) {
            System.out.println("Select one of the following main menu options:\n" +
                    "[V]iew by  [C]reate, [G]o to [E]vent list [D]elete  [Q]uit\n");
            String options = userInput.nextLine();
            label1:
            switch (options) {
                case "V":
                    System.out.println("[D]ay view or [M]view ?");
                    String input = userInput.nextLine();
                    if (input.equals("D")) {
                        MyCalendar.printDate(cal);
                        calendar.getEventsForDate(cal);                        // Print events for following day

                        System.out.println("[P]revious or [N]ext or [G]o back to the main menu ?");
                        input = userInput.nextLine();
                        switch (input) {
                            case "P":
                                MyCalendar.printDate(cal.minusDays(1));
                                calendar.getEventsForDate(cal.minusDays(1));    // Print events for previous day
                                // Recurring events pending
                                break;
                            case "N":
                                MyCalendar.printDate(cal.plusDays(1));
                                calendar.getEventsForDate(cal.plusDays(1));     // print events for next day
                                // Recurring events pending
                                break;
                            case "G":
                                break label1;                                   //Go back to main menu
                        }
                    } else if (input.equals("M")) {
                        calendar.printMonthWithEvents(cal);                  // Highlights day with {} if event on that day
                        // Recurring events pending
                        System.out.println("[P]revious or [N]ext or [G]o back to the main menu ?");
                        input = userInput.nextLine();
                        switch (input) {
                            case "P":
                                calendar.printMonthWithEvents(cal.minusMonths(1));   // Go to previous month
                                // Recurring events pending
                                break;
                            case "N":
                                calendar.printMonthWithEvents(cal.plusMonths(1));    // Go to next month
                                // Recurring events pending
                                break;
                            case "G":
                                break label1;                                        //Go back to main menu
                        }
                    }
                    break;

                case "C":                                                           // Creates an event.
                    System.out.println("Name: ");
                    String name = userInput.nextLine();
                    System.out.println("Date: MM/DD/yyyy");
                    String newEventDate = userInput.nextLine();
                    LocalDate eventDate = LocalDate.parse(newEventDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                    System.out.println(eventDate);
                    System.out.println("Start time in 24 hr format");
                    String start = userInput.nextLine();
                    LocalTime startTime = LocalTime.parse(start, timeFormatter);
                    System.out.println(startTime);
                    System.out.println("End time in 24 hr format");
                    String end = userInput.nextLine();
                    LocalTime endTime = LocalTime.parse(end, timeFormatter);
                    System.out.println(endTime);
                    calendar.createEvent(name, eventDate, startTime, endTime);
                    // Checking for time conflict pending
                    break;

                case "G":                                      // Goes and prints events for given date
                    System.out.println("Enter date in MM/DD/yyyy format");
                    String date = userInput.nextLine();
                    LocalDate gotoDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                    MyCalendar.printDate(gotoDate);
                    calendar.getEventsForDate(gotoDate);       // print events for given date
                    break;
                case "E":
                    System.out.println("ONE TIME EVENTS");
                    calendar.getOneTimeEvents();                       // Prints one time events
                    System.out.println(" RECURRING EVENTS");
                    calendar.getRecurringEvents();                     // Prints recurring events

                    break;
                case "D":
                    System.out.println("[S] [A]ll [DR]");
                    String deleteType = userInput.nextLine();
                    switch (deleteType) {
                        case "S":
                            System.out.println("Enter the date [MM/dd/yyyy]");
                            String Date = userInput.nextLine();
                            LocalDate deleteSelected = LocalDate.parse(Date, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                            calendar.getEventsForDate(deleteSelected);
                            System.out.println("Enter name of the event to delete");
                            String eventNameToDelete = userInput.nextLine();
                            calendar.deleteSelectedEvent(deleteSelected, eventNameToDelete);    // delete one time event.
                            break;
                        case "A":
                            System.out.println("Enter the date [MM/dd/yyyy]");
                            String deleteDate = userInput.nextLine();
                            LocalDate deleteAll = LocalDate.parse(deleteDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                            calendar.deleteEventsForDate(deleteAll);                       // deletes all one time events for deleteDate;
                            break;
                        case "DR":
                            System.out.println("Enter recurring event name to be deleted");
                            String recurringName = userInput.nextLine();
                            calendar.deleteRecurringEvent(recurringName);                   // deletes all recurring events for given name;
                            break;
                    }

                    break;
                case "Q":
                    System.out.println("Good Bye");
                    // Save current events in file called output.txt pending
                    break label;
            }
        }
    }

    public static void printCalendar() {
        LocalDate today = LocalDate.now();
        Calendar cal = new GregorianCalendar(today.getYear(), today.getMonth().getValue() - 1, today.getDayOfMonth());
        cal.set(Calendar.DAY_OF_MONTH, 1); //Set the day of month to 1
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); //get day of week for 1st of month
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

//print month name and year
        System.out.println(new SimpleDateFormat("       MMMM YYYY").format(cal.getTime()));
        System.out.println(" Su  Mo  Tu  We  Th  Fr  Sa");

//print initial spaces
        System.out.print("    ".repeat(Math.max(0, dayOfWeek - 1)));

//print the days of the month starting from 1
        for (int i = 0, dayOfMonth = 1; dayOfMonth <= daysInMonth; i++) {
            for (int j = ((i == 0) ? dayOfWeek - 1 : 0); j < 7 && (dayOfMonth <= daysInMonth); j++) {
                if (dayOfMonth == today.getDayOfMonth()) {
                    System.out.printf("[" + "%2d" + "]", dayOfMonth);
                } else {
                    System.out.printf(" %2d ", dayOfMonth);
                }
                dayOfMonth++;
            }
            System.out.println();
        }
    }
}
