package view;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
	
	@FXML private Button enter;
	@FXML private TextField userinput;
	private Stage stage;
	/**
	 * Loads existing users from file into a string array
	 *
	 */
	private ArrayList<String> users;
	public void start(Stage mainstage){
		users = new ArrayList<String>();
		this.stage = mainstage;
	}
	public void login() throws IOException{
		String userid = userinput.getText().trim();
		if(userid.compareToIgnoreCase("admin") == 0){
			//user is admin load AdminUI
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AdminUI.fxml"));
	        
            Parent admin = (Parent) fxmlLoader.load();
            Scene adminpage = new Scene(admin);
            Stage currStage = (Stage) this.stage.getScene().getWindow();
            AdminController adminController = fxmlLoader.getController();
            adminController.start()
            currStage.setScene(adminpage);
            currStage.show();
		}else if(users.contains(userid)){
			//user is in list. Load corresponding photoalbumUI to user
		}else{
			//user does not exist in list of users
		}
	}

}
