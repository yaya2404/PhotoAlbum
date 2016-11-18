package utility;

import java.io.Serializable;
import java.util.ArrayList;

public class PhotoAlbum implements Serializable{
	
	private String name;
	private ArrayList<Photo> photos;
	private Photo latest;
	private Photo earliest;
	
	public PhotoAlbum(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
		photos = new ArrayList<Photo>();
	}
	public String toString(){
		return this.name;
	}
	public int getSize(){
		return this.photos.size();
	}
	public void setName(String name){
		this.name = name;
	}
	public String getInfo(){
		return "Name: " + this.name + "\n" + "Number of Photos: " + photos.size() + "\n" + "Earliest Photo: test\n" + "Latest Photo: test"; 
	}
}
