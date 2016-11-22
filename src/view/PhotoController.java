package view;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utility.Photo;
import utility.PhotoAlbum;
import utility.SerializeData;
import utility.Tag;
import utility.User;
/**
 * @author Matthew Ya
 * @author Taehee Lee
 */
public class PhotoController {
	/**
	 * ImageView where the image is displayed
	 */
	@FXML ImageView imageview;
	/**
	 * ListView where tags of photo are displayed
	 */
	@FXML ListView<Tag> taglist;
	/**
	 * TextArea where details of photo are displayed
	 */
	@FXML TextArea photodetails;
	/**
	 * TextField where name of the tag is to be enter. Used for adding tags.
	 */
	@FXML TextField tagname;
	/**
	 * TextField where value of the tag is to be enter. Used for adding tags.
	 */
	@FXML TextField tagvalue;
	/**
	 * Corresponds to the tag button on PhotoUI. Used for adding tags.
	 */
	@FXML Button tag;
	/**
	 * Corresponds to the next button on PhotoUI. Used for slideshow.
	 */
	@FXML Button next;
	/**
	 * Corresponds to the previous button on PhotoUI. Used for slideshow.
	 */
	@FXML Button previous;
	/**
	 * Corresponds to the return button on PhotoUI. Used to return to PhotoAlbum page.
	 */
	@FXML Button ret;
	/**
	 * MenuItem that is used on listview of tags. Used for removing tags from photo.
	 */
	@FXML MenuItem remove;
	/**
	 * Corresponds to caption button on PhotoUI. Used to caption/recaption photo.
	 */
	@FXML Button caption;
	/**
	 * TextArea where user enters a text. Used to caption/recaption photo.
	 */
	@FXML TextArea captiontext;
	/**
	 * ContextMenu to display menu items. Used for removing tags.
	 */
	@FXML ContextMenu cm;
	/**
	 * ObservableList to be used for ListView.
	 */
	private ObservableList<Tag> obstaglist;
	/**
	 * Photo that is being displayed
	 */
	private Photo photo;
	/**
	 * Album where photo is located
	 */
	private PhotoAlbum album;
	/**
	 * User that owns the photo and album.
	 */
	private User user;
	/**
	 * Loads photo and photo details onto page.
	 * 
	 * 
	 * @param user		user that owns album and photo
	 * @param album		album where photo is located
	 * @param photo		photo that is to be displayed
	 */
	public void start(User user,PhotoAlbum album,Photo photo){
		this.album = album;
		this.photo = photo;
		this.user = user;
		imageview.setImage(photo.getImage());
		imageview.setPreserveRatio(true);
		imageview.setSmooth(true);
		imageview.setCache(true);
		obstaglist = FXCollections.observableArrayList(photo.getTags());
		taglist.setItems(obstaglist);
		photodetails.setText(photo.getInfo());
		taglist.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if(event.getButton().equals(MouseButton.SECONDARY)){
					cm.show((Node)event.getSource(), event.getScreenX(), event.getScreenY());

				}
			}
			
		});
		if(this.album.getPhotos().size() == 1){
			next.setDisable(true);
			previous.setDisable(true);
		}else if(this.album.getPhotos().indexOf(photo) == 0){
			previous.setDisable(true);
			next.setDisable(false);
		}else if(this.album.getPhotos().indexOf(photo) == (this.album.getPhotos().size() - 1)){
			next.setDisable(true);
			previous.setDisable(false);
		}else{
			next.setDisable(false);
			previous.setDisable(false);
		}

		photodetails.setEditable(false);
	}
	/**
	 * Allows a user to caption/recaption and add a tag
	 * 
	 * <p>Tag: a user can add a tag if both text fields of the tag name and tag value are filled. 
	 * A user cannot add a duplicate tag to a photo.
	 * <p>Caption: a user can caption/recaption a photo if the caption textfield is filled.
	 * 
	 * @param e 	event from tag and caption button.
	 */
	public void buttonAction(ActionEvent e){
		Button b = (Button)e.getSource();
		if(b == tag){
			if(tagname.getText().isEmpty() || tagvalue.getText().isEmpty()){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Photo");
				alert.setHeaderText("ERROR!");
				alert.setContentText("Tag fields cannot be empty");
				alert.showAndWait();
			}else if(this.photo.containsTag(new Tag(tagname.getText().trim(),tagvalue.getText().trim()))){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Photo");
				alert.setHeaderText("ERROR!");
				alert.setContentText("Cannot add duplicate tags");
				alert.showAndWait();
			}else{
				Tag newtag = new Tag(tagname.getText().trim(),tagvalue.getText().trim());
				obstaglist.add(newtag);
				photo.addTag(newtag);
			}
			tagname.clear();
			tagvalue.clear();
		}else if(b == caption){
			this.photo.setCaption(captiontext.getText().trim());
			captiontext.clear();
		}
		photodetails.setText(this.photo.getInfo());
		SerializeData.writeData();
	}
	/**
	 * Allows a user to remove a tag from a photo.
	 * 
	 * 
	 * @param e		event from when the user selects the "remove tag" menuitem
	 */
	public void remove(ActionEvent e){
		if(taglist.getSelectionModel().getSelectedIndex() == -1){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Photo");
			alert.setHeaderText("ERROR!");
			alert.setContentText("No item selected.");
			alert.showAndWait();
		}else{
			photo.removeTag(taglist.getSelectionModel().getSelectedIndex());
			obstaglist.remove(taglist.getSelectionModel().getSelectedIndex());
		}
		SerializeData.writeData();
	}
	/**
	 * Allows a user to manually go through photos in an album.
	 * 
	 * 
	 * @param e		event from the next and previous button.
	 */
	public void slide(ActionEvent e){
		Button b = (Button)e.getSource();
		int index = this.album.getPhotos().indexOf(this.photo);
		if(b == next){
			start(this.user,this.album, this.album.getPhotos().get(index+1));
		}else if(b == previous){
			start(this.user,this.album, this.album.getPhotos().get(index-1));
		}
	}
	/**
	 * Allows a user to return to the photoalbum page
	 * 
	 * @param e		event from the return button
	 */
	public void returnToAlbumPage(ActionEvent e){
		try{
			SerializeData.writeData();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/PhotoAlbumUI.fxml"));
			Parent photoalbum = (Parent) fxmlLoader.load();
			Scene photoalbumpage = new Scene(photoalbum);
			Stage currStage = (Stage)((Node)e.getSource()).getScene().getWindow();
			PhotoAlbumController photoalbumController = fxmlLoader.getController();
			photoalbumController.start(this.user, this.album);
			currStage.setTitle(this.user.toString() + "\\" + this.album.toString());
			currStage.setScene(photoalbumpage);
			currStage.show();
		}catch(IOException e1){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Photo");
			alert.setHeaderText("ERROR!");
			alert.setContentText("Application error: mercy on my grade.");
			alert.showAndWait();
		}
	}
}
