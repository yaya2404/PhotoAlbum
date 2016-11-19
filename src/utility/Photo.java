package utility;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Photo implements Serializable{
	
	private Image image;
	private Date date;
	private ArrayList<Tag> tags;
	private Calendar calendar;
	private String caption;
	
	public Photo(String path) {
		// TODO Auto-generated constructor stub
		this.calendar = new GregorianCalendar();
		this.calendar.set(Calendar.MILLISECOND, 0);
		this.image = new Image(path);
		this.tags = new ArrayList<Tag>();
		this.date = calendar.getTime();
		this.caption = "";
	}
	public void setCaption(String text){
		this.caption = text;
	}
	public void getInfo(){
		StringBuilder mes = new StringBuilder();
		mes.append("Caption: " + this.caption + "\n");
		//append date
		//mes.append("");
		mes.append("Tags: \n" );
		for(int i = 0; i < tags.size(); i++){
			mes.append(tags.get(i).toString() + "\n");
		}
	}
}
