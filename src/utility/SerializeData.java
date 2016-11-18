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
	private static ArrayList<User> users;
	
	public static void initData(){
		try {
			File out = new File(dir + File.separator + file);
			if(!out.exists()){
		        ObjectOutputStream newout = new ObjectOutputStream(new FileOutputStream(dir + File.separator + file));
		        users = new ArrayList<User>();
		        newout.close();
			}else{
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dir + File.separator + file));
				users = (ArrayList<User>)ois.readObject();
				ois.close();
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
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dir + File.separator + file));
			oos.writeObject(users);
			oos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
