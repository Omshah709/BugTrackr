import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BugDAO{

    public void addBug(Bug bug) {
        String sql = "INSERT INTO bugs (title, severity, status) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, bug.getTitle());
            stmt.setString(2, bug.getSeverity().name());
            stmt.setString(3, bug.getStatus().name());
            stmt.executeUpdate();

            System.out.println("Bug reported successfully!");

        } catch (SQLSyntaxErrorException e) {
            System.out.println("SQL Syntax Error: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    public List<Bug> listBugs() {
        List<Bug> bugs = new ArrayList<>();
        String sql = "SELECT * FROM bugs";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                bugs.add(new Bug(
                        rs.getInt("id"),
                        rs.getString("title"),
                        Bug.Severity.valueOf(rs.getString("severity")),
                        Bug.Status.valueOf(rs.getString("status"))
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching bugs: " + e.getMessage());
        }

        return bugs;
    }

    public void updateStatus(int id, Bug.Status status) {
        String sql = "UPDATE bugs SET status=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status.name());
            stmt.setInt(2, id);

            if (stmt.executeUpdate() > 0)
                System.out.println("Status updated successfully!");
            else
                System.out.println("No bug found with ID: " + id);

        } catch (SQLException e) {
            System.out.println("Error updating status: " + e.getMessage());
        }
    }

    public List<Bug> filterBySeverity(Bug.Severity severity) {
        List<Bug> bugs = new ArrayList<>();
        String sql = "SELECT * FROM bugs WHERE severity=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, severity.name());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                bugs.add(new Bug(
                        rs.getInt("id"),
                        rs.getString("title"),
                        Bug.Severity.valueOf(rs.getString("severity")),
                        Bug.Status.valueOf(rs.getString("status"))
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error filtering bugs: " + e.getMessage());
        }

        return bugs;
    }

    public void clearAllBugs() {
        String sql = "TRUNCATE TABLE bugs"; // or "DELETE FROM bugs"
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("All bugs cleared successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
