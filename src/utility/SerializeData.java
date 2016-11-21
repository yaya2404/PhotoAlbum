package utility;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class SerializeData {
	
	static final String file = "users";
	private static ArrayList<User> users;
	
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
				/**
				 * The Image object is not serializable so I create the Image object when reading serialized data.
				 */
				for(int i = 0; i < users.size(); i++){
					for(int j = 0; j < users.get(i).getAlbums().size(); j++){
						for(int k = 0; k < users.get(i).getAlbums().get(j).getPhotos().size(); k++){
							users.get(i).getAlbums().get(j).getPhotos().get(k).setImage();
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static ArrayList<User> getData(){
		return users;
	}
	public static void writeData(){
		try{
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(users);
			oos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
