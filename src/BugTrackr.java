import java.util.*;

class Bug {
    enum Severity { LOW, MEDIUM, HIGH }
    enum Status { OPEN, IN_PROGRESS, RESOLVED }

    private static int idCounter = 1;
    private final int id;
    private final String title;
    private final Severity severity;
    private Status status;

    public Bug(String title, Severity severity) {
        this.id = idCounter++;
        this.title = title;
        this.severity = severity;
        this.status = Status.OPEN;
    }

    public int getId() { return id; }
    public Severity getSeverity() { return severity; }
    public Status getStatus() { return status; }

    public void updateStatus(Status newStatus) {
        this.status = newStatus;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | %s | Severity: %s | Status: %s", id, title, severity, status);
    }
}

public class BugTrackr {
    private static final List<Bug> bugs = new ArrayList<>();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== BugTrackr - Java CLI Bug Tracker ===");
        boolean running = true;
        while (running) {
            showMenu();
            try {
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1 -> createBug();
                    case 2 -> listBugs();
                    case 3 -> updateBugStatus();
                    case 4 -> filterBugsBySeverity();
                    case 5 -> running = false;
                    default -> System.out.println("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    static void showMenu() {
        System.out.println("\n1. Report New Bug");
        System.out.println("2. List All Bugs");
        System.out.println("3. Update Bug Status");
        System.out.println("4. Filter Bugs by Severity");
        System.out.println("5. Exit");
        System.out.print("Select an option: ");
    }

    static void createBug() {
        try {
            System.out.print("Bug Title: ");
            String title = sc.nextLine();

            Bug.Severity severity = null;
            while (true) {
                System.out.print("Severity (LOW, MEDIUM, HIGH): ");
                String severityInput = sc.nextLine().toUpperCase();
                try {
                    severity = Bug.Severity.valueOf(severityInput);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid severity! Please enter LOW, MEDIUM, or HIGH.");
                }
            }

            bugs.add(new Bug(title, severity));
            System.out.println("Bug reported successfully.");
        } catch (Exception e) {
            System.out.println("Error while creating bug: " + e.getMessage());
        }
    }

    static void updateBugStatus() {
        listBugs();
        try {
            System.out.print("Enter Bug ID to update: ");
            int id = Integer.parseInt(sc.nextLine());

            boolean found = false;
            for (Bug b : bugs) {
                if (b.getId() == id) {
                    found = true;
                    while (true) {
                        System.out.print("New Status (OPEN, IN_PROGRESS, RESOLVED): ");
                        String input = sc.nextLine().toUpperCase();
                        try {
                            Bug.Status newStatus = Bug.Status.valueOf(input);
                            b.updateStatus(newStatus);
                            System.out.println("Status updated.");
                            return;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid status! Please enter one of: OPEN, IN_PROGRESS, RESOLVED.");
                        }
                    }
                }
            }

            if (!found) {
                System.out.println("Bug not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID. Please enter a number.");
        } catch (Exception e) {
            System.out.println("Error while updating status: " + e.getMessage());
        }
    }

    static void filterBugsBySeverity() {
        try {
            Bug.Severity severity = null;
            while (true) {
                System.out.print("Enter severity to filter (LOW, MEDIUM, HIGH): ");
                String input = sc.nextLine().toUpperCase();
                try {
                    severity = Bug.Severity.valueOf(input);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid severity! Please enter LOW, MEDIUM, or HIGH.");
                }
            }

            boolean found = false;
            for (Bug b : bugs) {
                if (b.getSeverity() == severity) {
                    System.out.println(b);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("No bugs with severity: " + severity);
            }
        } catch (Exception e) {
            System.out.println("Error filtering bugs: " + e.getMessage());
        }
    }


    static void listBugs() {
        try {
            if (bugs.isEmpty()) {
                System.out.println("No bugs reported.");
                return;
            }
            bugs.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Failed to list bugs: " + e.getMessage());
        }
    }}

