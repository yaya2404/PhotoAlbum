package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AdminController {
	
	@FXML private Button add;
	@FXML private Button edit;
	@FXML private Button logout;
	@FXML private TextField userid;
	@FXML private ListView listview;
	
	private ArrayList<String> users;
	
	private static final String dir = "admin";
	private static final String file = "users";
	
	private Stage stage;
	public void start(Stage mainstage){
		this.stage = mainstage;
		try{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dir + File.separator + file));
			users = (ArrayList<String>)ois.readObject();
			ois.close();
		}catch(Exception a){
			a.printStackTrace();
		}
	}
	public void ael(ActionEvent e){
		Button b = (Button) e.getSource();
		try{
			if(b == add){
				if(userid.getText().isEmpty()){
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Admin");
					alert.setHeaderText("ERROR!");
					alert.setContentText("UserID field is empty");
					alert.showAndWait();
				}else if(userid.getText().trim().compareToIgnoreCase("admin") == 0){
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Admin");
					alert.setHeaderText("ERROR!");
					alert.setContentText("Admin should not be added to list of users");
					alert.showAndWait();
				}else{
					users.add(userid.getText().trim());
				}
			}else if(b == edit){
				
			}else if(b == logout){
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/LoginUI.fxml"));
				Parent admin = (Parent) fxmlLoader.load();
				Scene adminpage = new Scene(admin);
				Stage currStage = (Stage) this.stage.getScene().getWindow();
				LoginController loginController = fxmlLoader.getController();
				loginController.start(this.stage);
				currStage.setScene(adminpage);
				currStage.show();
			}
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dir + File.separator + file));
			oos.writeObject(users);
			oos.close();
		}catch(IOException z){
			z.printStackTrace();
		}
	}
}
