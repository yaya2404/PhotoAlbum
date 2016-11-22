package utility;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javafx.scene.image.Image;
/**
 * @author Matthew Ya
 * @author Taehee Lee
 */
public class PhotoAlbum implements Serializable{
	
	/**
	 * Name of the PhotoAlbum object.
	 */
	private String name;
	/**
	 * ArrayList of Photo objects
	 */
	private ArrayList<Photo> photos;
	
	public PhotoAlbum(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
		photos = new ArrayList<Photo>();
	}
	/**
	 * @return	returns the name of the PhotoAlbum object.
	 */
	public String toString(){
		return this.name;
	}
	/**
	 * 
	 * @return returns the number of photos inside the PhotoAlbum object.
	 */
	public int getSize(){
		return this.photos.size();
	}
	/**
	 * Renames the PhotoAlbum object.
	 * 
	 * @param name	the new name of the PhotoAlbum.
	 */
	public void setName(String name){
		this.name = name;
	}
	/**
	 * This is used to display the PhotoAlbum object's information.
	 * 
	 * @return	returns the name, number of photos, oldest photo, and range of dates in a String.
	 */
	public String getInfo(){
		if(photos.size() == 0){
			return "Name: " + this.name + "\n" + "Number of Photos: " + photos.size() + "\n"; 
		}
		return "Name: " + this.name + "\n" + "Number of Photos: " + photos.size() + "\n" + "Oldest Photo: " + new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(getLatestPhoto().getDate()) + "\n" + "Range of Dates: " + new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(getEarliestPhoto().getDate()) + " to " + new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(getLatestPhoto().getDate()) + "\n"; 
	}
	/**
	 * 
	 * @return	returns the ArrayList of Photo objects in the PhotoAlbum object.
	 */
	public ArrayList<Photo> getPhotos(){
		return this.photos;
	}
	/**
	 * Searches the PhotoAlbum object for the Photo object that contains the Image object.
	 * 
	 * 
	 * @param image		the image that is being queried.
	 * @return		returns the index of the Photo object that contains the image. -1 if the Image object is not found.
	 */
	public int getIndexOfPhoto(Image image){
		for(int i = 0; i < this.photos.size(); i++){
			if(this.photos.get(i).getImage().equals(image)){
				return i;
			}
		}
		return -1;
	}
	/**
	 * Obtains the Photo object with the latest date.
	 * 
	 * @return		returns the Photo object with the latest date.
	 */
	private Photo getLatestPhoto(){
		Photo latest = this.photos.get(0);
		for(int i = 0; i < this.photos.size(); i++){
			if(latest.getDate().before(this.photos.get(i).getDate())){
				latest = this.photos.get(i);
			}
		}
		return latest;
	}
	/**
	 * Obtains the Photo object with the earliest date.
	 * 
	 * @return		returns the Photo object with the earliest date.
	 */
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
