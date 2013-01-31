package net.mysocio.data;

import org.apache.camel.Processor;

import com.google.code.morphia.annotations.Entity;

@Entity("processors")
public abstract class AbstractProcessor extends SocioObject implements Processor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1749103184863281301L;
}
