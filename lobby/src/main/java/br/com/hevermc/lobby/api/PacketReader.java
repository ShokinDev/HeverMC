package br.com.hevermc.lobby.api;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.hevermc.commons.bukkit.Commons;
import br.com.hevermc.commons.bukkit.api.ReflectionAPI;
import br.com.hevermc.lobby.Lobby;

public class PacketReader {

	Player player;
	Channel channel;
	ArrayList<Player> buff;

	public PacketReader(Player player) {
		this.buff = new ArrayList<>();
		this.player = player;
	}

	public void inject() {
		CraftPlayer cPlayer = (CraftPlayer) this.player;
		this.channel = (cPlayer.getHandle()).playerConnection.networkManager.channel;
		this.channel.pipeline().addAfter("decoder", "PacketInjector",
				(ChannelHandler) new MessageToMessageDecoder<Packet<?>>() {
					protected void decode(ChannelHandlerContext arg0, Packet<?> packet, List<Object> arg2)
							throws Exception {
						arg2.add(packet);
						PacketReader.this.readPacket(packet);
					}
				});
	}

	public void uninject() {
		if (this.channel.pipeline().get("PacketInjector") != null)
			this.channel.pipeline().remove("PacketInjector");
	}

	public void readPacket(Packet<?> packet) {
		if (packet.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInUseEntity")) {
			int id = ((Integer) getValue(packet, "a")).intValue();
			if (!buff.contains(player)) {
				if (Lobby.getManager().npc.get(player) == id) {
					player.sendMessage("§aVocê está sendo conectado ao §eKitPvP§a!");
					ReflectionAPI.sendTitle(player, "§b§lCONNECT", "§fVocê está se conectando ao §aKitPvP§f!", 10, 10,
							10);
					Commons.getManager().getBungeeChannel().connect(player, "kitpvp");

				}
				new BukkitRunnable() {

					@Override
					public void run() {
						buff.remove(player);
					}
				}.runTaskLater(Lobby.getInstance(), 3 * 60l);
			} else {
				player.sendMessage("§cVocê deve esperar um pouco para tentar entrar no §c§lKitPvP§c novamente!");
			}
		}
	}

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
}