import java.sql.*;
 import java.util.Scanner;

public class UserAuthentication { private static final String DB_URL = "jdbc:sqlite:users.db";

public static void main(String[] args) {
    createUsersTable();
    
    Scanner scanner = new Scanner(System.in);
    while (true) {
        System.out.println("1. Register\n2. Login\n3. Exit");
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1:
                System.out.print("Enter username: ");
                String regUsername = scanner.nextLine();
                System.out.print("Enter password: ");
                String regPassword = scanner.nextLine();
                registerUser(regUsername, regPassword);
                break;
            case 2:
                System.out.print("Enter username: ");
                String loginUsername = scanner.nextLine();
                System.out.print("Enter password: ");
                String loginPassword = scanner.nextLine();
                if (authenticateUser(loginUsername, loginPassword)) {
                    System.out.println("Login successful!");
                } else {
                    System.out.println("Invalid username or password.");
                }
                break;
            case 3:
                System.out.println("Exiting...");
                scanner.close();
                return;
            default:
                System.out.println("Invalid choice. Try again.");
        }
    }
}

private static void createUsersTable() {
    try (Connection conn = DriverManager.getConnection(DB_URL);
         Statement stmt = conn.createStatement()) {
        String sql = "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE, password TEXT)";
        stmt.execute(sql);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private static void registerUser(String username, String password) {
    String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
    try (Connection conn = DriverManager.getConnection(DB_URL);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        pstmt.executeUpdate();
        System.out.println("User registered successfully!");
    } catch (SQLException e) {
        System.out.println("Registration failed: " + e.getMessage());
    }
}

private static boolean authenticateUser(String username, String password) {
    String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
    try (Connection conn = DriverManager.getConnection(DB_URL);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        ResultSet rs = pstmt.executeQuery();
        return rs.next();
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

}