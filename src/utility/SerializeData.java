package utility;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * @author Matthew Ya
 * @author Taehee Lee
 */
public class SerializeData {
	
	/**
	 * The file where the ArrayList users is stored and read.
	 */
	static final String file = "users";
	/**
	 * The ArrayList of users.
	 */
	private static ArrayList<User> users;
	
	
	/**
	 * initData is called when the application starts. The serialized items are read from the file and stored
	 * into the users ArrayList. Because the Image object is not serializable, the Image objects are created
	 * with their serialized File object.
	 */
	public static void initData(){
		try {
			File out = new File(file);
			if(!out.exists()){
		        ObjectOutputStream newout = new ObjectOutputStream(new FileOutputStream(file));
		        users = new ArrayList<User>();
		        newout.writeObject(users);
		        newout.close();
			}else{
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
				users = (ArrayList<User>)ois.readObject();
				ois.close();
				
				//The Image object is not serializable so I create the Image object when reading serialized data.
				for(int i = 0; i < users.size(); i++){
					for(int j = 0; j < users.get(i).getAlbums().size(); j++){
						for(int k = 0; k < users.get(i).getAlbums().get(j).getPhotos().size(); k++){
							users.get(i).getAlbums().get(j).getPhotos().get(k).setImage();
						}
					}
				}
			}
		}catch(Exception e){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("initData");
			alert.setHeaderText("ERROR!");
			alert.setContentText("Application error: mercy on my grade.");
			alert.showAndWait();
		}
	}
	/**
	 * 
	 * @return	The ArrayList of users that was read from the file.
	 */
	public static ArrayList<User> getData(){
		return users;
	}
	/**
	 * Writes the ArrayList of users into the file.
	 */
	public static void writeData(){
		try{
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(users);
			oos.close();
		}catch(Exception e){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("initData");
			alert.setHeaderText("ERROR!");
			alert.setContentText("Application error: mercy on my grade.");
			alert.showAndWait();
		}
	}
}
