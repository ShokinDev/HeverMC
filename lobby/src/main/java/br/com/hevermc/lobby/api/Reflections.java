package br.com.hevermc.lobby.api;

import java.lang.reflect.Field;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Reflections {
	public void setValue(Object obj, String name, Object value) {
		try {
			Field field = obj.getClass().getDeclaredField(name);
			field.setAccessible(true);
			field.set(obj, value);
		} catch (Exception exception) {
		}
	}

	public Object getValue(Object obj, String name) {
		try {
			Field field = obj.getClass().getDeclaredField(name);
			field.setAccessible(true);
			return field.get(obj);
		} catch (Exception exception) {
			return null;
		}
	}

	public void sendPacket(Packet<?> packet, Player player) {
		(((CraftPlayer) player).getHandle()).playerConnection.sendPacket(packet);
	}

	public void sendPacket(Packet<?> packet) {
		for (Player player : Bukkit.getOnlinePlayers())
			sendPacket(packet, player);
	}
}
