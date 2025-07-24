import java.util.*;

class Bug {
    enum Severity { LOW, MEDIUM, HIGH }
    enum Status { OPEN, IN_PROGRESS, RESOLVED }

    private static int idCounter = 1000;
    private final int id;
    private String title;
    private Severity severity;
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
            int choice = sc.nextInt();
            sc.nextLine(); // clear buffer
            switch (choice) {
                case 1 -> createBug();
                case 2 -> listBugs();
                case 3 -> updateBugStatus();
                case 4 -> filterBugsBySeverity();
                case 5 -> running = false;
                default -> System.out.println("Invalid choice.");
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
        System.out.print("Bug Title: ");
        String title = sc.nextLine();
        System.out.print("Severity (LOW, MEDIUM, HIGH): ");
        Bug.Severity severity = Bug.Severity.valueOf(sc.nextLine().toUpperCase());
        bugs.add(new Bug(title, severity));
        System.out.println("✅ Bug reported successfully.");
    }

    static void listBugs() {
        if (bugs.isEmpty()) {
            System.out.println("No bugs reported.");
            return;
        }
        bugs.forEach(System.out::println);
    }

    static void updateBugStatus() {
        listBugs();
        System.out.print("Enter Bug ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();
        for (Bug b : bugs) {
            if (b.getId() == id) {
                System.out.print("New Status (OPEN, IN_PROGRESS, RESOLVED): ");
                b.updateStatus(Bug.Status.valueOf(sc.nextLine().toUpperCase()));
                System.out.println("✅ Status updated.");
                return;
            }
        }
        System.out.println("❌ Bug not found.");
    }

    static void filterBugsBySeverity() {
        System.out.print("Enter severity to filter (LOW, MEDIUM, HIGH): ");
        Bug.Severity severity = Bug.Severity.valueOf(sc.nextLine().toUpperCase());
        bugs.stream()
                .filter(b -> b.getSeverity() == severity)
                .forEach(System.out::println);
    }
}
