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
	
	static final String dir = "admin";
	static final String file = "users";
	private static ObservableList<User> users;
	
	public static void initData(){
		ObjectInputStream ois;
		try {
			File out = new File(dir + File.separator + file);
			if(!out.exists()){
		        ObjectOutputStream newout = new ObjectOutputStream(new FileOutputStream(dir + File.separator + file));
		        users = FXCollections.observableArrayList();
		        newout.close();
			}else{
				ois = new ObjectInputStream(new FileInputStream(dir + File.separator + file));
				users = FXCollections.observableArrayList((ArrayList<User>)ois.readObject());
				ois.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static ObservableList<User> getData(){
		return users;
	}
	public static void writeData(){
		try{
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dir + File.separator + file));
			oos.writeObject(new ArrayList<User>(users));
			oos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
