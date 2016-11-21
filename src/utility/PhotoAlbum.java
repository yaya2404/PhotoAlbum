package utility;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javafx.scene.image.Image;

public class PhotoAlbum implements Serializable{
	
	private String name;
	private ArrayList<Photo> photos;
	
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
		if(photos.size() == 0){
			return "Name: " + this.name + "\n" + "Number of Photos: " + photos.size() + "\n"; 
		}
		return "Name: " + this.name + "\n" + "Number of Photos: " + photos.size() + "\n" + "Oldest Photo: " + new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(getLatestPhoto().getDate()) + "\n" + "Range of Dates: " + new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(getEarliestPhoto().getDate()) + "-" + new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(getLatestPhoto().getDate()) + "\n"; 
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
	private Photo getLatestPhoto(){
		Photo latest = this.photos.get(0);
		for(int i = 0; i < this.photos.size(); i++){
			if(latest.getDate().before(this.photos.get(i).getDate())){
				latest = this.photos.get(i);
			}
		}
		return latest;
	}
	private Photo getEarliestPhoto(){
		Photo earliest = this.photos.get(0);
		for(int i = 0; i < this.photos.size(); i++){
			if(earliest.getDate().after(this.photos.get(i).getDate())){
				earliest = this.photos.get(i);
			}
		}
		return earliest;
	}
}
