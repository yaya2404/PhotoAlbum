package view;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
import utility.Photo;
import utility.SerializeData;
import utility.Tag;

public class PhotoController {
	
	@FXML ImageView imageview;
	@FXML ListView<Tag> taglist;
	@FXML TextArea photodetails;
	@FXML TextField tagname;
	@FXML TextField tagvalue;
	@FXML Button tag;
	@FXML Button next;
	@FXML Button previous;
	@FXML MenuItem remove;
	@FXML Button caption;
	@FXML TextArea captiontext;
	@FXML ContextMenu cm;
	private ObservableList<Tag> obstaglist;
	private Photo photo;
	private ArrayList<Photo> album;
	public void start(ArrayList<Photo> album,Photo photo){
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
		if(album.size() == 1){
			next.setDisable(true);
			previous.setDisable(true);
		}else if(album.indexOf(photo) == 0){
			previous.setDisable(false);
		}else if(album.indexOf(photo) == album.size() -1){
			next.setDisable(true);
		}
		this.album = album;
		this.photo = photo;
	}
	public void buttonAction(ActionEvent e){
		Button b = (Button)e.getSource();
		if(b == tag){
			if(tagname.getText().isEmpty() || tagvalue.getText().isEmpty()){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Photo");
				alert.setHeaderText("ERROR!");
				alert.setContentText("Tag fields cannot be empty");
				alert.showAndWait();
			}else if(obstaglist.contains(new Tag(tagname.getText().trim(),tagvalue.getText().trim()))){
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
	public void remove(ActionEvent e){
		if(taglist.getSelectionModel().getSelectedIndex() == -1){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Photo");
			alert.setHeaderText("ERROR!");
			alert.setContentText("No item selected.");
			alert.showAndWait();
		}else{
			photo.getTags().remove(obstaglist.remove(taglist.getSelectionModel().getSelectedIndex()));
		}
		SerializeData.writeData();
	}
	public void slide(ActionEvent e){
		Button b = (Button)e.getSource();
	}
}
