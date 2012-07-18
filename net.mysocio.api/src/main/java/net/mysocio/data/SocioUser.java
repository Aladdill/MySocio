/**
 * 
 */
package net.mysocio.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.mysocio.connection.readers.Source;
import net.mysocio.connection.writers.Destination;
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
	private List<Account> accounts = new ArrayList<Account>();
	@Reference
	private Account mainAccount;
	@Reference
	private List<SocioContact> contacts = new ArrayList<SocioContact>();
	@Reference
	private List<Source> sources = new ArrayList<Source>();
	@Reference
	private List<Destination> destinations = new ArrayList<Destination>();
	@Reference
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

	public List<SocioContact> getContacts(){
		return contacts;
	}
	
	/**
	 * @return the accounts
	 */
	public List<Account> getAccounts() {
		return accounts;
	}

	/**
	 * @param accounts the accounts to set
	 */
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	
	public void addAccount(Account account) {
		if (!getAccounts().contains(account)){
			getAccounts().add(account);
		}
	}

	/**
	 * @param contacts the contacts to set
	 */
	public void setContacts(List<SocioContact> contacts) {
		this.contacts = contacts;
	}
	public void addContact(SocioContact contact) {
		this.contacts.add(contact);
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public void addSource(Source source) {
		if (!getSources().contains(source)){
			getSources().add(source);
		}
	}
	
	public Source removeSource(String id) throws CorruptedDataException{
		Iterator<Source> iterator = sources.iterator();
		while (iterator.hasNext()){
			Source source = iterator.next();
			if (source.getId().equals(id)){
				iterator.remove();
				return source;
			}
		}
		return null;
	}

	public void addSources(List<? extends Source> sources) {
		for (Source source : sources) {
			addSource(source);
		}
	}

	public List<Source> getSources() {
		return sources;
	}
	
	public List<Destination> getDestinations() {
		return destinations;
	}
	
	public void addDestination(Destination destination) {
		destinations.add(destination);
	}

	public void addDestinations(List<? extends Destination> destinations) {
		this.destinations.addAll(destinations);		
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
