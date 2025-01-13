package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

import controllers.DashboardController; // Add this import statement
import database.DBHelper; // Import DBHelper

public class App extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        App.primaryStage = primaryStage;
        primaryStage.setTitle("WiFi Subscription Management");

        // Initialize database connection
        try {
            Connection connection = DBHelper.getConnection();
            System.out.println("Database connection successfull.");
            // You can store the connection in a static variable if needed
            // DBHelper.setConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to establish database connection.");
        }

        showLoginPage();
    }

    public static void showLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/views/login.fxml"));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("/styles/login_style.css")).toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showDashboard(String role) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/views/dashboard.fxml"));
            AnchorPane root = loader.load();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("/styles/dashboard_style.css")).toExternalForm());
            primaryStage.setScene(scene);

            // Set role-specific visibility
            DashboardController controller = loader.getController();
            controller.setRole(role);

            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}