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
/**
 * @author Matthew Ya
 * @author Taehee Lee
 */
public class NonAdminController {
	/**
	 * Corresponds to the create button on NonAdminUI
	 */
	@FXML Button create;
	/**
	 * Corresponds to the delete button on NonAdminUI
	 */
	@FXML Button delete;
	/**
	 * Corresponds to the rename button on NonAdminUI
	 */
	@FXML Button rename;
	/**
	 * Corresponds to the open button on NonAdminUI
	 */
	@FXML Button open;
	/**
	 * Corresponds to the search button on NonAdminUI
	 */
	@FXML Button search;
	/**
	 * Corresponds to the logout button on NonAdminUI
	 */
	@FXML Button logout;
	/**
	 * TextField where user enters the name of the album. Used for create and rename.
	 */
	@FXML TextField album;
	/**
	 * TextField where user enters the name of the tag type. Used for Search.
	 */
	@FXML TextField tagtype;
	/**
	 * TextField where user enters the value of the tag value. Used for Search.
	 */
	@FXML TextField tagvalue;
	/**
	 * list of name of albums used for ListView.
	 */
	@FXML ListView<PhotoAlbum> listView;
	/**
	 * Textarea that displays information corresponding to the album selected in the listview.
	 */
	@FXML TextArea albuminfo;
	/**
	 * datepicker that allows user to pick start date. Used for Search.
	 */
	@FXML DatePicker startdate;
	/**
	 * datepicker that allows user to pick end date. Used for Search.
	 */
	@FXML DatePicker enddate;
	
	/**
	 * ObservableList that is used to set content for listview.
	 */
	private ObservableList<PhotoAlbum> albums;
	/**
	 * List of albums that the user that the has on disk.
	 */
	private ArrayList<PhotoAlbum> hardalbums;
	
	/**
	 * User that is in currently logged into the non-admin subsystem.
	 */
	private User user;
	/**
	 * Loads the user albums onto the listview
	 * 
	 * @param user		User that is currently logged in
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
		albuminfo.setEditable(false);
	}
	/**
	 * <p>When a user selects an item from the list view, the album information is
	 * displayed in the TextArea albuminfo.
	 */
	private void showItem() {
		// TODO Auto-generated method stub
		albuminfo.setText(listView.getSelectionModel().getSelectedItem().getInfo());
	}
	/**
	 * Creates, renames, or deletes and album
	 * 
	 * <p>Create: the user enters a name into the TextField album and presses the create button.
	 * The user cannot enter an empty album name or duplicate album name.
	 * 
	 * <p>Rename: the user selects an album from the listview, types in a new album name, and presses rename.
	 * The user cannot have two albums of the same name. 
	 * 
	 * <p>Delete: the user selects an album from the listview and presses delete to remove the album from his/her disk.
	 * 
	 * @param e event from button create, rename, or delete.
	 */
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
	/**
	 * Allows user to logout from nonadmin subsystem and return to login page.
	 * 
	 * 
	 * @param e event from the logout button
	 */
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
	/**
	 * Redirects the page to the contents of the selected album.
	 * 
	 * @param e event from the open button.
	 */
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
			alert.setContentText("A photo has an invalid file path");
			alert.showAndWait();
		}catch(Exception z){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("User");
			alert.setHeaderText("ERROR!");
			alert.setContentText("Application error: mercy on my grade.");
			alert.showAndWait();
		}
	}
	/**
	 * Searches for photos based on tag and range date queries.
	 * 
	 * <p> The user has the option to search by tag, range dates, or both tag and range dates.
	 * The search will open a new window containing the photos that match the queries. If no
	 * photos were found then the user be notified by an Alert message.
	 * 
	 * @param e	event from the Search button
	 */
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
					Stage currStage = (Stage)((Node)e.getSource()).getScene().getWindow();
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
				}
			}
		}else if((tagtype.getText().trim().isEmpty() && tagvalue.getText().trim().isEmpty()) && (startdate.getValue() == null && enddate.getValue() == null)){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("User");
			alert.setHeaderText("ERROR!");
			alert.setContentText("Search error: empty fields");
			alert.showAndWait();
		}else if(tagtype.getText().trim().isEmpty() && tagvalue.getText().trim().isEmpty()){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("User");
			alert.setHeaderText("ERROR!");
			alert.setContentText("Search error: must enter both a tag name and tag value");
			alert.showAndWait();
		}else if(startdate.getValue() == null && enddate.getValue() == null){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("User");
			alert.setHeaderText("ERROR!");
			alert.setContentText("Search error: both start date and end date must be specified");
			alert.showAndWait();
		}
	}
}
