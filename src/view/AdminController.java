package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

public class AdminController{
	
	@FXML Button add;
	@FXML Button delete;
	@FXML Button logout;
	@FXML TextField userid;
	@FXML ListView<String> listview;
	@FXML ObservableList<String> users;
	
	static final String dir = "admin";
	static final String file = "users";
	
	private Stage stage;
	
	public void start(Stage mainstage){
		this.stage = mainstage;
		users = FXCollections.observableArrayList();
		try{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dir + File.separator + file));
			users = FXCollections.observableArrayList((ArrayList<String>)ois.readObject());
			ois.close();
		}catch(Exception a){
			System.out.println(a.getMessage());
		}
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
				}else{
					users.add(userid.getText().trim());
					userid.clear();
				}
			}else if(b == delete){
				String item = listview.getSelectionModel().getSelectedItem();
				int index = users.indexOf(item);
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
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dir + File.separator + file));
				oos.writeObject(new ArrayList<String>(users));
				oos.close();
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
			oos.writeObject(new ArrayList<String>(users));
			oos.close();
		}catch(IOException z){
			z.printStackTrace();
		}
	}
}
