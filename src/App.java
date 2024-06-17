import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


class Book {//created a class that represents the book object
    private int id;
    private String title;//title field represents the title of the book
    private String author;//author field represents the author of the book(both are saved as strings)

    public Book(int id, String title, String author) {//this is a constructor used for initializing the book object and its attributes
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    // Getters and Setters used to access and modify the values of private attributes
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
public class App {
    private static Scanner scanner = new Scanner(System.in);//static variable used on a specific data type
    
    public static void main(String[] args){
        System.out.println("Classpath: " + System.getProperty("java.class.path"));
      databasehelper.createTables();

        while (true) { //this loop keeps looping through all the options offered until the exit option is chosen
             System.out.println("1. Add a book");
             System.out.println("2. View all books");
             System.out.println("3. Update a book");
             System.out.println("4. Delete a book");
             System.out.println("5. Exit");
             System.out.print("Enter your choice: ");
             int choice = scanner.nextInt();
             scanner.nextLine(); // consume newline
        
                switch (choice) {//the switch provides the various options that can be used for the system
                     case 1:
                         addBook();
                         break;
                     case 2:
                        viewBooks();
                         break;
                     case 3:
                         updateBook();
                         break;
                     case 4:
                        deleteBook();
                        break;
                     case 5:
                        System.out.println("Exiting...");//this option makes sure that you can exit the loop
                        System.exit(0);
                        break;
                      default:
                        System.out.println("Invalid choice! Please enter a number between 1 and 5.");
                    }
                }
            }
        
            private static void addBook() {//function for adding the book and its author
                System.out.print("Enter book title: ");
                String title = scanner.nextLine();
                System.out.print("Enter book author: ");
                String author = scanner.nextLine();
        
                Book book = new Book(title, author);

                String sql = "INSERT INTO Books(title, author) VALUES(?, ?)";
                try (Connection conn = databasehelper.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, book.getTitle());
                pstmt.setString(2, book.getAuthor());
                pstmt.executeUpdate();
                System.out.println("Book added successfully!");
                } catch (SQLException e) {
                System.out.println(e.getMessage());
                    }
            }
                
        
            private static void viewBooks() {//function for viewing all the saved books
                String sql = "SELECT id, title, author FROM Books";
                try (Connection conn = databasehelper.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

                if (!rs.isBeforeFirst()) { // Check if result set is empty
                System.out.println("No books available.");
                } else {
                System.out.println("List of books:");
                while (rs.next()) {
                    System.out.println(rs.getInt("id") + ". " + rs.getString("title") + " by " + rs.getString("author"));
                }
                }
                } catch (SQLException e) {
                System.out.println(e.getMessage());
                }
            }


            private static void updateBook() {//function for updating the saved books based on their indexes of how they were saved
                System.out.print("Enter the id of the book to update: ");
                int id = scanner.nextInt();
                scanner.nextLine(); // consume newline
        
                System.out.print("Enter new title: ");
                String newTitle = scanner.nextLine();
                System.out.print("Enter new author: ");
                String newAuthor = scanner.nextLine();
        
                String sql = "UPDATE Books SET title = ?, author = ? WHERE id = ?";
                try (Connection conn = databasehelper.connect();
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, newTitle);
                    pstmt.setString(2, newAuthor);
                    pstmt.setInt(3, id);
                    int rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Book updated successfully!");
                    } else {
                        System.out.println("Book not found.");
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        
            private static void deleteBook() {//function for deleting any of the saved books 
                System.out.print("Enter the id of the book to delete: ");
                int id = scanner.nextInt();
                scanner.nextLine(); // consume newline

                String sql = "DELETE FROM Books WHERE id = ?";
                try (Connection conn = databasehelper.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                System.out.println("Book deleted successfully!");
                } else {
                System.out.println("Book not found.");
                }
                } catch (SQLException e) {
                System.out.println(e.getMessage());
                }
            }
}
            
        




