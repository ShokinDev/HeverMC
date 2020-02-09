package br.com.hevermc.commons.bungee.command.common.loader;
import com.google.common.reflect.ClassPath;

import br.com.hevermc.commons.bungee.Commons;
import br.com.hevermc.commons.bungee.command.common.HeverCommand;
import net.md_5.bungee.api.plugin.Command;

public class CommandLoader {

	public CommandLoader() {
		registerAllCommands();
	}

	public void registerAllCommands() {
		try {
			ClassPath cp = ClassPath.from(getClass().getClassLoader());
			cp.getTopLevelClasses("br.com.hevermc.commons.bungee.command").stream()
					.filter(obj -> obj.load().getSuperclass().equals(HeverCommand.class)).forEach(cmds -> {
						try {
							registerCommand((Command) cmds.load().newInstance());
						} catch (InstantiationException | IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void registerCommand(Command command) {
		Commons.getInstance().getProxy().getPluginManager().registerCommand(Commons.getInstance(), command);
	}

}
