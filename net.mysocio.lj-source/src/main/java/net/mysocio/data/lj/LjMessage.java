/**
 * 
 */
package net.mysocio.data.lj;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.data.messages.rss.RssMessage;


/**
 * @author gurfinke
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class LjMessage extends RssMessage{
}
