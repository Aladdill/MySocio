/**
 * 
 */
package net.mysocio.data;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;



/**
 * @author Aladdin
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class SocioContact extends Contact implements IContact {
}
