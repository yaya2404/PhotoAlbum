package utility;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.scene.image.Image;

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
	public ArrayList<Photo> getPhotos(){
		return this.photos;
	}
	public int getIndexOfPhoto(Image image){
		for(int i = 0; i < this.photos.size(); i++){
			if(this.photos.get(i).getImage().equals(image)){
				return i;
			}
		}
		return -1;
	}
}
