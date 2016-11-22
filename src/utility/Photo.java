package utility;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;
/**
 * @author Matthew Ya
 * @author Taehee Lee
 */
public class Photo implements Serializable{

	/**
	 * Image object where the image is created stored.
	 */
	private transient Image image;
	/**
	 * File that contains image file.
	 */
	private File file;
	/**
	 * Contains the date of the photo. This changes when the photo object
	 * adds/removes a tag or captions/recaptions a photo.
	 */
	private Date date;
	/**
	 * An ArrayList of tags.
	 */
	private ArrayList<Tag> tags;
	/**
	 * String that contains the caption of the image.
	 */
	private String caption;
	/**
	 * Name of the image. In this case, it's the name of the file.
	 */
	private String name;
	
	public Photo(File file) throws IllegalArgumentException{
		// TODO Auto-generated constructor stub
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(Calendar.MILLISECOND, 0);
		this.date = calendar.getTime();
		this.file = file;
		this.tags = new ArrayList<Tag>();
		this.caption = "";
		this.name = file.getName();
	}
	/**
	 * 
	 * @return	returns the ArrayList of tags in this photo
	 */
	public ArrayList<Tag> getTags(){
		return this.tags;
	}
	/**
	 * adds a new tag to the photo
	 * 
	 * @param newtag	the new tag to be added to the photo
	 */
	public void addTag(Tag newtag){
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(Calendar.MILLISECOND, 0);
		this.date = calendar.getTime();
		this.tags.add(newtag);
	}
	/**
	 * Removes a tag from the photo
	 * 
	 * @param index		index of tag to be removed
	 */
	public void removeTag(int index){
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(Calendar.MILLISECOND, 0);
		this.date = calendar.getTime();
		this.tags.remove(index);
	}
	/**
	 * captions/recaptions the photo
	 * 
	 * @param text	the new text to be captioned onto the photo
	 */
	public void setCaption(String text){
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(Calendar.MILLISECOND, 0);
		this.date = calendar.getTime();
		this.caption = text;
	}
	/**
	 * 
	 * @return	returns the caption of this photo
	 */
	public String getCaption(){
		return this.caption;
	}
	/**
	 * 
	 * @return	returns the name of this photo
	 */
	public String getName(){
		return this.name;
	}
	/**
	 * 
	 * @return	returns the caption and date of this photo.
	 */
	public String getInfo(){
		StringBuilder mes = new StringBuilder();
		mes.append("Caption: " + this.caption + "\n");
		//append date
		mes.append("Date: " + new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(this.date));
		return mes.toString();
	}
	/**
	 * 
	 * @return	returns the image File associated with this photo.
	 */
	public File getFile(){
		return this.file;
	}
	/**
	 * 
	 * @return	returns the image associated with this photo.
	 */
	public Image getImage(){
		return this.image;
	}
	/**
	 * 
	 * @return	returns the date associated with this photo.
	 */
	public Date getDate(){
		return this.date;
	}
	/**
	 * 
	 * Because the image file is not serializable, the Image object is manually created.
	 * 
	 * @throws FileNotFoundException	when the file cannot be located on the users disk.
	 */
	public void setImage() throws FileNotFoundException{
		this.image = new Image(new FileInputStream(this.file));
	}
	/**
	 * 
	 * @param tag	the tag that is being queried
	 * @return	true if the photo contains the tag, false if not.
	 */
	public boolean containsTag(Tag tag){
		for(int i = 0; i < tags.size(); i++){
			if(tags.get(i).equals(tag)){
				return true;
			}
		}
		return false;
	}
}
