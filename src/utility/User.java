package utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
/**
 * @author Matthew Ya
 * @author Taehee Lee
 */
public class User implements Serializable{
	
	/**
	 * Name of the user
	 */
	private String name;
	/**
	 * ArrayList of PhotoAlbums the user has
	 */
	private ArrayList<PhotoAlbum> Albums;
	
	public User(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.Albums = new ArrayList<PhotoAlbum>();
	}
	/**
	 * returns the name of the user. Used for ListView.
	 */
	public String toString(){
		return this.name;
	}
	/**
	 * 
	 * @param name	the name of the PhotoAlbum object that is being queried
	 * @return		the PhotoAlbum object that contains the name. null if not found.
	 */
	public PhotoAlbum getAlbum(String name){
		for(int i = 0; i < this.Albums.size(); i++){
			if(this.Albums.get(i).toString().compareToIgnoreCase(name) == 0){
				return this.Albums.get(i);
			}
		}
		return null;
	}
	/**
	 * 
	 * @return	the ArrayList of PhotoAlbum objects contained in the user.
	 */
	public ArrayList<PhotoAlbum> getAlbums(){
		return this.Albums;
	}
	/**
	 * adds a new album to the user's photo album collection.
	 * 
	 * @param album		album to be added the user's ArrayList of PhotoAlbum objects.
	 */
	public void addAlbum(PhotoAlbum album){
		this.Albums.add(album);
	}
	/**
	 * Allows a user to search for photos by a date range.
	 * 
	 * 
	 * @param start		the start date
	 * @param end		the end date
	 * @return		returns an ArrayList of Photo objects that fall within the start and end dates.
	 */
	public ArrayList<Photo> searchByDate(Date start, Date end){
		ArrayList<Photo> query = new ArrayList<Photo>();
		for(int i = 0; i < Albums.size(); i++){
			for(int j = 0; j < Albums.get(i).getPhotos().size(); j++){
				if(Albums.get(i).getPhotos().get(j).getDate().after(start) && Albums.get(i).getPhotos().get(j).getDate().before(end)){
					query.add(Albums.get(i).getPhotos().get(j));
				}
			}
		}
		return query;
	}
	/**
	 * Allows a user to search for photos by tag
	 * 
	 * 
	 * @param tag	tag that is contained in a photo
	 * @return		returns an ArrayList of Photo objects that contain the tag.
	 */
	public ArrayList<Photo> searchByTag(Tag tag){
		ArrayList<Photo> query = new ArrayList<Photo>();
		for(int i = 0; i < Albums.size(); i++){
			for(int j = 0; j < Albums.get(i).getPhotos().size(); j++){
				if(Albums.get(i).getPhotos().get(j).containsTag(tag)){
					query.add(Albums.get(i).getPhotos().get(j));
				}
			}
		}
		return query;
	}
	
}
