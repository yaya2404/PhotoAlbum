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

public class Photo implements Serializable{
	
	private transient Image image;
	private File file;
	private Date date;
	private ArrayList<Tag> tags;
	private String caption;
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
	public ArrayList<Tag> getTags(){
		return this.tags;
	}
	public void addTag(Tag newtag){
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(Calendar.MILLISECOND, 0);
		this.date = calendar.getTime();
		this.tags.add(newtag);
	}
	public void setCaption(String text){
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(Calendar.MILLISECOND, 0);
		this.date = calendar.getTime();
		this.caption = text;
	}
	public String getCaption(){
		return this.caption;
	}
	public String getName(){
		return this.name;
	}
	public String getInfo(){
		StringBuilder mes = new StringBuilder();
		mes.append("Caption: " + this.caption + "\n");
		//append date
		mes.append("Date: " + new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(this.date));
		return mes.toString();
	}
	public File getFile(){
		return this.file;
	}
	public Image getImage(){
		return this.image;
	}
	public Date getDate(){
		return this.date;
	}
	public void setImage() throws FileNotFoundException{
		this.image = new Image(new FileInputStream(this.file));
	}
	public boolean containsTag(Tag tag){
		for(int i = 0; i < tags.size(); i++){
			if(tags.get(i).equals(tag)){
				return true;
			}
		}
		return false;
	}
}
