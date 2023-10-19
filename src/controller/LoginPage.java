package controller;

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
	
    

//    public static void main(String[] args) {
//        launch(args);
//    }
    
    // Validate login credentials
    public boolean validateLogin(String username, String password) {
        return new model.LoginPage().validateLogin(username, password);
    }
    
    // Retrieve the full name of the user
    public String getFullName(String username) {
        return new model.LoginPage().getFullName(username);
    }
    
    // Helper method to display an alert
    public void showAlert(String title, String message) {
        new model.LoginPage().showAlert(title, message);
    }
    
    // Navigate to the dash board
    public void navigateToDashboard(Stage primaryStage, String fullName,String username, boolean isVIP) {
    	new model.LoginPage().navigateToDashboard(primaryStage, fullName, username, isVIP);
    }
}
