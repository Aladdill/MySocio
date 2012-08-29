/**
 * 
 */
package net.mysocio.data;

import net.mysocio.data.accounts.Account;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Reference;

/**
 * @author Aladdin
 * Although User is subclass of MySocio object, tags list is private list of tags usable by this user and not object tags.
 */
@Entity("my_socio_users")
public class SocioUser extends NamedObject{
	public static final String ASCENDING_ORDER = "ascending";
	public static final String DESCENDING_ORDER = "descending";
	public static final String ALL_TAGS = "All";
	/**
	 * 
	 */
	private static final long serialVersionUID = -2886854604233072581L;
	@Reference
	private Account mainAccount;
	private String order = ASCENDING_ORDER;
	private int range = 25;
	private String selectedTag = ALL_TAGS;
	private String locale;
	
	public String getSelectedTag() {
		return selectedTag;
	}

	public void setSelectedTag(String selectedTag) {
		this.selectedTag = selectedTag;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public Account getMainAccount() {
		return mainAccount;
	}

	public void setMainAccount(Account mainAccount) {
		this.mainAccount = mainAccount;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}
}
