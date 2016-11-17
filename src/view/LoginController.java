package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import utility.SerializeData;
import utility.User;

public class LoginController {
	
	@FXML Button enter;
	@FXML TextField userinput;
	private Stage stage;
	/**
	 * Loads existing users from file into a string array
	 *
	 */
	private ArrayList<User> users;
	
	public void start(Stage mainstage){
		try{
			//obtains permitted users from admin folder
			users = new ArrayList<User>(SerializeData.getData());
		}catch(Exception a){
			//a.printStackTrace();
		}
		this.stage = mainstage;
	}
	public void login(){
		String userid = userinput.getText().trim();
		int index = -1;
		try{
			if(userid.compareToIgnoreCase("admin") == 0){
				//user is admin load AdminUI
		        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AdminUI.fxml"));
	            Parent admin = (Parent) fxmlLoader.load();
	            Scene adminpage = new Scene(admin);
	            Stage currStage = (Stage) this.stage.getScene().getWindow();
	            AdminController adminController = fxmlLoader.getController();
	            adminController.start(this.stage);
	            currStage.setScene(adminpage);
	            currStage.show();
			}else if((index = getUser(userid)) > -1){
				//user is in list. Load corresponding photoalbumUI to user
				User user = users.get(index);
		        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/NonAdminUI.fxml"));
	            Parent admin = (Parent) fxmlLoader.load();
	            Scene adminpage = new Scene(admin);
	            Stage currStage = (Stage) this.stage.getScene().getWindow();
	            NonAdminController nonadminController = fxmlLoader.getController();
	            nonadminController.start(this.stage, user);
	            currStage.setScene(adminpage);
	            currStage.show();
			}else{
				//user does not exist in list of users
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Login");
				alert.setHeaderText("ERROR!");
				alert.setContentText("Invalid User");
				alert.showAndWait();
			}
		}catch(IOException h){
			h.printStackTrace();
		}
	}
	private int getUser(String name){
		for(int i = 0; i < users.size(); i++){
			if(users.get(i).toString().compareToIgnoreCase(name) == 0){
				return i;
			}
		}
		
		return -1;
	}

}
