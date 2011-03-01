/**
 * 
 */
package net.mysocio.ui.management;

import net.mysocio.data.IConnectionData;
import net.socio.ui.managers.basic.DefaultCommandIterpreter;

/**
 * @author Aladdin
 *
 */
public class CommandIterpreterFactory {
	static public ICommandInterpreter getCommandInterpreter(IConnectionData connectionManager){
		return new DefaultCommandIterpreter(connectionManager);
	}
}
