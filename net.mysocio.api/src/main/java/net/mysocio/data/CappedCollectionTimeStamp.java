package net.mysocio.data;

import com.google.code.morphia.annotations.Entity;

@Entity(value="time_stamps")
public class CappedCollectionTimeStamp extends SocioObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -178352829053819445L;
	private String collection;
	private long timestamp;
	public String getCollection() {
		return collection;
	}
	public void setCollection(String collection) {
		this.collection = collection;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
