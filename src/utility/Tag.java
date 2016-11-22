package utility;

import java.io.Serializable;

public class Tag implements Serializable{
	
	private String name;
	private String value;

	public Tag(String name, String value) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.value = value;
	}
	public String getName(){
		return this.name;
	}
	public String getValue(){
		return this.value;
	}
	public boolean equals(Tag tag){
		if((tag.getName().compareToIgnoreCase(this.name) == 0) && (tag.getValue().compareToIgnoreCase(this.value) == 0)){
			return true;
		}
		return false;
	}
	public String toString(){
		return this.name+ ": " + this.value;
	}
}
