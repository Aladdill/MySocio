package net.mysocio.ui.management;


public interface ICommandInterpreter {
	public String executeCommand(String command) throws CommandExecutionException;

	public String getCommandResponseType(String command) throws CommandExecutionException;
}
