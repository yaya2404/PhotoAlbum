package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import utility.SerializeData;
import view.LoginController;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class PhotoAlbum extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			SerializeData.initData();
			FXMLLoader loader = new FXMLLoader();   
			loader.setLocation(getClass().getResource("/view/LoginUI.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			LoginController loginController = loader.getController();
			loginController.start();
			primaryStage.setTitle("Login");
			Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
