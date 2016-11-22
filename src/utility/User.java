package utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class User implements Serializable{
	
	private String name;
	private ArrayList<PhotoAlbum> Albums;
	
	public User(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.Albums = new ArrayList<PhotoAlbum>();
	}
	public String toString(){
		return this.name;
	}
	public PhotoAlbum getAlbum(String name){
		for(int i = 0; i < this.Albums.size(); i++){
			if(this.Albums.get(i).toString().compareToIgnoreCase(name) == 0){
				return this.Albums.get(i);
			}
		}
		return null;
	}
	public ArrayList<PhotoAlbum> getAlbums(){
		return this.Albums;
	}
	public void addAlbum(PhotoAlbum album){
		this.Albums.add(album);
	}
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
