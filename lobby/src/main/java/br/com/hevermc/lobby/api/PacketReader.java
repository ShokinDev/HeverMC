package br.com.hevermc.lobby.api;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.hevermc.commons.bukkit.Commons;
import br.com.hevermc.commons.bukkit.account.HeverPlayer;
import br.com.hevermc.commons.bukkit.account.loader.PlayerLoader;
import br.com.hevermc.commons.enums.Groups;
import br.com.hevermc.lobby.Lobby;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.minecraft.server.v1_8_R3.Packet;

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
				HeverPlayer hp = PlayerLoader.getHP(player);
				if (Lobby.getManager().npc.get(player) == id) {
					player.sendMessage("§b§lCONNECT §fVocê está sendo conectado ao §b§lKITPVP§f!");
					Commons.getManager().getBungeeChannel().connect(player, "kitpvp");
					buff.add(player);
				}
				if (Lobby.getManager().npc2.get(player) == id) {
					if (hp.groupIsLarger(Groups.ADMIN)) {
						player.sendMessage("§b§lCONNECT §fVocê está sendo conectado ao §b§lHG§f!");
						Commons.getManager().getBungeeChannel().connect(player, "hg-1");
					} else {
						player.sendMessage("§aServidor em desenvolvimento!");
					}
					buff.add(player);
				}
				if (Lobby.getManager().npc3.get(player) == id) {
					if (hp.groupIsLarger(Groups.ADMIN)) {
						player.sendMessage("§b§lCONNECT §fVocê está sendo conectado ao §b§lGLADIATOR§f!");
						Commons.getManager().getBungeeChannel().connect(player, "gladiator");
					} else {
						player.sendMessage("§aServidor em desenvolvimento!");
					}
					buff.add(player);
				}
				new BukkitRunnable() {
					@Override
					public void run() {
						buff.remove(player);
					}
				}.runTaskLater(Lobby.getInstance(), 50L);
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