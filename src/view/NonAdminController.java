package view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
import utility.Photo;
import utility.PhotoAlbum;
import utility.SerializeData;
import utility.Tag;
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
	/**
	 * Load albums from database
	 */
	public void start(User user){
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
	public void logout(ActionEvent e){
		try{
			
			SerializeData.writeData();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/LoginUI.fxml"));
			Parent admin = (Parent) fxmlLoader.load();
			Scene adminpage = new Scene(admin);
			Stage currStage = (Stage)((Node)e.getSource()).getScene().getWindow();
			LoginController loginController = fxmlLoader.getController();
			loginController.start();
			currStage.setTitle("Login");
			currStage.setScene(adminpage);
			currStage.show();
		}catch(Exception e1){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("User");
			alert.setHeaderText("ERROR!");
			alert.setContentText("Application error: mercy on my grade.");
			alert.showAndWait();
		}
	}
	public void open(ActionEvent e){
		try{
			if(listView.getSelectionModel().getSelectedIndex() == -1){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("User");
				alert.setHeaderText("ERROR!");
				alert.setContentText("No item selected.");
				alert.showAndWait();
			}else{
				SerializeData.writeData();
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/PhotoAlbumUI.fxml"));
				Parent photoalbum = (Parent) fxmlLoader.load();
				Scene photoalbumpage = new Scene(photoalbum);
				Stage currStage = (Stage)((Node)e.getSource()).getScene().getWindow();
				PhotoAlbumController photoalbumController = fxmlLoader.getController();
				photoalbumController.start(this.user, this.user.getAlbum(listView.getSelectionModel().getSelectedItem().toString()));
				currStage.setTitle(this.user.toString() + "\\" + listView.getSelectionModel().getSelectedItem().toString());
				currStage.setScene(photoalbumpage);
				currStage.show();
			}
		}catch(FileNotFoundException e1){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("User");
			alert.setHeaderText("ERROR!");
			alert.setContentText("A photo has an invalid file path"); //check this
			alert.showAndWait();
			e1.printStackTrace();
		}catch(Exception z){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("User");
			alert.setHeaderText("ERROR!");
			alert.setContentText("Application error: mercy on my grade.");
			alert.showAndWait();
		}
	}
	public void search(ActionEvent e){
		if((!tagtype.getText().trim().isEmpty() && !tagvalue.getText().trim().isEmpty()) || (startdate.getValue() != null && enddate.getValue() != null)){
			ArrayList<Photo> query = new ArrayList<Photo>();
			if((startdate.getValue() != null && enddate.getValue() != null)){
				GregorianCalendar start = new GregorianCalendar();
				GregorianCalendar end = new GregorianCalendar();
				start.set(Calendar.MILLISECOND, 0);
				start.set(startdate.getValue().getYear(), startdate.getValue().getMonthValue()-1, startdate.getValue().getDayOfMonth(), 0, 0,0);
				end.set(Calendar.MILLISECOND, 0);
				end.set(enddate.getValue().getYear(), enddate.getValue().getMonthValue()-1, enddate.getValue().getDayOfMonth(),23,59,59);
				Date s = start.getTime();
				Date en = end.getTime();
				query.addAll(this.user.searchByDate(s, en));
			}
			if((!tagtype.getText().trim().isEmpty() && !tagvalue.getText().trim().isEmpty())){
				Tag test = new Tag(tagtype.getText().trim(), tagvalue.getText().trim());
				ArrayList<Photo> tagquery = this.user.searchByTag(test);
				for(int i = 0; i < tagquery.size();i++){
					if(!query.contains(tagquery.get(i))){
						query.add(tagquery.get(i));
					}
				}
			}
			if(query.size() == 0){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("User");
				alert.setHeaderText("Search Photos");
				alert.setContentText("No photos were found in search");
				alert.showAndWait();
			}else{
				tagtype.clear();
				tagvalue.clear();
				try{
					SerializeData.writeData();
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/SearchUI.fxml"));
					Parent search = (Parent) fxmlLoader.load();
					Scene searchpage = new Scene(search);
					Stage currStage = new Stage();
					SearchController searchController = fxmlLoader.getController();
					searchController.start(this.user,query);
					currStage.setTitle("Search");
					currStage.setScene(searchpage);
					currStage.show();

				}catch(IOException e1){
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("User");
					alert.setHeaderText("ERROR!");
					alert.setContentText("Application error: mercy on my grade.");
					alert.showAndWait();
					e1.printStackTrace();
				}
			}
		}else{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("User");
			alert.setHeaderText("ERROR!");
			alert.setContentText("Search error: must enter both a tag name and tag value");
			alert.showAndWait();
		}

	}
}
