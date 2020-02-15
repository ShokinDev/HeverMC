package br.com.hevermc.lobby.api;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import br.com.hevermc.lobby.Lobby;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;

public class Holograms {

	private List<EntityArmorStand> entitylist = new ArrayList<EntityArmorStand>();
	private Location location;
	private String name;
	private double DISTANCE = 0.25D;
	int count;
	private EntityArmorStand as;

	public Holograms(String name, Location location) {
		this.name = name;
		this.location = location;
		create();
	}

	public void showPlayerTemp(Player p, int Time) {
		showPlayer(p);
		Bukkit.getScheduler().runTaskLater(Lobby.getInstance(), new Runnable() {
			public void run() {
				hidePlayer(p);
			}
		}, Time);
	}

	public void showAllTemp(int Time) {
		showAll();
		Bukkit.getScheduler().runTaskLater(Lobby.getInstance(), new Runnable() {
			public void run() {
				hideAll();
			}
		}, Time);
	}

	public Holograms showPlayer(Player p) {
		for (EntityArmorStand armor : entitylist) {
			armor.setCustomName(name);
			PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(armor);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		}
		return this;
	}

	public void hidePlayer(Player p) {
		for (EntityArmorStand armor : entitylist) {
			armor.setCustomName(armor.getCustomName());
			PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(armor.getId());
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		}
	}

	public void showAll() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			for (EntityArmorStand armor : entitylist) {
				armor.setCustomName(name);
				PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(armor);
				((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
			}
		}
	}

	public void hideAll() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			for (EntityArmorStand armor : entitylist) {
				armor.setCustomName(armor.getCustomName());
				PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(armor.getId());
				((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
			}
		}
	}

	private void create() {

		as = new EntityArmorStand(((CraftWorld) this.location.getWorld()).getHandle(), this.location.getX(),
				this.location.getY(), this.location.getZ());
		as.setCustomName(name);
		as.setCustomNameVisible(true);
		as.setInvisible(true);
		as.setGravity(false);
		entitylist.add(as);
		this.location.subtract(0, this.DISTANCE, 0);
		count++;

		for (int i = 0; i < count; i++) {
			this.location.add(0, this.DISTANCE, 0);
		}
		this.count = 0;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void update() {
		showAll();
	}

	public void update(String name, Player p) {
		hidePlayer(p);
		this.name = name;
		create();
		showPlayer(p);
	}

	public void update(String name) {
		hideAll();
		create();
		showAll();

	}

}