package net.mysocio.ui.management;


public interface ICommandInterpreter {
	public String executeCommand(String command);

	public String getCommandResponseType(String command);
}
