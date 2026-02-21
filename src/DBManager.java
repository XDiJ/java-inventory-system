import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private static Connection connection;

    public static Connection connect() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String dbPath = "jdbc:mysql://127.0.0.1:3306/WeaponShopDB";
                connection = DriverManager.getConnection(dbPath, "USER", "PASSWORD");
                System.out.println("Connected to MySQL database successfully!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void disconnect() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Disconnected from DB.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
