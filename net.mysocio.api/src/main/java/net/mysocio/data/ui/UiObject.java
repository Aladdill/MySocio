/**
 * 
 */
package net.mysocio.data.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.PersistenceCapable;

import net.mysocio.data.NamedObject;

/**
 * @author Aladdin
 *
 */
@PersistenceCapable
@Inheritance(customStrategy="complete-table")
public abstract class UiObject extends NamedObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = -306328178179491034L;
	public static final String TAG_START = "<<";
	public static final String TAG_END = ">>";
	private String category;
	private Map<String, UiObject> innerObjects = new HashMap<String, UiObject>();
	private List<String> textLabels = new ArrayList<String>();
	private String htmlTemplate = "";

	public String getObjectTag(int index){
		return getTag(this.category + "." + index);
	}
	public String getTag(String tagName){
		return UiObject.TAG_START + tagName + TAG_END;
	}
	public void setInnerObjects(Map<String, UiObject> innerObjects) {
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

	public String getCategory() {
		return category;
	}

	public Map<String, UiObject> getInnerUiObjects() {
		return innerObjects;
	}

	public List<String> getInnerTextLabels() {
		return textLabels;
	}

	public String getHtmlTemplate() {return this.htmlTemplate;}
	
	public String getPageFile(){return null;}
	
	public void setHtmlTemplate(String htmlTemplate) {
		this.htmlTemplate = htmlTemplate;
	}
}
