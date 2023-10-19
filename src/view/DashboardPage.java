package view;

import java.io.BufferedReader;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

public class DashboardPage {
    public void start(Stage primaryStage, String fullName, String username, boolean isVIP) {
        BorderPane dashboardLayout = new BorderPane();
        dashboardLayout.setPadding(new Insets(20));

        // Create a welcome label with the user's full name
        Label welcomeLabel = new Label("Welcome, " + fullName + "!");
        welcomeLabel.setStyle("-fx-font-size: 20px;");
        
        // Create a link to navigate to the "Edit Profile" page
        Label editProfileLabel = new Label("Edit Profile");
        editProfileLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: blue; -fx-cursor: hand;"); // Style the label

        // Add a click event handler to the "Edit Profile" label
        editProfileLabel.setOnMouseClicked((MouseEvent event) -> {
            EditProfilePage editProfilePage = new EditProfilePage();
            editProfilePage.start(primaryStage, fullName, username);
        });
        
        // Create a logout label
        Label logoutLabel = new Label("Logout");
        logoutLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: blue; -fx-cursor: hand;"); // Style the label
        
        // Add a click event handler to the logout label
        logoutLabel.setOnMouseClicked((MouseEvent event) -> {
            // Handle logout here, e.g., return to the login screen
            // You can close the current dash board window and open the login window
           
            new LoginPage().start(primaryStage);

        });

        // Create labels for the menu options
        Label menuLabel = new Label("Select from the menu:");
        Label option1Label = new Label("1) Add a social media post");
        Label option2Label = new Label("2) Delete an existing social media post");
        Label option3Label = new Label("3) Retrieve a social media post");
        Label option4Label = new Label("4) Retrieve the top N posts with most likes");
        Label option5Label = new Label("5) Retrieve the top N posts with most shares");
        Label option6Label = new Label("6) Export a post to a file based on post ID");
        
        HBox headerBox;
        VBox menuBox;
        
        if(isVIP) {
        	Label option7Label = new Label("7) Show Shares Distribution Pie Chart");
            Label option8Label = new Label("8) Bulk Import Posts");

            // Create a label for user input
            Label inputLabel = new Label("Enter your choice (1-8):");

            // Create a TextField for user choice
            TextField userInput = new TextField();
            userInput.setPromptText("Enter a number...");

            // Create a button to perform the selected action
            Button selectButton = new Button("Select");
            selectButton.setOnAction(e -> {
                String input = userInput.getText();
                int selectedOption = -1; // Initialize with an invalid value

                try {
                    selectedOption = Integer.parseInt(input);
                } catch (NumberFormatException ex) {
                    new controller.DashboardPage().showAlert("Invalid Input", "Please enter a valid number.");
                }

                if (selectedOption >= 1 && selectedOption <= 8) {
                    // Implement logic for the selected option here
                    new controller.DashboardPage().handleSelectedOption(selectedOption, primaryStage);
                } else {
                    new controller.DashboardPage().showAlert("Invalid Option", "Please select a valid option (1-8).");
                }
            });
            
            
            
            // Create a HBox for the header containing welcomeLabel and logoutLabel
            headerBox = new HBox(20, welcomeLabel, editProfileLabel, logoutLabel);
            headerBox.setAlignment(Pos.CENTER_RIGHT); // Align logoutLabel to the right

            // Create a VBox to organize the menu components
            menuBox = new VBox(menuLabel, option1Label, option2Label, option3Label, option4Label, option5Label, option6Label, option7Label, option8Label, inputLabel, userInput, selectButton);
            menuBox.setSpacing(10);
        }else {
            // Create a label for user input
            Label inputLabel = new Label("Enter your choice (1-6):");

            // Create a TextField for user choice
            TextField userInput = new TextField();
            userInput.setPromptText("Enter a number...");

            // Create a button to perform the selected action
            Button selectButton = new Button("Select");
            selectButton.setOnAction(e -> {
                String input = userInput.getText();
                int selectedOption = -1; // Initialize with an invalid value

                try {
                    selectedOption = Integer.parseInt(input);
                } catch (NumberFormatException ex) {
                    new controller.DashboardPage().showAlert("Invalid Input", "Please enter a valid number.");
                }

                if (selectedOption >= 1 && selectedOption <= 6) {
                    // Implement logic for the selected option here
                    new controller.DashboardPage().handleSelectedOption(selectedOption, primaryStage);
                } else {
                    new controller.DashboardPage().showAlert("Invalid Option", "Please select a valid option (1-6).");
                }
            });
            
            
            
            // Create a HBox for the header containing welcomeLabel and logoutLabel
            headerBox = new HBox(20, welcomeLabel, editProfileLabel, logoutLabel);
            headerBox.setAlignment(Pos.CENTER_RIGHT); // Align logoutLabel to the right

            // Create a VBox to organize the menu components
            menuBox = new VBox(menuLabel, option1Label, option2Label, option3Label, option4Label, option5Label, option6Label, inputLabel, userInput, selectButton);
            menuBox.setSpacing(10);
        }

        // Arrange the components in the layout
        dashboardLayout.setTop(headerBox); // Set the headerBox as the top part of the layout
        dashboardLayout.setCenter(menuBox);

        Scene dashboardScene = new Scene(dashboardLayout, 500, 420);
        primaryStage.setTitle("Dashboard");
        primaryStage.setScene(dashboardScene);
        primaryStage.show();
    }
    
    


}
