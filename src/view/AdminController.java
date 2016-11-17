package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import utility.SerializeData;
import utility.User;

public class AdminController{
	
	@FXML Button add;
	@FXML Button delete;
	@FXML Button logout;
	@FXML TextField userid;
	@FXML ListView<User> listview;
	private ObservableList<User> users;
	
	static final String dir = "admin";
	static final String file = "users";
	
	private Stage stage;
	
	public void start(Stage mainstage){
		this.stage = mainstage;
		
		users = SerializeData.getData();
		
		listview.setItems(users);
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
				}else if(containUser(userid.getText().trim())){
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Admin");
					alert.setHeaderText("ERROR!");
					alert.setContentText("Duplicate user");
					alert.showAndWait();
				}else{
					User user = new User(userid.getText().trim());
					users.add(user);
					userid.clear();
				}
			}else if(b == delete){

				if(users.size() == 0){
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Admin");
					alert.setHeaderText("ERROR!");
					alert.setContentText("Cannot delete from an empty list");
					alert.showAndWait();
				}else if(listview.getSelectionModel().getSelectedIndex() == -1){
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Admin");
					alert.setHeaderText("ERROR!");
					alert.setContentText("No item selected.");
					alert.showAndWait();
				}else{
					User item = listview.getSelectionModel().getSelectedItem();
					int index = users.indexOf(item);
					users.remove(item);
					if(users.size() != 0){
						if(index+1 > users.size()){
							listview.getSelectionModel().selectPrevious();
						}else{
							listview.getSelectionModel().selectNext();
						}
					}else{
						listview.getSelectionModel().clearSelection();
					}
				}
			}else if(b == logout){
				SerializeData.writeData();
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/LoginUI.fxml"));
				Parent admin = (Parent) fxmlLoader.load();
				Scene adminpage = new Scene(admin);
				Stage currStage = (Stage) this.stage.getScene().getWindow();
				LoginController loginController = fxmlLoader.getController();
				loginController.start(this.stage);
				currStage.setScene(adminpage);
				currStage.show();
			}
			SerializeData.writeData();
		}catch(IOException z){
			z.printStackTrace();
		}
	}
	private boolean containUser(String name){
		for(int i = 0; i < users.size(); i++){
			if(users.get(i).toString().compareToIgnoreCase(name) == 0){
				return true;
			}
		}
		
		return false;
	}
}
