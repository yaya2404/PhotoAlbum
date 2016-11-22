package view;

import java.util.ArrayList;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import utility.Photo;
import utility.PhotoAlbum;
import utility.User;

public class SearchController {
	
	@FXML ScrollPane scrollpane;
	@FXML Button create;
	
	private User user;
	private ArrayList<Photo> query;
	public void start(User user,ArrayList<Photo> query){
		TilePane phototile = new TilePane();
		phototile.setPadding(new Insets(15, 15, 15, 15));
		phototile.setHgap(15);
		phototile.setVgap(15);
		phototile.setTileAlignment(Pos.TOP_LEFT);
		
		for(int i = 0; i < query.size(); i++){
			ImageView view = new ImageView(query.get(i).getImage());
			view.setFitWidth(100);
			view.setFitHeight(100);
			phototile.getChildren().add(view);
		}
		scrollpane.setContent(phototile);
		this.user = user;
		this.query = query;
	}
	public void create(ActionEvent e){
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Search");
		dialog.setHeaderText("To create a new album, enter a new name");
		dialog.setContentText("Enter Album name: ");
		Optional<String> result = dialog.showAndWait();
		String name = "";
		if(result.isPresent()){name = result.get().trim();}
		if(!name.isEmpty()){
			
			if(user.getAlbum(name) != null){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Search");
				alert.setHeaderText("ERROR!");
				alert.setContentText("Album name " + name + " already exists.");
				alert.showAndWait();
			}else{
				PhotoAlbum newalbum = new PhotoAlbum(name);
				newalbum.getPhotos().addAll(query);
				user.getAlbums().add(newalbum);
			}
		}
	}
}
