package net.mysocio.connection.writers;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import net.mysocio.data.NamedObject;

@Entity(name = "destinations")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Destination extends NamedObject implements IDestinatioin{
}
