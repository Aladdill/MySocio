package net.mysocio.ui.management;

import java.util.Map;

import net.mysocio.data.SocioUser;


public interface ICommandExecutor {
	public String execute(SocioUser user, Map<String, String> parameters);
}
