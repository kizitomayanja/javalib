import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.lang.Class;


public class databasehelper {
    private static final String URL = "jdbc:sqlite:./SQLitedatabaselib/books.db"; // Path to your SQLite database

  /*  static {
        try {
            // Load the SQLite JDBC driver (you must have the SQLite JAR file in your classpath)
            Class.forName("sqlite-jdbc-3.46.0.0.jar");
            System.out.println("SQLite JDBC driver registered.");
        } catch (Exception e) {
            System.err.println("SQLite JDBC driver not found. Make sure the SQLite JDBC library is included in the classpath.");
            e.printStackTrace();
        }
    }*/

    public static Connection connect() {
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void createTables() {
        String createBooksTable = "CREATE TABLE IF NOT EXISTS Books ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "title TEXT NOT NULL,"
                + "author TEXT NOT NULL"
                + ");";

                try (Connection conn = connect();
                Statement stmt = conn != null ? conn.createStatement() : null) {
               if (stmt != null) {
                   stmt.execute(createBooksTable);
                   System.out.println("Tables created successfully.");
               } else {
                   System.out.println("Statement creation failed. Connection is null.");
               }
                 } catch (SQLException e) {
               System.out.println("SQL error: " + e.getMessage());
                }
    }
}

