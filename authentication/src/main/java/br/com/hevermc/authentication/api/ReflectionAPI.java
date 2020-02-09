package br.com.hevermc.authentication.api;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class ReflectionAPI {

	public static Class<?> getNMSClass(String name) {

		String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		try {
			return Class.forName("net.minecraft.server." + version + "." + name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void sendPacket(Player player, Object packet) {
		try {
			Object handle = player.getClass().getMethod("getHandle", new Class[0]).invoke(player, new Object[0]);
			Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
			playerConnection.getClass().getMethod("sendPacket", new Class[] { getNMSClass("Packet") })
					.invoke(playerConnection, new Object[] { packet });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void setField(Object packet, Field field, Object value) {
		field.setAccessible(true);
		try {
			field.set(packet, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		field.setAccessible(!field.isAccessible());
	}

	private static Field getField(Class<?> classs, String fieldname) {
		try {
			return classs.getDeclaredField(fieldname);
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void tag(Player p, String prefix, String suffix, String ordem) {
		try {

			String name = UUID.randomUUID().toString().substring(0, 15);
			PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();
			Class<? extends PacketPlayOutScoreboardTeam> clas = packet.getClass();
			Field team_name = getField(clas, "a");
			Field display_name = getField(clas, "b");
			Field prefix2 = getField(clas, "c");
			Field suffix2 = getField(clas, "d");
			Field members = getField(clas, "g");
			Field param_int = getField(clas, "h");
			Field pack_option = getField(clas, "i");
			setField(packet, team_name, String.valueOf(ordem + name));
			setField(packet, display_name, p.getName());
			setField(packet, prefix2, prefix);
			setField(packet, suffix2, suffix);
			setField(packet, members, Arrays.asList(p.getName()));
			setField(packet, param_int, 0);
			setField(packet, pack_option, 1);
			p.setDisplayName(prefix + p.getName());

			Bukkit.getOnlinePlayers().forEach(ps -> sendPacket(ps, packet));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void tag(Player p, Player topacket, String prefix, String suffix, String ordem) {
		try {

			String name = UUID.randomUUID().toString().substring(0, 15);
			PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();
			Class<? extends PacketPlayOutScoreboardTeam> clas = packet.getClass();
			Field team_name = getField(clas, "a");
			Field display_name = getField(clas, "b");
			Field prefix2 = getField(clas, "c");
			Field suffix2 = getField(clas, "d");
			Field members = getField(clas, "g");
			Field param_int = getField(clas, "h");
			Field pack_option = getField(clas, "i");
			setField(packet, team_name, String.valueOf(ordem + name));
			setField(packet, display_name, p.getName());
			setField(packet, prefix2, prefix);
			setField(packet, suffix2, suffix);
			setField(packet, members, Arrays.asList(p.getName()));
			setField(packet, param_int, 0);
			setField(packet, pack_option, 1);

			sendPacket(topacket, packet);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void tab(Player p, String header, String footer) {
		try {
			PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
			Class<? extends PacketPlayOutPlayerListHeaderFooter> clas = packet.getClass();
			Field field_header = getField(clas, "a");
			Field field_footer = getField(clas, "b");

			Object obj_header = new ChatComponentText(header);
			Object obj_footer = new ChatComponentText(footer);

			setField(packet, field_header, obj_header);
			setField(packet, field_footer, obj_footer);

			sendPacket(p, packet);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
		CraftPlayer craftplayer = (CraftPlayer) player;
		PlayerConnection connection = craftplayer.getHandle().playerConnection;
		IChatBaseComponent titleJSON = ChatSerializer.a("{'text': '" + title + "'}");
		IChatBaseComponent subtitleJSON = ChatSerializer.a("{'text': '" + subtitle + "'}");
		PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(EnumTitleAction.TITLE, titleJSON, fadeIn, stay,
				fadeOut);
		PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, subtitleJSON);
		connection.sendPacket(titlePacket);
		connection.sendPacket(subtitlePacket);
	}

	public static int getPing(Player p) {
		String serverName = Bukkit.getServer().getClass().getPackage().getName();
		String serverVersion = serverName.substring(serverName.lastIndexOf(".") + 1, serverName.length());
		try {
			Class<?> CPClass = Class.forName("org.bukkit.craftbukkit." + serverVersion + ".entity.CraftPlayer");
			Object CraftPlayer = CPClass.cast(p);
			Method getHandle = CraftPlayer.getClass().getMethod("getHandle", (Class<?>[]) new Class[0]);
			Object EntityPlayer = getHandle.invoke(CraftPlayer, new Object[0]);
			Field ping = EntityPlayer.getClass().getDeclaredField("ping");
			return ping.getInt(EntityPlayer);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}