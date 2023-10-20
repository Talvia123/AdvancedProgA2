package Main;

import javafx.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			 //RegistrationPage registrationPage = new RegistrationPage(primaryStage);
			 //registrationPage.start(primaryStage);
			new view.RegistrationPage().start(primaryStage);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
