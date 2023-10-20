package controller;



public class EditProfilePage {
	
    // Implement this method to update the user's profile in the database
    public boolean updateUserProfile(String newFirstName, String newLastName, String newUsername, String newPassword, String oldUserName) {
        return new model.EditProfilePage().updateUserProfile(newFirstName, newLastName, newUsername, newPassword, oldUserName);
    }
    
    // Retrieve the full name of the user
    public String getFullName(String username) {
        return new model.EditProfilePage().getFullName(username);
    }

    // Helper method to display an alert
    public void showAlert(String title, String message) {
        new model.EditProfilePage().showAlert(title, message);
    }
    
    public boolean isVIP(String username) {
    	
    	return new model.EditProfilePage().isVIP(username);
    }

}
