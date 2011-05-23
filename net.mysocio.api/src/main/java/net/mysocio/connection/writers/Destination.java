package net.mysocio.connection.writers;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.data.NamedObject;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public abstract class Destination extends NamedObject implements IDestination{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5472185950579096505L;
}
