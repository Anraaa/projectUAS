package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Dashboard;
import database.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DashboardContentController {

    @FXML
    private TableView<Dashboard> dashboardTable;
    @FXML
    private TableColumn<Dashboard, String> colName;
    @FXML
    private TableColumn<Dashboard, String> colPlanName;
    @FXML
    private TableColumn<Dashboard, Double> colPrice;
    @FXML
    private TableColumn<Dashboard, String> colStartDate;
    @FXML
    private TableColumn<Dashboard, String> colEndDate;

    private ObservableList<Dashboard> dashboardList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up table columns based on Dashboard model properties
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPlanName.setCellValueFactory(new PropertyValueFactory<>("planName"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        // Load data from the database into the table
        loadDashboardData();
    }

    private void loadDashboardData() {
    // Clear the data list before reloading
    if (dashboardList != null) {
        dashboardList.clear();
    }
    
    try (Connection conn = DBHelper.getConnection()) {
        String sql = """
                     SELECT c.name, 
                            MAX(s.plan_name) AS plan_name, 
                            MAX(s.price) AS price, 
                            MAX(s.start_date) AS start_date, 
                            MAX(s.end_date) AS end_date
                     FROM customers c
                     LEFT JOIN subscriptions s ON c.customer_id = s.customer_id
                     GROUP BY c.customer_id;
                     """;
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            dashboardList.add(new Dashboard(
                    rs.getString("name"),
                    rs.getString("plan_name") != null ? rs.getString("plan_name") : "N/A",
                    rs.getDouble("price"),
                    rs.getString("start_date") != null ? rs.getString("start_date") : "N/A",
                    rs.getString("end_date") != null ? rs.getString("end_date") : "N/A"
            ));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    
    // Reset and set data into the table
    dashboardTable.setItems(null); // Reset TableView items if needed
    dashboardTable.setItems(dashboardList); // Assign the updated list
}

}