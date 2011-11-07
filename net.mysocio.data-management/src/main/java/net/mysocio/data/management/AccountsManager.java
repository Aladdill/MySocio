/**
 * 
 */
package net.mysocio.data.management;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.mysocio.data.IAuthenticationManager;
import net.mysocio.data.IConnectionData;
import net.mysocio.data.accounts.Account;

/**
 * @author Aladdin
 *
 */
public class AccountsManager {
	public static final String IDENTIFIER = "identifier";
	private static final AccountsManager instance = new AccountsManager();
	private static Map<String, IAuthenticationManager> accounts = new HashMap<String, IAuthenticationManager>();
	private AccountsManager(){}
	public static AccountsManager getInstance() {
		return instance;
	}
	public void addAccount(String identifier, IAuthenticationManager authenticationManager){
		accounts.put(identifier, authenticationManager);
	}
	
	public IAuthenticationManager getAccountAuthManager(String identifier){
		return accounts.get(identifier);
	}
	
	public Set<String> getAccounts(){
		return accounts.keySet();
	}
	
	public String getAccountRequestUrl(String identifier) throws InvalidNetworkException{
		IAuthenticationManager authenticationManager = accounts.get(identifier);
		if (authenticationManager == null){
			throw new InvalidNetworkException(identifier);
		}
		return authenticationManager.getRequestUrl();
	}
	
	public Account getAccount(IConnectionData connectionData) throws Exception{
		String identifier = connectionData.getSessionAttribute(IDENTIFIER);
		if (identifier == null){
			throw new InvalidNetworkException(identifier);
		}
		IAuthenticationManager authenticationManager = accounts.get(identifier);
		if (authenticationManager == null){
			throw new InvalidNetworkException(identifier);
		}
		return authenticationManager.getAccount(connectionData);
	}
}
