package utility;

import java.io.Serializable;
import java.util.ArrayList;

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
}