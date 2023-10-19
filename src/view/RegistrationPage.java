package view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RegistrationPage {
	private boolean isVIP = false;
	private Stage primaryStage; // Store the primaryStage

    public RegistrationPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public RegistrationPage() {
		// TODO Auto-generated constructor stub
	}

    public void start(Stage primaryStage) {
        // Create a form layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Create labels and input fields
        Label titleLabel = new Label("Registration Page");
        titleLabel.setStyle("-fx-font-size: 20px;");
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Label firstNameLabel = new Label("First Name:");
        TextField firstNameField = new TextField();
        Label lastNameLabel = new Label("Last Name:");
        TextField lastNameField = new TextField();

        // Create a Register button
        Button registerButton = new Button("Register");
        registerButton.setOnAction(e -> {
            // Add your registration logic here
            String username = usernameField.getText();
            String password = passwordField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            // Perform registration actions (e.g., validation, database storage)
            // Display success or error messages to the user
            // You can implement this logic as needed
            
            if (username.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
                // Display an error message if any field is empty
                new controller.RegistrationPage(primaryStage).showAlert("Registration Error", "All fields are required.");
            } else {
            	new controller.RegistrationPage(primaryStage).isVIP(primaryStage,username, password, firstName, lastName);
            }
        });
        
        // Create the "Already have an account? Log in" link
        Hyperlink loginLink = new Hyperlink("Already have an account? Log in");
        loginLink.setOnAction(e -> {
            // Handle the click event to switch to the login page
            LoginPage loginPage = new LoginPage();
            loginPage.start(primaryStage);
        });

        // Add components to the grid
        gridPane.add(titleLabel, 0, 0, 2, 1);
        gridPane.add(usernameLabel, 0, 1);
        gridPane.add(usernameField, 1, 1);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordField, 1, 2);
        gridPane.add(firstNameLabel, 0, 3);
        gridPane.add(firstNameField, 1, 3);
        gridPane.add(lastNameLabel, 0, 4);
        gridPane.add(lastNameField, 1, 4);
        gridPane.add(registerButton, 1, 5);	
        gridPane.add(loginLink, 1, 6);

        // Create and set the scene
        Scene scene = new Scene(gridPane, 400, 300);
        primaryStage.setTitle("Registration Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

//    public static void main(String[] args) {
//        launch(args);
//    }
    
    
}
