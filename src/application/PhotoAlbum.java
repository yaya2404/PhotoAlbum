package application;
/**
 * @author Matthew Ya
 * @author Taehee Lee
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import utility.SerializeData;
import view.LoginController;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Start");
			alert.setHeaderText("ERROR!");
			alert.setContentText("Application error: mercy on my grade.");
			alert.showAndWait();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
