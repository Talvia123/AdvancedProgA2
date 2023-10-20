package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbConnection.DatabaseUtil;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LoginPage {
	boolean isVIP = false;
    

//    public static void main(String[] args) {
//        launch(args);
//    }
    
    // Validate login credentials
    public boolean validateLogin(String username, String password) {
        try {
            Connection connection = DatabaseUtil.getConnection();
            String query = "SELECT * FROM user WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            boolean isValid = resultSet.next();
            if(resultSet.getInt("isVIP")==1) {
            	isVIP=true;
            }else {
            	isVIP=false;
            }
            resultSet.close();
            preparedStatement.close();
            return isValid;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Retrieve the full name of the user
    public String getFullName(String username) {
        try {
            Connection connection = DatabaseUtil.getConnection();
            String query = "SELECT firstname, lastname FROM user WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                return firstName + " " + lastName;
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    // Helper method to display an alert
    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    // Navigate to the dash board
    public void navigateToDashboard(Stage primaryStage, String fullName,String username, boolean isVIP) {
    	// Create a new stage for the dash board
        Stage dashboardStage = new Stage();
        
        new view.DashboardPage().start(dashboardStage, fullName, username, isVIP);

        // Close the login page
        primaryStage.close();
    }
}
