import java.sql.*;
 import java.util.Scanner;

public class QuizManagement { private static final String DB_URL = "jdbc:sqlite:quiz.db";

public static void main(String[] args) {
    createQuizTable();
    
    Scanner scanner = new Scanner(System.in);
    while (true) {
        System.out.println("1. Add Quiz Question\n2. Edit Quiz Question\n3. Delete Quiz Question\n4. Exit");
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1:
                addQuizQuestion(scanner);
                break;
            case 2:
                editQuizQuestion(scanner);
                break;
            case 3:
                deleteQuizQuestion(scanner);
                break;
            case 4:
                System.out.println("Exiting...");
                scanner.close();
                return;
            default:
                System.out.println("Invalid choice. Try again.");
        }
    }
}

private static void createQuizTable() {
    try (Connection conn = DriverManager.getConnection(DB_URL);
         Statement stmt = conn.createStatement()) {
        String sql = "CREATE TABLE IF NOT EXISTS quiz (id INTEGER PRIMARY KEY AUTOINCREMENT, question TEXT, option1 TEXT, option2 TEXT, option3 TEXT, option4 TEXT, correctAnswer TEXT)";
        stmt.execute(sql);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private static void addQuizQuestion(Scanner scanner) {
    System.out.print("Enter question: ");
    String question = scanner.nextLine();
    System.out.print("Enter option 1: ");
    String option1 = scanner.nextLine();
    System.out.print("Enter option 2: ");
    String option2 = scanner.nextLine();
    System.out.print("Enter option 3: ");
    String option3 = scanner.nextLine();
    System.out.print("Enter option 4: ");
    String option4 = scanner.nextLine();
    System.out.print("Enter correct answer: ");
    String correctAnswer = scanner.nextLine();
    
    String sql = "INSERT INTO quiz (question, option1, option2, option3, option4, correctAnswer) VALUES (?, ?, ?, ?, ?, ?)";
    try (Connection conn = DriverManager.getConnection(DB_URL);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, question);
        pstmt.setString(2, option1);
        pstmt.setString(3, option2);
        pstmt.setString(4, option3);
        pstmt.setString(5, option4);
        pstmt.setString(6, correctAnswer);
        pstmt.executeUpdate();
        System.out.println("Question added successfully!");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private static void editQuizQuestion(Scanner scanner) {
    System.out.print("Enter question ID to edit: ");
    int id = scanner.nextInt();
    scanner.nextLine();
    System.out.print("Enter new question: ");
    String question = scanner.nextLine();
    System.out.print("Enter new correct answer: ");
    String correctAnswer = scanner.nextLine();
    
    String sql = "UPDATE quiz SET question = ?, correctAnswer = ? WHERE id = ?";
    try (Connection conn = DriverManager.getConnection(DB_URL);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, question);
        pstmt.setString(2, correctAnswer);
        pstmt.setInt(3, id);
        pstmt.executeUpdate();
        System.out.println("Question updated successfully!");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private static void deleteQuizQuestion(Scanner scanner) {
    System.out.print("Enter question ID to delete: ");
    int id = scanner.nextInt();
    
    String sql = "DELETE FROM quiz WHERE id = ?";
    try (Connection conn = DriverManager.getConnection(DB_URL);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
        System.out.println("Question deleted successfully!");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

}