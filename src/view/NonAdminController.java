package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utility.PhotoAlbum;
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
	
	private ObservableList<PhotoAlbum> albums;
	
	User user;
	
	private Stage stage;
	/**
	 * Load albums from database
	 */
	public void start(Stage stage, User user){
		this.stage = stage;
		this.user = user;
		albums = FXCollections.observableArrayList(user.getAlbums());
		listView.setItems(albums);
	}
	
	public void cdr(ActionEvent e){
		
	}
	public void logout(){
		
	}
	public void open(){
		
	}
	public void search(){
		
	}
}
