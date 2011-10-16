/**
 * 
 */
package net.mysocio.data.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.Key;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Value;

import net.mysocio.data.NamedObject;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class UiObject extends NamedObject implements IUiObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -306328178179491034L;
	public static final String TAG_START = "<<";
	public static final String TAG_END = ">>";
	private String category;
	@Join
	@Key(types=String.class)
    @Value(types=UiObject.class)
	private Map<String, IUiObject> innerObjects = new HashMap<String, IUiObject>();
	private List<String> textLabels = new ArrayList<String>();
	private String htmlTemplate = "";

	public String getObjectTag(int index){
		return getTag(this.category + "." + index);
	}
	public String getTag(String tagName){
		return UiObject.TAG_START + tagName + TAG_END;
	}
	public void setInnerObjects(Map<String, IUiObject> innerObjects) {
		this.innerObjects = innerObjects;
	}

	public void setTextLabels(List<String> textLabels) {
		this.textLabels = textLabels;
	}
	
	public void addInnerObject(UiObject innerObject, int id) {
		this.innerObjects.put(innerObject.getObjectTag(id), innerObject);
	}

	public void addTextLabel(String textLabel) {
		this.textLabels.add(textLabel);
	}

	public void setCategory(String category) {
		this.category = category;
	}

	/* (non-Javadoc)
	 * @see net.mysocio.data.IUiObject#getCategory()
	 */
	public String getCategory() {
		return category;
	}

	/* (non-Javadoc)
	 * @see net.mysocio.data.IUiObject#getInnerUiOjjects()
	 */
	public Map<String, IUiObject> getInnerUiObjects() {
		return innerObjects;
	}

	/* (non-Javadoc)
	 * @see net.mysocio.data.IUiObject#getInnerTextLabels()
	 */
	public List<String> getInnerTextLabels() {
		return textLabels;
	}

	public String getHtmlTemplate() {return this.htmlTemplate;}
	
	public void setHtmlTemplate(String htmlTemplate) {
		this.htmlTemplate = htmlTemplate;
	}
}
