package net.mysocio.data;

import java.io.Serializable;

import org.apache.camel.Processor;

public abstract class AbstractProcessor implements Processor, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2076814374967849891L;
	
	protected String to;
	
	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
}
