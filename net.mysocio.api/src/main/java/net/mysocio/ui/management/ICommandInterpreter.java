package net.mysocio.ui.management;

import java.util.Map;

public interface ICommandInterpreter {
	public String executeCommand(String command, Map parameters);

	public abstract String getCommandResponseType(String command);
}
