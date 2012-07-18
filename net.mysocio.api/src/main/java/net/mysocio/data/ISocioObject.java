/**
 * 
 */
package net.mysocio.data;

import java.io.Serializable;

import org.bson.types.ObjectId;



/**
 * @author Aladdin
 *
 */
public interface ISocioObject extends Serializable{
	public ObjectId getId();
}
