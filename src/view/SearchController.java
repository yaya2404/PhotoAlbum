package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import utility.Photo;
import utility.PhotoAlbum;
import utility.SerializeData;
import utility.User;
/**
 * @author Matthew Ya
 * @author Taehee Lee
 */
public class SearchController {
	
	/**
	 * Corresponds to the scrollpane on the SearchUI
	 */
	@FXML ScrollPane scrollpane;
	/**
	 * Corresponds to the create button on SearchUI. Used for create.
	 */
	@FXML Button create;
	/**
	 * Corresponds to the return button SearchUI. Used for returnToUserPage().
	 */
	@FXML Button ret;
	
	/**
	 * User that is doing search.
	 */
	private User user;
	/**
	 * Photos that were found in the search
	 */
	private ArrayList<Photo> query;
	/**
	 * Loads the images onto the page.
	 * 
	 * @param user	the user that is doing the search
	 * @param query	the photos the were found in the search
	 */
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
		scrollpane.setFitToWidth(true);
		this.user = user;
		this.query = query;
	}
	/**
	 * Allows user to create an album based on the photos from query.
	 * 
	 * @param e		event from the button create.
	 */
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
				SerializeData.writeData();
			}
		}
		
	}
	/**
	 * Allows a user to return to the non-admin subsystem.
	 * 
	 * 
	 * @param e		event from the button return.
	 */
	public void returnToUserPage(ActionEvent e){
		try{
			SerializeData.writeData();
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/NonAdminUI.fxml"));
            Parent admin = (Parent) fxmlLoader.load();
            Scene adminpage = new Scene(admin);
            Stage currStage = (Stage)((Node)e.getSource()).getScene().getWindow();
            NonAdminController nonadminController = fxmlLoader.getController();
            nonadminController.start(this.user);
            currStage.setTitle(this.user.toString());
            currStage.setScene(adminpage);
            currStage.show();
		}catch(IOException e1){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("User");
			alert.setHeaderText("ERROR!");
			alert.setContentText("Application error: mercy on my grade.");
			alert.showAndWait();
		}
	}
}
