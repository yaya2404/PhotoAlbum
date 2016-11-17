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
	public Photo(String path) {
		// TODO Auto-generated constructor stub
		calendar = new GregorianCalendar();
		calendar.set(Calendar.MILLISECOND, 0);
		image = new Image(path);
		tags = new ArrayList<Tag>();
		date = calendar.getTime();
	}

}
