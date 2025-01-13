package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHelper {

    private static final String URL = "jdbc:mysql://localhost:31230/";
    private static final String USER = "root";
    private static final String PASSWORD = "123";
    private static final String DATABASE_NAME = "wifi_subscription";

    // Get a connection to the database
    public static Connection getConnection() throws SQLException {
        try {
            // Load the MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            
            // Create the database if it doesn't exist
            createDatabaseIfNeeded(connection);
            
            // Return a connection to the specific database
            return DriverManager.getConnection(URL + DATABASE_NAME, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("MySQL JDBC Driver not found.");
        }
    }

    // Create the database and tables if needed
    private static void createDatabaseIfNeeded(Connection connection) {
    try (Statement stmt = connection.createStatement()) {
        // Create the database if it doesn't exist
        String createDBQuery = "CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME;
        stmt.executeUpdate(createDBQuery);

        // Switch to the new database
        stmt.executeUpdate("USE " + DATABASE_NAME);

        // Create the users table if it doesn't exist
        String createUsersTableQuery = "CREATE TABLE IF NOT EXISTS users ("
                + "user_id INT AUTO_INCREMENT PRIMARY KEY, "
                + "username VARCHAR(50) UNIQUE NOT NULL, "
                + "password VARCHAR(255) NOT NULL, "
                + "role ENUM('admin', 'staff', 'customer') NOT NULL)";
        stmt.executeUpdate(createUsersTableQuery);

        // Create the customers table if it doesn't exist
        String createCustomersTableQuery = "CREATE TABLE IF NOT EXISTS customers ("
                + "customer_id INT AUTO_INCREMENT PRIMARY KEY, "
                + "name VARCHAR(100) NOT NULL, "
                + "email VARCHAR(100) UNIQUE NOT NULL, "
                + "phone VARCHAR(20) NOT NULL, "
                + "address TEXT)";
        stmt.executeUpdate(createCustomersTableQuery);

        // Create the subscriptions table if it doesn't exist
        String createSubscriptionsTableQuery = "CREATE TABLE IF NOT EXISTS subscriptions ("
                + "subscription_id INT AUTO_INCREMENT PRIMARY KEY, "
                + "customer_id INT NOT NULL, "
                + "plan_name VARCHAR(100) NOT NULL, "
                + "price DECIMAL(10, 2) NOT NULL, "
                + "start_date DATE NOT NULL, "
                + "end_date DATE NOT NULL, "
                + "FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE ON UPDATE CASCADE)";
        stmt.executeUpdate(createSubscriptionsTableQuery);

        // Insert default users for testing (admin and customer)
        String insertUsersQuery = "INSERT INTO users (username, password, role) VALUES "
                + "('admin', 'admin123', 'admin'), "
                + "('cust', 'cust123', 'customer') "
                + "ON DUPLICATE KEY UPDATE password=VALUES(password), role=VALUES(role)";
        stmt.executeUpdate(insertUsersQuery);

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

}