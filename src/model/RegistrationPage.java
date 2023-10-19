package model;

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

    

   
//    public static void main(String[] args) {
//        launch(args);
//    }
    
    public boolean registerUser(String username, String password, String firstName, String lastName,int type) {
        try {
            Connection connection = DatabaseUtil.getConnection();
            String insertQuery = "INSERT INTO user (username, password, firstname, lastname, isVIP) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, lastName);
            preparedStatement.setInt(5, type);

            int rowsInserted = preparedStatement.executeUpdate();
            preparedStatement.close();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Helper method to display an alert
    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    // New method to navigate to the dash board
    public void navigateToDashboard(String fullName, String username, boolean isVIP) {
        // Create a new stage for the dash board
        Stage dashboardStage = new Stage();
        new view.DashboardPage().start(dashboardStage, fullName, username, isVIP);
        // Close the registration page
        primaryStage.close();
    }
    
    public void isVIP(Stage primaryStage,String username,String password,String firstName,String lastName) {
        

        // Create the subscription prompt window as a dialog
        Stage subscriptionStage = new Stage();
        subscriptionStage.initModality(Modality.APPLICATION_MODAL);
        subscriptionStage.initOwner(primaryStage);
        
        primaryStage.setTitle("Subscription Prompt");

        // Create UI elements for the subscription prompt
        Label promptLabel = new Label("Would you like to subscribe to the application for a monthly fee of $0?");
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        // Handle "Yes" button click
        yesButton.setOnAction(e -> {
            // Set the user as a VIP
            isVIP = true;
            // Close the subscription prompt
            subscriptionStage.close();
        });

        // Handle "No" button click
        noButton.setOnAction(e -> {
            // Close the subscription prompt without making changes
            subscriptionStage.close();
        });
        
        // Create a layout for the subscription prompt
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.getChildren().addAll(promptLabel, yesButton, noButton);

        // Set the layout for the subscription prompt
        Scene subscriptionScene = new Scene(layout, 400, 120);
        subscriptionStage.setScene(subscriptionScene);

        subscriptionStage.showAndWait(); // Show the subscription prompt as a modal dialog

        // At this point, you can check the `isVIP` status to know if the user subscribed.
        if (isVIP) {
        	// Perform registration actions (e.g., validation, database storage)
            if (registerUser(username, password, firstName, lastName,1)) {
                // Display a success message to the user
                System.out.println("Registration successful");
                navigateToDashboard(firstName + " " + lastName, username, true);
            } else {
                // Display an error message to the user
                System.out.println("Registration failed");
                showAlert("Registration Error", "Username already exists, please provide another username.");
            }
            System.out.println("User is now a VIP!");
        } else {
        	// Perform registration actions (e.g., validation, database storage)
            if (registerUser(username, password, firstName, lastName,0)) {
                // Display a success message to the user
                System.out.println("Registration successful");
                navigateToDashboard(firstName + " " + lastName, username, false);
            } else {
                // Display an error message to the user
                System.out.println("Registration failed");
                showAlert("Registration Error", "Username already exists, please provide another username.");
            }
            System.out.println("User chose not to subscribe.");
        }
    }
}
