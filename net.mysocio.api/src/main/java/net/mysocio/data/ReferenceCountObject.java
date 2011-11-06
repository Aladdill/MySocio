/**
 * 
 */
package net.mysocio.data;

import java.io.Serializable;

/**
 * @author Aladdin
 *
 */
public class ReferenceCountObject<T> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8458960430715532701L;
	private int counter;
	private T object;
	public T getObject() {
		return object;
	}
	public void setObject(T object) {
		this.object = object;
	}
	public int getCounter() {
		return counter;
	}
	public void setCounter(int counter) {
		this.counter = counter;
	}
}
