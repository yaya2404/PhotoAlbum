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
		phototile = new TilePane();
		phototile.setPadding(new Insets(15, 15, 15, 15));
		phototile.setHgap(15);
		phototile.setPrefTileHeight(photogallery.getPrefHeight());
		phototile.setPrefWidth(photogallery.getPrefWidth());
		this.userAlbum = album;
		this.photoData = album.getPhotos();
		for(int i = 0; i < this.photoData.size(); i++){
			//Label view = new Label(this.photoData.get(i).getCaption());
			//view.setTextAlignment(TextAlignment.JUSTIFY);
			//view.setGraphic(createImageView(this.photoData.get(i).getImage()));
			ImageView view = createImageView(this.photoData.get(i).getImage());
			phototile.getChildren().addAll(view);
		}
		photogallery.setContent(phototile);
		this.user = user;
	}

	private ImageView createImageView(Image image) throws FileNotFoundException{

		//Image image = new Image(new FileInputStream("imageFile"), 150, 0, true, true);
		final ImageView imageView = new ImageView(image);
		imageView.setFitWidth(100);
		imageView.setFitHeight(100);
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

				if(name.isEmpty()){
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Copy Photo");
					alert.setHeaderText("ERROR!");
					alert.setContentText("Photo name cannot be empty");
					alert.showAndWait();
				}else{
					PhotoAlbum album = user.getAlbum(name);
					if(album == null){
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Move Photo");
						alert.setHeaderText("ERROR!");
						alert.setContentText(name + " does not exist");
						alert.showAndWait();
					}else{
						album.getPhotos().add(photoData.get(getIndexOfPhoto(image)));
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
				if(name.isEmpty()){
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Move Photo");
					alert.setHeaderText("ERROR!");
					alert.setContentText("Photo name cannot be empty");
					alert.showAndWait();
				}else{
					PhotoAlbum album = user.getAlbum(name);
					if(album == null){
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Move Photo");
						alert.setHeaderText("ERROR!");
						alert.setContentText(name + " does not exist");
						alert.showAndWait();
					}else{
						album.getPhotos().add(photoData.remove(getIndexOfPhoto(image)));
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
				photoData.remove(getIndexOfPhoto(image));
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
							photoController.start(photoData.get(getIndexOfPhoto(image)));
							currStage.setScene(photoalbumpage);
							currStage.show();
						}catch(IOException r){
							
						}
					}else if(mouseEvent.getClickCount() == 1){
						caption.setText(photoData.get(getIndexOfPhoto(image)).getCaption());
					}
				}else if(mouseEvent.getButton().equals(MouseButton.SECONDARY)){
					cm.show((Node)mouseEvent.getSource(), mouseEvent.getScreenX(), mouseEvent.getScreenY());
				}
			}
		});
		return imageView;
	}
	private int getIndexOfPhoto(Image image){
		for(int i = 0; i < this.photoData.size(); i++){
			if(this.photoData.get(i).getImage().equals(image)){
				return i;
			}
		}
		return -1;
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
	            currStage.setScene(adminpage);
	            currStage.show();
			}catch(IOException e1){
				e1.printStackTrace();
			}
		}
		SerializeData.writeData();
	}
}
