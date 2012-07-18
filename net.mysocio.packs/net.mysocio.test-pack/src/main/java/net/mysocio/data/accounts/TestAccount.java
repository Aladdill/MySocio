/**
 * 
 */
package net.mysocio.data.accounts;

import java.util.ArrayList;
import java.util.List;

import net.mysocio.authentication.test.TestSource;
import net.mysocio.connection.readers.Source;
import net.mysocio.data.SocioTag;

/**
 * @author Aladdin
 *
 */
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
	public List<Source> getSources() {
		List<Source> sources = new ArrayList<Source>();
		TestSource ts = new TestSource();
		ts.setName("Test account");
		ts.setUrl("testSourceId");
		sources.add(ts);
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

	@Override
	public String getIconUrl() {
		return "test.icon.account";
	}

}
