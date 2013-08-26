/**
 * 
 */
package net.mysocio.data;

import com.google.code.morphia.annotations.Entity;

/**
 * @author nathan
 *
 */
@Entity("my_socio_user_permissions")
public class UserPermissions extends SocioObject implements IUniqueObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8040208864792847297L;
	private String mail;

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@Override
	public String getUniqueFieldName() {
		return "mail";
	}

	@Override
	public Object getUniqueFieldValue() {
		return getMail();
	}
}
