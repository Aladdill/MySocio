/**
 * 
 */
package net.mysocio.data;

import com.github.jmkgreen.morphia.annotations.Embedded;
import com.github.jmkgreen.morphia.annotations.Entity;

/**
 * @author Aladdin
 *
 */
@Entity("routes")
public class SocioRoute extends SocioObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7294367182716078765L;
	private String from;
	@Embedded
	private AbstractProcessor processor;
	private String to;
	private Long delay = 0l;
	private boolean autoStartup = true;
	private String camelRouteId;
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public AbstractProcessor getProcessor() {
		return processor;
	}
	public void setProcessor(AbstractProcessor processor) {
		this.processor = processor;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public Long getDelay() {
		return delay;
	}
	public void setDelay(Long delay) {
		this.delay = delay;
	}
	public boolean isAutoStartup() {
		return autoStartup;
	}
	public void setAutoStartup(boolean autoStartup) {
		this.autoStartup = autoStartup;
	}
	public String getCamelRouteId() {
		return camelRouteId;
	}
	public void setCamelRouteId(String camelRouteId) {
		this.camelRouteId = camelRouteId;
	} 
}
