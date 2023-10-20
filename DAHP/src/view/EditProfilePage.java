package view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EditProfilePage {
	
	public void start(Stage primaryStage, String fullName,String username) {
        BorderPane editProfileLayout = new BorderPane();
        editProfileLayout.setPadding(new Insets(20));

        // Create labels and input fields for editing profile information
        Label firstNameLabel = new Label("First Name:");
        TextField firstNameField = new TextField();

        Label lastNameLabel = new Label("Last Name:");
        TextField lastNameField = new TextField();

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        // Create a "Save" button to update the profile
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            // Get the updated profile information from the input fields
            String newFirstName = firstNameField.getText();
            String newLastName = lastNameField.getText();
            String newUsername = usernameField.getText();
            String newPassword = passwordField.getText();

            // Update the user's profile in the database (implement this logic)
            boolean updated = new controller.EditProfilePage().updateUserProfile(newFirstName, newLastName, newUsername, newPassword, username);
            if (updated) {
                new controller.EditProfilePage().showAlert("Profile Updated", "Your profile has been updated successfully.");
            } else {
            	new controller.EditProfilePage().showAlert("Error", "Failed to update your profile. UserName is Already Taken.");
            }
        });

        // Create a link to navigate back to the dash board
        Button backToDashboardButton = new Button("Back to Dashboard");
        backToDashboardButton.setOnAction(e -> {
        	
        	String userFullName = new controller.EditProfilePage().getFullName(username);
            DashboardPage dashboardPage = new DashboardPage();
            dashboardPage.start(primaryStage, userFullName, username, new controller.EditProfilePage().isVIP(username));
        });

        // Create a VBox to organize the profile editing components
        VBox editProfileBox = new VBox(
                firstNameLabel, firstNameField,
                lastNameLabel, lastNameField,
                usernameLabel, usernameField,
                passwordLabel, passwordField,
                saveButton,
                backToDashboardButton
        );
        editProfileBox.setSpacing(10);
        editProfileBox.setAlignment(Pos.CENTER);

        // Set the profile editing components in the center of the layout
        editProfileLayout.setCenter(editProfileBox);

        Scene editProfileScene = new Scene(editProfileLayout, 400, 400);
        primaryStage.setTitle("Edit Profile");
        primaryStage.setScene(editProfileScene);
        primaryStage.show();
    }
	
    
    

}
