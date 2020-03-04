package br.com.hevermc.authentication.command.commons.loader;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;

import com.google.common.reflect.ClassPath;

import br.com.hevermc.authentication.command.commons.HeverCommand;;

public class CommandLoader {

	CommandMap cmdMap;

	public CommandLoader() {
		registerAllCommands();
	}

	public void registerAllCommands() {
		try {
			ClassPath cp = ClassPath.from(getClass().getClassLoader());
			cp.getTopLevelClasses("br.com.hevermc.authentication.command").stream()
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

	private void registerCommand(Command cmd) {

		try {
			Class<?> craftServer = Class.forName("org.bukkit.craftbukkit."
					.concat(Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3])
					.concat(".CraftServer"));
			Field fieldMap = craftServer.getDeclaredField("commandMap");
			fieldMap.setAccessible(true);
			this.cmdMap = (CommandMap) fieldMap.get(Bukkit.getServer());
			cmdMap.register(cmd.getName(), cmd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
