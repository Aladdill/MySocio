/**
 * 
 */
package net.mysocio.data.accounts;




/**
 * @author Aladdin
 *
 */
public abstract class Oauth2Account extends Account{
	/**
	 * 
	 */
	private static final long serialVersionUID = 766068901313870004L;
	private String token;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
