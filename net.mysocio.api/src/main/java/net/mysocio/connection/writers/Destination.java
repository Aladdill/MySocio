package net.mysocio.connection.writers;

import net.mysocio.data.NamedObject;
import net.mysocio.data.messages.GeneralMessage;

import com.google.code.morphia.annotations.Entity;

@Entity("destinations")
public abstract class Destination extends NamedObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5472185950579096505L;

	public abstract void postMessage(GeneralMessage message);
}
