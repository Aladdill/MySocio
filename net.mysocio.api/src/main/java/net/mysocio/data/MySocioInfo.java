/**
 * 
 */
package net.mysocio.data;

import com.github.jmkgreen.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("mysocio_info")
public class MySocioInfo extends SocioObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7226285968410894056L;

	private String event;
	private String info;
	private String date;
	
	public MySocioInfo(){}
	
	public MySocioInfo(String event, String info, String date) {
		super();
		this.event = event;
		this.info = info;
		this.date = date;
	}
	
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
