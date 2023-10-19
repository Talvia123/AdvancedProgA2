package controller;

import java.io.File;
import java.time.LocalDate;
import javafx.stage.Stage;

public class DashboardPage  {
    
    
    public void handleSelectedOption(int option, Stage primaryStage) {
       new model.DashboardPage().handleSelectedOption(option, primaryStage);
    }
    
    // Helper method to display an alert
    public void showAlert(String title, String message) {
        new model.DashboardPage().showAlert(title, message);
    }
    
    public void showAddPostDialog(Stage primaryStage) {
        new model.DashboardPage().showAddPostDialog(primaryStage);
    }
    
    public boolean validateAndSavePost(String content, String author, String likes, String shares, LocalDate date, String time) {
        return new model.DashboardPage().validateAndSavePost(content, author, likes, shares, date, time);
    }
    
    public void showDeletePostDialog(Stage primaryStage) {
    	new model.DashboardPage().showDeletePostDialog(primaryStage);
    }
    
    public boolean deletePost(int postId) {
        return new model.DashboardPage().deletePost(postId);
    }
    
    public void showRetrievePostDialog(Stage primaryStage) {
    	new model.DashboardPage().showRetrievePostDialog(primaryStage);
    }
    
    public String retrievePostInfo(int postId) {
        return new model.DashboardPage().retrievePostInfo(postId);
    }
    
    
    public void showRetrieveTopLikesDialog(Stage primaryStage) {
        new model.DashboardPage().showRetrieveTopLikesDialog(primaryStage);
    }
    
    public String retrieveTopLikedPosts(int n) {
    	return new model.DashboardPage().retrieveTopLikedPosts(n);
    }
    
    
    public void showRetrieveTopSharesDialog(Stage primaryStage) {
        new model.DashboardPage().showRetrieveTopSharesDialog(primaryStage);
    }

    public String retrieveTopSharedPosts(int n) {
        return new model.DashboardPage().retrieveTopSharedPosts(n);
    }
    
    public void exportPostToCSV(Stage primaryStage) {
        new model.DashboardPage().exportPostToCSV(primaryStage);
    }

    public boolean savePostToCSV(String postInfo, File file) {
        return new model.DashboardPage().savePostToCSV(postInfo, file);
    }
    
    public void bulkImportPosts(Stage primaryStage) {
        new model.DashboardPage().bulkImportPosts(primaryStage);
    }
    
    public void showSharesDistributionPieChart(Stage primaryStage) {
        new model.DashboardPage().showSharesDistributionPieChart(primaryStage);
    }


}
