package net.mysocio.ui.management;

import net.mysocio.data.IConnectionData;


public interface ICommandExecutor {
	public String execute(IConnectionData connectionManager);
}
