package net.mysocio.connection.writers;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.data.NamedObject;

@PersistenceCapable
@Inheritance(strategy=InheritanceStrategy.SUBCLASS_TABLE)
public abstract class Destination extends NamedObject implements IDestination{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5472185950579096505L;
}
