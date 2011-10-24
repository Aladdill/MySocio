package net.mysocio.data;

import java.util.List;

public interface ITagedObject extends ISocioObject {
	public List<SocioTag> getTags();
	
	/**
	 * This method should return list of tags, that should be created, when object is created
	 * @return
	 */
	public List<SocioTag> getDefaultTags();
}
