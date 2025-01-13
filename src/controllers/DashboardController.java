package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import javafx.fxml.FXML;

import java.io.IOException;

public class DashboardController {

    @FXML
    private Button manageDashboardMenuButton;

    @FXML
    private Button manageCustomersMenuButton;

    @FXML
    private Button manageSubscriptionsMenuButton;

    @FXML
    private MenuItem handleLogout;

    @FXML
    private StackPane contentPane;

    private String role;

    @FXML
    public void initialize() {
        // Load the initial Dashboard page
        manageDashboardMenu();
    }

    @FXML
    public void manageDashboardMenu() {
        System.out.println("Dashboard menu clicked");
        loadPage("/views/dashboard_content.fxml", contentPane);
    }

    @FXML
    public void manageCustomersMenu() {
        System.out.println("Menu Customer clicked");
        loadPage("/views/customers.fxml", contentPane);
    }

    @FXML
    public void manageSubscriptionsMenu() {
        System.out.println("Menu Subscription clicked");
        loadPage("/views/subscriptions.fxml", contentPane);
    }

    @FXML
    public void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
            AnchorPane loginPane = loader.load();

            if (contentPane != null && contentPane.getScene() != null) {
                contentPane.getScene().setRoot(loginPane);
            } else {
                System.err.println("Unable to set root. ContentPane or Scene is null.");
            }
        } catch (IOException e) {
            System.err.println("Failed to load login page.");
            e.printStackTrace();
        }
    }

    private void loadPage(String fxmlPath, StackPane contentPane) {
        try {
            System.out.println("Loading page: " + fxmlPath);
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            AnchorPane newContent = loader.load();
            System.out.println("FXML loaded successfully: " + fxmlPath);

            if (contentPane != null) {
                contentPane.getChildren().setAll(newContent);
                System.out.println("ContentPane updated successfully.");
            } else {
                System.err.println("ContentPane is null. Cannot load new content.");
            }
        } catch (IOException e) {
            System.err.println("Failed to load FXML: " + fxmlPath);
            e.printStackTrace();
        }
    }

    public void setRole(String role) {
        this.role = role;
        adjustMenuBasedOnRole();
    }

    private void adjustMenuBasedOnRole() {
        boolean isAdmin = "admin".equals(role);
        boolean isCustomer = "customer".equals(role);

        manageCustomersMenuButton.setVisible(isAdmin);
        manageSubscriptionsMenuButton.setVisible(isAdmin);
        handleLogout.setVisible(isAdmin || isCustomer);
    }
}
