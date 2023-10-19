package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dbConnection.DatabaseUtil;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RegistrationPage {
	public boolean isVIP = false;
	public Stage primaryStage; // Store the primaryStage

    public RegistrationPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public RegistrationPage() {
		// TODO Auto-generated constructor stub
	}

   
//    public static void main(String[] args) {
//        launch(args);
//    }
    
    public boolean registerUser(String username, String password, String firstName, String lastName,int type) {
        return new model.RegistrationPage(primaryStage).registerUser(username, password, firstName, lastName, type);
    }
    
    // Helper method to display an alert
    public void showAlert(String title, String message) {
        new model.RegistrationPage(primaryStage).showAlert(title, message);
    }
    
    // New method to navigate to the dash board
    public void navigateToDashboard(String fullName, String username, boolean isVIP) {
        new model.RegistrationPage(primaryStage).navigateToDashboard(fullName, username, isVIP);
    }
    
    public void isVIP(Stage primaryStage,String username,String password,String firstName,String lastName) {
        

        new model.RegistrationPage(primaryStage).isVIP(primaryStage, username, password, firstName, lastName);
    }
}
