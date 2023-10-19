package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dbConnection.DatabaseUtil;
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
	
    // Implement this method to update the user's profile in the database
    public boolean updateUserProfile(String newFirstName, String newLastName, String newUsername, String newPassword, String oldUserName) {
        try {
            Connection connection = DatabaseUtil.getConnection();

            // Create the base SQL UPDATE statement
            StringBuilder updateQuery = new StringBuilder("UPDATE user SET");

            // Create a list of parameters to set in the prepared statement
            java.util.List<Object> parameters = new java.util.ArrayList<>();

            if (!newFirstName.isEmpty()) {
                updateQuery.append(" firstname = ?,");
                parameters.add(newFirstName);
            }

            if (!newLastName.isEmpty()) {
                updateQuery.append(" lastname = ?,");
                parameters.add(newLastName);
            }

            if (!newUsername.isEmpty()) {
                updateQuery.append(" username = ?,");
                parameters.add(newUsername);
            }

            if (!newPassword.isEmpty()) {
                updateQuery.append(" password = ?,");
                parameters.add(newPassword);
            }

            // Remove the trailing comma and add the WHERE clause
            updateQuery.setLength(updateQuery.length() - 1); // Remove trailing comma
            updateQuery.append(" WHERE username = ?");
            parameters.add(oldUserName);

            // Prepare the SQL statement
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery.toString());

            // Set parameters in the prepared statement
            int parameterIndex = 1;
            for (Object parameter : parameters) {
                preparedStatement.setObject(parameterIndex++, parameter);
            }

            // Execute the update
            int rowsUpdated = preparedStatement.executeUpdate();
            preparedStatement.close();
            
            
            return rowsUpdated > 0;
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public boolean isVIP(String username) {
    	
    	try {
    		Connection connection = DatabaseUtil.getConnection();
        	Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT isVIP FROM user WHERE username='"+username+"'");
            resultSet.next();
            if(resultSet.getInt("isVIP")==1) {
            	return true;
            }else {
            	return false;
            }
		} catch (Exception e) {
			showAlert("Unexpected Error", e.getMessage());
			return false;
		}
    }

}
