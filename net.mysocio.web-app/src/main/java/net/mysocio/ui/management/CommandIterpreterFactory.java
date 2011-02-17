/**
 * 
 */
package net.mysocio.ui.management;

import net.mysocio.data.SocioUser;
import net.socio.ui.managers.basic.DefaultCommandIterpreter;

/**
 * @author Aladdin
 *
 */
public class CommandIterpreterFactory {
	static public ICommandInterpreter getCommandInterpreter(SocioUser user){
		return new DefaultCommandIterpreter(user);
	}
}
