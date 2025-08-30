public class Bug {
    public enum Severity { LOW, MEDIUM, HIGH }
    public enum Status { OPEN, IN_PROGRESS, RESOLVED }

    private int id;
    private String title;
    private Severity severity;
    private Status status;

    public Bug(int id, String title, Severity severity, Status status) {
        this.id = id;
        this.title = title;
        this.severity = severity;
        this.status = status;
    }

    public Bug(String title, Severity severity) {
        this.title = title;
        this.severity = severity;
        this.status = Status.OPEN;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public Severity getSeverity() { return severity; }
    public Status getStatus() { return status; }

    @Override
    public String toString() {
        return String.format("ID: %d | %s | Severity: %s | Status: %s",
                id, title, severity, status);
    }
}
