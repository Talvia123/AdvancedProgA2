package model;

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

import dbConnection.DatabaseUtil;
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
    
    
    public void handleSelectedOption(int option, Stage primaryStage) {
        // Implement logic for the selected option here
        switch (option) {
            case 1:
            	showAddPostDialog(primaryStage);
                break;
            case 2:
            	showDeletePostDialog(primaryStage);
                break;
            case 3:
            	showRetrievePostDialog(primaryStage);
                break;
            case 4:
            	showRetrieveTopLikesDialog(primaryStage);
                break;
            case 5:
            	showRetrieveTopSharesDialog(primaryStage);
                break;
            case 6:
                exportPostToCSV(primaryStage);
                break;
            case 7:
            	showSharesDistributionPieChart(primaryStage);
                break;
            case 8:
            	bulkImportPosts(primaryStage);
                break;
            default:
                showAlert("Invalid Option", "Please select a valid option (1-8).");
                break;
        }
    }
    
    // Helper method to display an alert
    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public void showAddPostDialog(Stage primaryStage) {
        // Create a new dialog for adding a post
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Add Social Media Post");
        dialog.setHeaderText("Enter post details:");

        // Set the button types (OK and Cancel)
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create and configure the content for the dialog
        VBox content = new VBox();
        content.setSpacing(10);
        TextField contentField = new TextField();
        contentField.setPromptText("Content");
        TextField authorField = new TextField();
        authorField.setPromptText("Author");
        TextField likesField = new TextField();
        likesField.setPromptText("Likes");
        TextField sharesField = new TextField();
        sharesField.setPromptText("Shares");
        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Date (DD/MM/YYYY)");
        TextField timeField = new TextField();
        timeField.setPromptText("Time (HH:MM)");

        content.getChildren().addAll(contentField, authorField, likesField, sharesField, datePicker, timeField);
        dialog.getDialogPane().setContent(content);

        // Convert the result to a String when the Save button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                // Validate and save the post to the database
                if (validateAndSavePost(contentField.getText(), authorField.getText(), likesField.getText(), sharesField.getText(), datePicker.getValue(), timeField.getText())) {
                    showAlert("Success", "Post saved successfully.");
                } else {
                    showAlert("Error", "Failed to save the post.");
                }
            }
            return null;
        });

        // Show the dialog
        dialog.initOwner(primaryStage);
        dialog.showAndWait();
    }
    
    public boolean validateAndSavePost(String content, String author, String likes, String shares, LocalDate date, String time) {
        try {
            Connection connection = DatabaseUtil.getConnection();
            String insertQuery = "INSERT INTO post (content, author, likes, shares, dateTime) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, content);
            preparedStatement.setString(2, author);
            preparedStatement.setInt(3, Integer.parseInt(likes));
            preparedStatement.setInt(4, Integer.parseInt(shares));

            // Combine date and time into a single date time string
            String dateTimeStr = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " " + time;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            java.util.Date parsedDate = sdf.parse(dateTimeStr);

            // Convert java.util.Date to java.sql.Timestamp
            java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());

            preparedStatement.setTimestamp(5, timestamp);

            int rowsInserted = preparedStatement.executeUpdate();
            preparedStatement.close();
            return rowsInserted > 0;
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void showDeletePostDialog(Stage primaryStage) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Social Media Post");
        dialog.setHeaderText("Enter post ID to delete:");
        dialog.setContentText("Post ID:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(postId -> {
            if (deletePost(Integer.parseInt(postId))) {
                showAlert("Success", "Post with ID " + postId + " deleted successfully.");
            } else {
                showAlert("Error", "Failed to delete the post with ID " + postId + ".");
            }
        });
    }
    
    public boolean deletePost(int postId) {
        try {
            Connection connection = DatabaseUtil.getConnection();
            String deleteQuery = "DELETE FROM post WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, postId);

            int rowsDeleted = preparedStatement.executeUpdate();
            preparedStatement.close();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void showRetrievePostDialog(Stage primaryStage) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Retrieve Social Media Post");
        dialog.setHeaderText("Enter post ID to retrieve:");
        dialog.setContentText("Post ID:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(postId -> {
            int postIdInt = Integer.parseInt(postId);
            String postInfo = retrievePostInfo(postIdInt);
            if (postInfo != null) {
                showAlert("Post Information", postInfo);
            } else {
                showAlert("Error", "The post with this Id " + postId + " is not present.");
            }
        });
    }
    
    public String retrievePostInfo(int postId) {
        try {
            Connection connection = DatabaseUtil.getConnection();
            String selectQuery = "SELECT * FROM post WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, postId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String content = resultSet.getString("content");
                String author = resultSet.getString("author");
                int likes = resultSet.getInt("likes");
                int shares = resultSet.getInt("shares");
                java.sql.Timestamp timestamp = resultSet.getTimestamp("dateTime");

                // Format the time stamp as DD/MM/YYYY HH:MM
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String formattedTimestamp = sdf.format(timestamp);

                return "Post ID: " + id + "\nContent: " + content + "\nAuthor: " + author + "\nLikes: " + likes + "\nShares: " + shares + "\nDate and Time: " + formattedTimestamp;
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    
    public void showRetrieveTopLikesDialog(Stage primaryStage) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Retrieve Top Liked Posts");
        dialog.setHeaderText("\"Please specify the number of posts to retrieve (N):");
        dialog.setContentText("N:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(likesCount -> {
            try {
                int n = Integer.parseInt(likesCount);
                if (n > 0) {
                    String topLikedPosts = retrieveTopLikedPosts(n);
                    if (topLikedPosts != null) {
                        showAlert("Top Liked Posts", topLikedPosts);
                    } else {
                        showAlert("Error", "No posts found or an error occurred.");
                    }
                } else {
                    showAlert("Invalid Input", "Please enter a positive number (N > 0).");
                }
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Please enter a valid number.");
            }
        });
    }
    
    public String retrieveTopLikedPosts(int n) {
        try {
            Connection connection = DatabaseUtil.getConnection();
            String selectQuery = "SELECT * FROM post ORDER BY likes DESC LIMIT ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, n);

            ResultSet resultSet = preparedStatement.executeQuery();
            StringBuilder resultBuilder = new StringBuilder();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String content = resultSet.getString("content");
                String author = resultSet.getString("author");
                int likes = resultSet.getInt("likes");
                int shares = resultSet.getInt("shares");
                java.sql.Timestamp timestamp = resultSet.getTimestamp("dateTime");

                // Format the time stamp as DD/MM/YYYY HH:MM
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String formattedTimestamp = sdf.format(timestamp);

                resultBuilder.append("Post ID: ").append(id).append("\nContent: ").append(content).append("\nAuthor: ").append(author).append("\nLikes: ").append(likes).append("\nShares: ").append(shares).append("\nDate and Time: ").append(formattedTimestamp).append("\n\n");
            }

            resultSet.close();
            preparedStatement.close();

            return resultBuilder.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    
    public void showRetrieveTopSharesDialog(Stage primaryStage) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Retrieve Top Shared Posts");
        dialog.setHeaderText("Please specify the number of posts to retrieve (N):");
        dialog.setContentText("N:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(sharesCount -> {
            try {
                int n = Integer.parseInt(sharesCount);
                if (n > 0) {
                    String topSharedPosts = retrieveTopSharedPosts(n);
                    if (topSharedPosts != null) {
                        showAlert("Top Shared Posts", topSharedPosts);
                    } else {
                        showAlert("Error", "No posts found or an error occurred.");
                    }
                } else {
                    showAlert("Invalid Input", "Please enter a positive number (N > 0).");
                }
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Please enter a valid number.");
            }
        });
    }

    public String retrieveTopSharedPosts(int n) {
        try {
            Connection connection = DatabaseUtil.getConnection();
            String selectQuery = "SELECT * FROM post ORDER BY shares DESC LIMIT ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, n);

            ResultSet resultSet = preparedStatement.executeQuery();
            StringBuilder resultBuilder = new StringBuilder();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String content = resultSet.getString("content");
                String author = resultSet.getString("author");
                int likes = resultSet.getInt("likes");
                int shares = resultSet.getInt("shares");
                java.sql.Timestamp timestamp = resultSet.getTimestamp("dateTime");

                // Format the time stamp as DD/MM/YYYY HH:MM
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String formattedTimestamp = sdf.format(timestamp);

                resultBuilder.append("Post ID: ").append(id).append("\nContent: ").append(content).append("\nAuthor: ").append(author).append("\nLikes: ").append(likes).append("\nShares: ").append(shares).append("\nDate and Time: ").append(formattedTimestamp).append("\n\n");
            }

            resultSet.close();
            preparedStatement.close();

            return resultBuilder.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public void exportPostToCSV(Stage primaryStage) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Export Post to CSV");
        dialog.setHeaderText("Enter post ID to export:");
        dialog.setContentText("Post ID:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(postId -> {
            int postIdInt = Integer.parseInt(postId);
            String postInfo = retrievePostInfo(postIdInt);

            if (postInfo != null) {
                // Ask the user to choose a folder for saving the CSV file
                DirectoryChooser directoryChooser = new DirectoryChooser();
                directoryChooser.setTitle("Choose a Folder for Saving the CSV File");
                File selectedDirectory = directoryChooser.showDialog(primaryStage);

                if (selectedDirectory != null) {
                    // Ask the user for the file name
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Save as CSV");
                    fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV Files", "*.csv"));
                    fileChooser.setInitialFileName("post_export.csv");

                    File selectedFile = fileChooser.showSaveDialog(primaryStage);

                    if (selectedFile != null) {
                        // Save post information to the selected CSV file
                        if (savePostToCSV(postInfo, selectedFile)) {
                            showAlert("Success", "Post exported to CSV file successfully.");
                        } else {
                            showAlert("Error", "Failed to export the post to CSV file.");
                        }
                    }
                }
            } else {
                showAlert("Error", "The post with this Id " + postId + " is not present.");
            }
        });
    }

    public boolean savePostToCSV(String postInfo, File file) {
        try (FileWriter writer = new FileWriter(file)) {
            // Write the post information to the CSV file
            writer.write(postInfo);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void bulkImportPosts(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV Files", "*.csv"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {
            try {
                // Database connection setup (Replace with your own database details)
                Connection connection = DatabaseUtil.getConnection();

                // Read and process the CSV file
                try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                    reader.readLine(); // Skip header

                    String line;
                    String insertQuery = "INSERT INTO post (id, content, author, likes, shares, dateTime) VALUES (?, ?, ?, ?, ?, ?)";
                    String updateQuery = "UPDATE post SET content = ?, author = ?, likes = ?, shares = ?, dateTime = ? WHERE id = ?";
                    
                    try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                         PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                        
                        while ((line = reader.readLine()) != null) {
                            // Split the line into columns (adjust as per your CSV format)
                            String[] columns = line.split(",");

                            if (columns.length >= 6) {
                                int id = parseId(columns[0].trim());
                                if (id == -1) {
                                    // Invalid ID, skip this record
                                    continue;
                                }
                                String content = columns[1].trim();
                                String author = columns[2].trim();
                                int likes = Integer.parseInt(columns[3].trim());
                                int shares = Integer.parseInt(columns[4].trim());

                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                java.util.Date date = dateFormat.parse(columns[5]);
                                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                                // Check if the record with the given ID exists in the database
                                boolean recordExists = checkIfRecordExists(id, connection);

                                // Use INSERT or UPDATE based on whether the record exists
                                if (recordExists) {
                                    // Update the record
                                    updateStatement.setString(1, content);
                                    updateStatement.setString(2, author);
                                    updateStatement.setInt(3, likes);
                                    updateStatement.setInt(4, shares);
                                    updateStatement.setDate(5, java.sql.Date.valueOf(localDate));
                                    updateStatement.setInt(6, id);
                                    updateStatement.executeUpdate();
                                } else {
                                    // Insert a new record
                                    insertStatement.setInt(1, id);
                                    insertStatement.setString(2, content);
                                    insertStatement.setString(3, author);
                                    insertStatement.setInt(4, likes);
                                    insertStatement.setInt(5, shares);
                                    insertStatement.setDate(6, java.sql.Date.valueOf(localDate));
                                    insertStatement.executeUpdate();
                                }
                            }
                        }
                    }
                }

                
                showAlert("Success", "Posts imported successfully.");
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error", "Failed to import posts.");
            }
        }
    }

    private boolean checkIfRecordExists(int id, Connection connection) throws Exception {
        String query = "SELECT id FROM post WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private int parseId(String idString) {
        try {
            return Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            return -1; // Return -1 for invalid IDs
        }
    }
    
    public void showSharesDistributionPieChart(Stage primaryStage) {
        // Create a PieChart with sample data
        PieChart pieChart = new PieChart();

        // Replace this with your SQL query to retrieve shares data from your database
        String selectSharesQuery = "SELECT shares FROM post";
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        try {
            Connection connection = DatabaseUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectSharesQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            int categoryA = 0;
            int categoryB = 0;
            int categoryC = 0;

            while (resultSet.next()) {
                int shares = resultSet.getInt("shares");

                if (shares >= 0 && shares <= 99) {
                    categoryA++;
                } else if (shares >= 100 && shares <= 999) {
                    categoryB++;
                } else if (shares >= 1000) {
                    categoryC++;
                }
            }

            pieChartData.add(new PieChart.Data("0-99", categoryA));
            pieChartData.add(new PieChart.Data("100-999", categoryB));
            pieChartData.add(new PieChart.Data("1000+", categoryC));

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        pieChart.setData(pieChartData);
        pieChart.setTitle("Shares Distribution");

        // Create a new scene for displaying the pie chart
        Scene chartScene = new Scene(pieChart, 600, 400);

        // Set up a new stage to display the pie chart
        Stage chartStage = new Stage();
        chartStage.setTitle("Shares Distribution Chart");
        chartStage.setScene(chartScene);

        // Show the chart in a new window
        chartStage.initOwner(primaryStage);
        chartStage.show();
    }


}
