import java.util.List;
import java.util.Scanner;

public class BugTrackrApp {
    private static final Scanner sc = new Scanner(System.in);
    private static final BugDAO bugDAO = new BugDAO();

    public static void main(String[] args) {
        System.out.println("=== BugTrackr - DB Connected ===");

        while (true) {
            showMenu();
            try {
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1 -> createBug();
                    case 2 -> listBugs();
                    case 3 -> updateStatus();
                    case 4 -> filterBySeverity();
                    case 5 -> clearAllBugs();   // new option
                    case 6 -> { System.out.println("Exiting..."); return; }
                    default -> System.out.println("Invalid option!");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number.");
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n1. Report New Bug");
        System.out.println("2. List All Bugs");
        System.out.println("3. Update Bug Status");
        System.out.println("4. Filter Bugs by Severity");
        System.out.println("5. Clear All Bugs");  // new option
        System.out.println("6. Exit");            // update exit option
        System.out.print("Choose: ");
    }


    private static void createBug() {
        try {
            System.out.print("Title: ");
            String title = sc.nextLine();

            Bug.Severity severity;
            while (true) {
                System.out.print("Severity (LOW, MEDIUM, HIGH): ");
                try {
                    severity = Bug.Severity.valueOf(sc.nextLine().toUpperCase());
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid severity!");
                }
            }

            bugDAO.addBug(new Bug(title, severity));

        } catch (Exception e) {
            System.out.println("Error creating bug: " + e.getMessage());
        }
    }

    private static void listBugs() {
        List<Bug> bugs = bugDAO.listBugs();
        if (bugs.isEmpty()) System.out.println("No bugs reported.");
        else bugs.forEach(System.out::println);
    }

    private static void updateStatus() {
        listBugs();
        try {
            System.out.print("Enter Bug ID: ");
            int id = Integer.parseInt(sc.nextLine());

            Bug.Status status;
            while (true) {
                System.out.print("New Status (OPEN, IN_PROGRESS, RESOLVED): ");
                try {
                    status = Bug.Status.valueOf(sc.nextLine().toUpperCase());
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid status!");
                }
            }

            bugDAO.updateStatus(id, status);

        } catch (NumberFormatException e) {
            System.out.println("Invalid ID. Must be a number.");
        } catch (Exception e) {
            System.out.println("Error updating status: " + e.getMessage());
        }
    }

    private static void filterBySeverity() {
        try {
            Bug.Severity severity;
            while (true) {
                System.out.print("Severity to filter (LOW, MEDIUM, HIGH): ");
                try {
                    severity = Bug.Severity.valueOf(sc.nextLine().toUpperCase());
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid severity!");
                }
            }

            List<Bug> bugs = bugDAO.filterBySeverity(severity);
            if (bugs.isEmpty()) System.out.println("No bugs with severity " + severity);
            else bugs.forEach(System.out::println);

        } catch (Exception e) {
            System.out.println("Error filtering bugs: " + e.getMessage());
        }
    }
    private static void clearAllBugs() {
        try {
            bugDAO.clearAllBugs();   // method you will add in BugDAO
        } catch (Exception e) {
            System.out.println("Error clearing bugs: " + e.getMessage());
        }
    }

}
