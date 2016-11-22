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
import javafx.scene.Node;
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
/**
 * @author Matthew Ya
 * @author Taehee Lee
 */
public class AdminController{
	
	/**
	 * Corresponds to the add button 
	 */
	@FXML Button add;
	/**
	 * Corresponds to the delete button
	 */
	@FXML Button delete;
	/**
	 * Corresponds to the logout button
	 */
	@FXML Button logout;
	/**
	 * TextField where admin enters userID
	 */
	@FXML TextField userid;
	/**
	 * ListView of all permitted users
	 */
	@FXML ListView<User> listview;
	/**
	 * ObservableList of users used with ListView of users
	 */
	private ObservableList<User> users;
	/**
	 * the ArrayList of Users obtained from the file
	 */
	private ArrayList<User> hardusers;
	
	
	
	/**
	 * This function obtains the ArrayList of Users from the file and creates a listview of the
	 * users.
	 */
	public void start(){
		
		hardusers = SerializeData.getData();
		users = FXCollections.observableArrayList(hardusers);
		
		listview.setItems(users);
	}
	/**
	 * Handles the add, delete, or logout button actions.
	 * 
	 * <p>Add: adds a new user to the list of permitted users. The admin
	 * cannot add a userid of "admin", a duplicate user, or an empty userID.
	 * 
	 * <p>Delete: deletes a user from the list of permitted users by selecting an item
	 * from the listview and pressing the delete button. 
	 * 
	 * <p>Logout: the admin logouts from the admin subsystem and returns to the login page.
	 * 
	 * @param e		An event from button: add, delete, or logout.
	 */
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
					users.add(new User(userid.getText().trim()));
					hardusers.add(new User(userid.getText().trim()));
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
					hardusers.remove(item);
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
				Parent nonadmin = (Parent) fxmlLoader.load();
				Scene nonadminpage = new Scene(nonadmin);
				Stage currStage = (Stage)((Node)e.getSource()).getScene().getWindow();
				LoginController loginController = fxmlLoader.getController();
				loginController.start();
				currStage.setTitle("Login");
				currStage.setScene(nonadminpage);
				currStage.show();
			}
			SerializeData.writeData();
		}catch(IOException z){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Admin");
			alert.setHeaderText("ERROR!");
			alert.setContentText("Application error: mercy on my grade.");
			alert.showAndWait();
		}
	}
	/**
	 * Searchs the ArrayList of Users to see if a User with that name exists.
	 * 
	 * @param name		name of the User that is being queried
	 * @return		true if a User with that name exists. false if not
	 */
	private boolean containUser(String name){
		for(int i = 0; i < users.size(); i++){
			if(users.get(i).toString().compareToIgnoreCase(name) == 0){
				return true;
			}
		}
		
		return false;
	}
}
