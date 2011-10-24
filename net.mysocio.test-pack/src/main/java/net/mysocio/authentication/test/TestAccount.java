/**
 * 
 */
package net.mysocio.authentication.test;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.connection.readers.ISource;
import net.mysocio.data.SocioTag;
import net.mysocio.data.accounts.Account;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class TestAccount extends Account {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8260188677369879519L;
	private static final String ACCOUNT_TYPE = "test";

	/* (non-Javadoc)
	 * @see net.mysocio.data.accounts.Account#getAccountType()
	 */
	@Override
	public String getAccountType() {
		return ACCOUNT_TYPE;
	}

	@Override
	public List<ISource> getSources() {
		List<ISource> sources = new ArrayList<ISource>();
		TestSource ts = new TestSource();
		ts.setName("Test account");
		ts.setUrl("testSourceId");
		sources.add(ts);
		TestSource ts1 = new TestSource();
		ts1.setName("Test account1");
		ts1.setUrl("testSourceId1");
		sources.add(ts1);
		return sources;
	}
	
	public List<SocioTag> getDefaultTags() {
		List<SocioTag> tags = new ArrayList<SocioTag>();
		SocioTag tag = new SocioTag();
		tag.setValue("test.tag");
		tag.setIconType("test.icon.general");
		tags.add(tag);
		SocioTag accTag = new SocioTag();
		accTag.setValue(getUserName());
		accTag.setIconType("test.icon.account");
		tags.add(accTag);
		return tags;
	}

}
