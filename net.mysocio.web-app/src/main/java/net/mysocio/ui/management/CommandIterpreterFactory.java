/**
 * 
 */
package net.mysocio.ui.management;

import net.mysocio.data.IConnectionData;
import net.mysocio.ui.managers.basic.DefaultCommandIterpreter;

/**
 * @author Aladdin
 *
 */
public class CommandIterpreterFactory {
	static public ICommandInterpreter getCommandInterpreter(IConnectionData ConnectionData){
		return new DefaultCommandIterpreter(ConnectionData);
	}
}
