package view;

import java.io.FileNotFoundException;
import java.io.IOException;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import utility.PhotoAlbum;
import utility.SerializeData;
import utility.User;

public class NonAdminController {
	@FXML Button create;
	@FXML Button delete;
	@FXML Button rename;
	@FXML Button open;
	@FXML Button search;
	@FXML Button logout;
	@FXML TextField album;
	@FXML TextField tagtype;
	@FXML TextField tagvalue;
	@FXML ListView<PhotoAlbum> listView;
	@FXML TextArea albuminfo;
	@FXML DatePicker startdate;
	@FXML DatePicker enddate;
	
	private ObservableList<PhotoAlbum> albums;
	private ArrayList<PhotoAlbum> hardalbums;
	
	private User user;
	
	private Stage stage;
	/**
	 * Load albums from database
	 */
	public void start(Stage stage, User user){
		this.stage = stage;
		this.user = user;
		this.hardalbums = this.user.getAlbums();
		this.albums = FXCollections.observableArrayList(this.hardalbums);
		listView.setItems(this.albums);
		listView
		.getSelectionModel()
		.selectedItemProperty()
		.addListener(
				(obs, oldVal, newVal) -> 
				showItem()); 
	}
	
	private void showItem() {
		// TODO Auto-generated method stub
		albuminfo.setText(listView.getSelectionModel().getSelectedItem().getInfo());
	}

	public void cdr(ActionEvent e){
		Button a = (Button) e.getSource();
		String name;
		if(a == create){
			if(album.getText().isEmpty()){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("User");
				alert.setHeaderText("ERROR!");
				alert.setContentText("Album name field is empty");
				alert.showAndWait();
			}else if(this.user.getAlbum((album.getText().trim())) != null){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("User");
				alert.setHeaderText("ERROR!");
				alert.setContentText("Duplicate Album");
				alert.showAndWait();
			}else{
				name = this.album.getText().trim();
				PhotoAlbum test = new PhotoAlbum(name);
				this.albums.add(test);
				this.hardalbums.add(test);
				listView.getSelectionModel().select(test);
				this.album.clear();
			}
		}else if(a == rename){
			if(listView.getSelectionModel().getSelectedIndex() == -1){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("User");
				alert.setHeaderText("ERROR!");
				alert.setContentText("No item selected.");
				alert.showAndWait();
			}else if(album.getText().isEmpty()){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("User");
				alert.setHeaderText("ERROR!");
				alert.setContentText("Album name field is empty");
				alert.showAndWait();
			}else if(this.user.getAlbum((album.getText().trim())) != null){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("User");
				alert.setHeaderText("ERROR!");
				alert.setContentText("Duplicate Album");
				alert.showAndWait();
			}else{
				name = album.getText().trim();
				PhotoAlbum item = listView.getSelectionModel().getSelectedItem();
				PhotoAlbum old = this.user.getAlbum(item.toString());
				old.setName(name);
				item.setName(name);
				listView.refresh();
				albuminfo.setText(item.getInfo());
				album.clear();
			}
		}else if(a == delete){
			if(albums.size() == 0){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("User");
				alert.setHeaderText("ERROR!");
				alert.setContentText("Cannot delete from an empty list");
				alert.showAndWait();
			}else if(listView.getSelectionModel().getSelectedIndex() == -1){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("User");
				alert.setHeaderText("ERROR!");
				alert.setContentText("No item selected.");
				alert.showAndWait();
			}else{
				PhotoAlbum item = listView.getSelectionModel().getSelectedItem();
				int index = albums.indexOf(item);
				albums.remove(item);
				this.user.getAlbums().remove(item);
				if(albums.size() != 0){
					if(index+1 > albums.size()){
						listView.getSelectionModel().selectPrevious();
					}else{
						listView.getSelectionModel().selectNext();
					}
				}else{
					listView.getSelectionModel().clearSelection();
				}
			}
		}
		SerializeData.writeData();
	}
	public void logout(){
		try{
			SerializeData.writeData();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/LoginUI.fxml"));
			Parent admin = (Parent) fxmlLoader.load();
			Scene adminpage = new Scene(admin);
			Stage currStage = (Stage) this.stage.getScene().getWindow();
			LoginController loginController = fxmlLoader.getController();
			loginController.start(this.stage);
			currStage.setScene(adminpage);
			currStage.show();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void open(){
		
	}
	public void search(){
		
	}
}
