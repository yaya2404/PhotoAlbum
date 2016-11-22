package utility;

import java.io.Serializable;
/**
 * @author Matthew Ya
 * @author Taehee Lee
 */
public class Tag implements Serializable{
	
	/**
	 * Name of the tag
	 */
	private String name;
	/**
	 * Value of the tag
	 */
	private String value;

	public Tag(String name, String value) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.value = value;
	}
	/**
	 * 
	 * @return the name of the tag
	 */
	public String getName(){
		return this.name;
	}
	/**
	 * 
	 * @return	the value of the tag
	 */
	public String getValue(){
		return this.value;
	}
	/**
	 * Compares two tags to determine if the tag name and tag value are the same.
	 * 
	 * 
	 * @param tag	tag to be queried
	 * @return		true if tags are the same. false if not.
	 */
	public boolean equals(Tag tag){
		if((tag.getName().compareToIgnoreCase(this.name) == 0) && (tag.getValue().compareToIgnoreCase(this.value) == 0)){
			return true;
		}
		return false;
	}
	/**
	 * Returns tag name and tag value in the format: "name: value"
	 */
	public String toString(){
		return this.name+ ": " + this.value;
	}
}
