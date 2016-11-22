package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.DropShadow;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import utility.Photo;
import utility.PhotoAlbum;
import utility.SerializeData;
import utility.User;

public class PhotoAlbumController {



	@FXML Button add;
	@FXML Button exit;
	@FXML ScrollPane photogallery;
	@FXML Text caption;
	private TilePane phototile;
	private PhotoAlbum userAlbum;
	private ArrayList<Photo> photoData;
	private User user;
	public void start(User user, PhotoAlbum album) throws FileNotFoundException{
		this.userAlbum = album;
		this.photoData = album.getPhotos();
		
		
		phototile = new TilePane();
		phototile.setPadding(new Insets(15, 15, 15, 15));
		phototile.setHgap(15);
		phototile.setVgap(15);
		phototile.setTileAlignment(Pos.TOP_LEFT);

		for(int i = 0; i < this.photoData.size(); i++){
			ImageView view = createImageView(this.photoData.get(i).getImage());
			phototile.getChildren().add(view);
		}
		photogallery.setContent(phototile);
		photogallery.setFitToWidth(true);
		this.user = user;
	}

	private ImageView createImageView(Image image) throws FileNotFoundException{

	
		final ImageView imageView = new ImageView(image);
		int size = 100;
		imageView.setFitWidth(size);
		imageView.setFitHeight(size);
		
		//Context menu to copy, move, and delete a picture.
		ContextMenu cm = new ContextMenu();
		MenuItem copyImage = new MenuItem("Copy image");
		MenuItem moveImage = new MenuItem("Move image");
		MenuItem deleteImage = new MenuItem("Delete image");
		copyImage.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				TextInputDialog dialog = new TextInputDialog();
				dialog.setTitle("Copy Photo");
				dialog.setHeaderText("To copy a photo to a different album, enter the album name");
				dialog.setContentText("Enter Album name: ");
				Optional<String> result = dialog.showAndWait();
				String name = "";

				if(result.isPresent()){name = result.get().trim();}

				if(!name.isEmpty()){
					PhotoAlbum album = user.getAlbum(name);
					if(album == null){
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Move Photo");
						alert.setHeaderText("ERROR!");
						alert.setContentText(name + " does not exist");
						alert.showAndWait();
					}else{
						album.getPhotos().add(photoData.get(userAlbum.getIndexOfPhoto(image)));
					}
				}
			}
		}
				);
		moveImage.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				TextInputDialog dialog = new TextInputDialog();
				dialog.setTitle("Move Photo");
				dialog.setHeaderText("To move a photo to a different album, enter the album name");
				dialog.setContentText("Enter Album name: ");
				Optional<String> result = dialog.showAndWait();
				String name = "";
				if(result.isPresent()){name = result.get().trim();}
				if(!name.isEmpty()){
					PhotoAlbum album = user.getAlbum(name);
					if(album == null){
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Move Photo");
						alert.setHeaderText("ERROR!");
						alert.setContentText(name + " does not exist");
						alert.showAndWait();
					}else{
						album.getPhotos().add(photoData.remove(userAlbum.getIndexOfPhoto(image)));
						imageView.setImage(null);
						phototile.getChildren().remove(imageView);
					}
				}
			}
		}
				);
		deleteImage.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				imageView.setImage(null);
				phototile.getChildren().remove(imageView);
				photoData.remove(userAlbum.getIndexOfPhoto(image));
			}

		});
		cm.getItems().add(copyImage);
		cm.getItems().add(moveImage);
		cm.getItems().add(deleteImage);

		//add mouse event to imageview
		imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
					if(mouseEvent.getClickCount() == 2){
						try{
							FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/PhotoUI.fxml"));
							Parent photoalbum = (Parent) fxmlLoader.load();
							Scene photoalbumpage = new Scene(photoalbum);
							Stage currStage = new Stage();
							PhotoController photoController = fxmlLoader.getController();
							photoController.start(photoData,photoData.get(userAlbum.getIndexOfPhoto(image)));
							currStage.setTitle(((Stage)((Node)mouseEvent.getSource()).getScene().getWindow()).getTitle() + "\\" + photoData.get(userAlbum.getIndexOfPhoto(image)).getName());
							currStage.setScene(photoalbumpage);
							currStage.show();
						}catch(IOException r){
							r.printStackTrace();
						}
					}else if(mouseEvent.getClickCount() == 1){
						caption.setText("Caption: " + photoData.get(userAlbum.getIndexOfPhoto(image)).getCaption());
						DropShadow borderGlow= new DropShadow();
						borderGlow.setOffsetY(0f);
						borderGlow.setOffsetX(0f);
						borderGlow.setColor(Color.CYAN);
						borderGlow.setWidth(75);
						borderGlow.setHeight(75);
						imageView.setEffect(borderGlow);
					}
				}else if(mouseEvent.getButton().equals(MouseButton.SECONDARY)){
					cm.show((Node)mouseEvent.getSource(), mouseEvent.getScreenX(), mouseEvent.getScreenY());

				}
			}
		});
		//highlight image when user hovers over it.
		imageView.setOnMouseEntered(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				DropShadow borderGlow= new DropShadow();
				borderGlow.setOffsetY(0f);
				borderGlow.setOffsetX(0f);
				borderGlow.setColor(Color.LIGHTSKYBLUE);
				borderGlow.setWidth(75);
				borderGlow.setHeight(75);
				imageView.setEffect(borderGlow);
			}
			
		});
		//remove highlight when user's mouse exits the image.
		imageView.setOnMouseExited(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				imageView.setEffect(null);
			}
			
		});
		return imageView;
	}
	public void buttonAction(ActionEvent e){
		Button b = (Button) e.getSource();
		if(b == add){
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Add Photo");
			File photofile = fileChooser.showOpenDialog((Stage)((Node)e.getSource()).getScene().getWindow());
			if(photofile != null){

				try {
					
					Photo newphoto = new Photo(photofile);
					newphoto.setImage();
					phototile.getChildren().addAll(createImageView(newphoto.getImage()));
					this.photoData.size();
					this.photoData.add(newphoto);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Photo Album");
					alert.setHeaderText("ERROR!");
					alert.setContentText("Invalid file path");
					alert.showAndWait();
					e1.printStackTrace();
				}
			}

		}else if(b == exit){
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
				e1.printStackTrace();
			}
		}
		SerializeData.writeData();
	}
}
