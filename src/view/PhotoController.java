package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utility.Photo;
import utility.Tag;

public class PhotoController {
	
	@FXML ImageView imageview;
	@FXML ListView<Tag> taglist;
	@FXML TextArea photodetails;
	@FXML TextField tagname;
	@FXML TextField tagvalue;
	@FXML Button tag;
	@FXML Button remove;
	@FXML Button caption;
	@FXML TextArea captiontext;
	private ObservableList<Tag> obstaglist;
	private Photo photo;
	public void start(Photo photo){
		imageview.setImage(photo.getImage());
		imageview.setPreserveRatio(true);
		imageview.setSmooth(true);
		imageview.setCache(true);
		obstaglist = FXCollections.observableArrayList(photo.getTags());
		taglist.setItems(obstaglist);
		photodetails.setText(photo.getInfo());
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
				photo.getTags().add(newtag);
			}
			tagname.clear();
			tagvalue.clear();
		}else if(b == remove){
			if(taglist.getSelectionModel().getSelectedIndex() == -1){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Photo");
				alert.setHeaderText("ERROR!");
				alert.setContentText("No item selected.");
				alert.showAndWait();
			}else{
				photo.getTags().remove(obstaglist.remove(taglist.getSelectionModel().getSelectedIndex()));
			}
		}else if(b == caption){
			this.photo.setCaption(captiontext.getText().trim());
			captiontext.clear();
		}
		photodetails.setText(this.photo.getInfo());
	}
}
