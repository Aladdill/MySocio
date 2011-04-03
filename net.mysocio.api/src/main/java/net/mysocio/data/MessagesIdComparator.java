/**
 * 
 */
package net.mysocio.data;

import java.util.Comparator;

/**
 * @author Aladdin
 *
 */
public class MessagesIdComparator implements Comparator<IMessage> {
	public int compare(IMessage arg0, IMessage arg1) {
		return arg0.getId().compareTo(arg1.getId());
	}
}
