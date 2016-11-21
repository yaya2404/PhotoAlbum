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

public class Photo implements Serializable{
	
	private transient Image image;
	private File file;
	private Date date;
	private ArrayList<Tag> tags;
	private Calendar calendar;
	private String caption;
	
	public Photo(File file) throws IllegalArgumentException{
		// TODO Auto-generated constructor stub
		this.calendar = new GregorianCalendar();
		this.calendar.set(Calendar.MILLISECOND, 0);
		this.file = file;
		this.tags = new ArrayList<Tag>();
		this.date = calendar.getTime();
		this.caption = "";
	}
	public ArrayList<Tag> getTags(){
		return this.tags;
	}
	public void setCaption(String text){
		this.caption = text;
	}
	public String getCaption(){
		return this.caption;
	}
	public String getInfo(){
		StringBuilder mes = new StringBuilder();
		mes.append("Caption: " + this.caption + "\n");
		//append date
		//mes.append("");
		mes.append("Tags: \n" );
		for(int i = 0; i < tags.size(); i++){
			mes.append(tags.get(i).toString() + "\n");
		}
		return mes.toString();
	}
	public File getFile(){
		return this.file;
	}
	public Image getImage(){
		return this.image;
	}
	public void setImage() throws FileNotFoundException{
		this.image = new Image(new FileInputStream(this.file));
	}
}
