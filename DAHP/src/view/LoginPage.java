package view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LoginPage {
	boolean isVIP = false;
    public void start(Stage primaryStage) {
    	
        // Create a form layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Create labels and input fields
        Label titleLabel = new Label("Login Page");
        titleLabel.setStyle("-fx-font-size: 20px;");
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        // Create a Login button
        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            // Add your login logic here
            String username = usernameField.getText();
            String password = passwordField.getText();
            // Perform login validation and actions
            // You can implement this logic as needed
            if (new controller.LoginPage().validateLogin(username, password)) {
                // Successful login
                String fullName = new controller.LoginPage().getFullName(username);
                new controller.LoginPage().navigateToDashboard(primaryStage, fullName, username, new model.isVIP().checkVIP(username));
            } else {
                // Failed login
            	new controller.LoginPage().showAlert("Login Error", "Invalid username or password.");
            }
        });

        // Create the "Don't have an account? Sign up" link
        Hyperlink signupLink = new Hyperlink("Don't have an account? Sign up");
        signupLink.setOnAction(e -> {
            // Handle the click event to switch to the registration page
            RegistrationPage registrationPage = new RegistrationPage();
            registrationPage.start(primaryStage);
        });

        // Add components to the grid
        gridPane.add(titleLabel, 0, 0, 2, 1);
        gridPane.add(usernameLabel, 0, 1);
        gridPane.add(usernameField, 1, 1);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordField, 1, 2);
        gridPane.add(loginButton, 1, 3);
        gridPane.add(signupLink, 1, 4);

        // Create and set the scene
        Scene scene = new Scene(gridPane, 400, 250);
        primaryStage.setTitle("Login Page");
        primaryStage.setScene(scene);
    }

//    public static void main(String[] args) {
//        launch(args);
//    }
    
    
}
