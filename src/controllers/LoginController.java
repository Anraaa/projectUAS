package controllers;

import database.DBHelper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.controlsfx.control.textfield.CustomTextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.App;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {
    @FXML
    private CustomTextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Label errorLabel;

    @FXML
    private void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Username or password cannot be empty.");
            return;
        }

        try (Connection conn = DBHelper.getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                App.showDashboard(role);
            } else {
                errorLabel.setText("Invalid username or password.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEnterKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            login();
        }
    }
}