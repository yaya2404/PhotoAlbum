package view;

import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import utility.Photo;
import utility.PhotoAlbum;
import utility.SerializeData;

public class PhotoAlbumController {
	
	@FXML Button add;
	@FXML Button delete;
	@FXML Button next;
	@FXML Button tag;
	@FXML Button remove;
	@FXML Button write;
	@FXML Button previous;
	@FXML Button move;
	@FXML Button copy;
	@FXML ListView<Photo> listPhotoView;
	@FXML ImageView photoView;
	@FXML TextField tagname;
	@FXML TextField tagvalue;
	@FXML TextField caption;
	@FXML TextArea photodetails;
	
	private PhotoAlbum userAlbum;
	private ObservableList<Photo> photoList;
	private ArrayList<Photo> photoData;
	
	public void start(PhotoAlbum album){
		this.userAlbum = album;
		this.photoData = album.getPhotos();
		photoList = FXCollections.observableArrayList(this.photoData);
		listPhotoView
		.getSelectionModel()
		.selectedItemProperty()
		.addListener(
				(obs, oldVal, newVal) -> 
				showItem()); 
	}
	public void showItem(){
		
	}
	public void buttonAction(ActionEvent e){
		Button b = (Button) e.getSource();
		if(b == add){
			   TextInputDialog dialog = new TextInputDialog();
			   dialog.setTitle("Add Photo");
			   dialog.setHeaderText("To add a photo, enter path name.");
			   dialog.setContentText("Enter path name: ");
			   Optional<String> result = dialog.showAndWait();
		}else if(b == delete){
			
		}else if(b == tag){
			
		}else if(b == remove){
			
		}else if(b == write){
			
		}
		SerializeData.writeData();
	}
}
