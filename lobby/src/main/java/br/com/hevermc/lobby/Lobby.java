package br.com.hevermc.lobby;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import br.com.hevermc.commons.bukkit.Commons;
import br.com.hevermc.lobby.api.File;
import br.com.hevermc.lobby.command.commons.loader.CommandLoader;
import br.com.hevermc.lobby.listeners.GeneralListener;
import lombok.Getter;

public class Lobby extends JavaPlugin {

	@Getter
	public static Manager manager = new Manager();
	@Getter
	public static Lobby instance;
	@Getter
	File locations = new File("locations");

	@Override
	public void onEnable() {
		instance = this;
		Bukkit.getPluginManager().registerEvents(new GeneralListener(), this);
		new CommandLoader();
		locations.setup();
		getManager().setup();
		new BukkitRunnable() {

			@Override
			public void run() {
				Lobby.getManager().log(Commons.getManager().getRedis().get("tkpvp"));
				Lobby.getManager().log(Commons.getManager().getRedis().get("tdpvp"));

				Location l = new Location(Bukkit.getWorld("world"),
						Lobby.getInstance().getLocations().getConfig().getDouble("npc.pvp.x"),
						Lobby.getInstance().getLocations().getConfig().getDouble("npc.pvp.y") + 1,
						Lobby.getInstance().getLocations().getConfig().getDouble("npc.pvp.z"));
				Hologram h = HologramsAPI.createHologram(Lobby.getInstance(),
						new Location(l.getWorld(), l.getX(), l.getY() + 2.5, l.getZ()));
				h.appendTextLine("§a§lKITPVP");
				h.appendTextLine("");
				h.appendTextLine("§fJogando: §a0");

				new BukkitRunnable() {
					String htext = "§fJogando: §a0";

					@Override
					public void run() {

						String ult = htext;
						htext = "§fJogando: §a" + Commons.getManager().getRedis().get("kitpvp").replace("on:", "");
						if (!htext.equalsIgnoreCase(ult)) {
							h.removeLine(2);
							h.appendTextLine(htext);

						}
					}
				}.runTaskTimer(Lobby.getInstance(), 0, 20);

				Location tkl = new Location(Bukkit.getWorld("world"),
						Lobby.getInstance().getLocations().getConfig().getDouble("holo.tkpvp.x"),
						Lobby.getInstance().getLocations().getConfig().getDouble("holo.tkpvp.y") + 3,
						Lobby.getInstance().getLocations().getConfig().getDouble("holo.tkpvp.z"));
				Hologram tkh = HologramsAPI.createHologram(getInstance(), tkl);
				String[] a = Commons.getManager().getRedis().get("tkpvp").split(",");
				tkh.appendTextLine("§a§lKITPVP TOP DEATHS");
				tkh.appendTextLine("§f");
				tkh.appendTextLine(a[0]);
				tkh.appendTextLine(a[1]);
				tkh.appendTextLine(a[2]);
				tkh.appendTextLine(a[3]);
				tkh.appendTextLine(a[4]);
				
				Location tdl = new Location(Bukkit.getWorld("world"),
						Lobby.getInstance().getLocations().getConfig().getDouble("holo.tdpvp.x"),
						Lobby.getInstance().getLocations().getConfig().getDouble("holo.tdpvp.y") + 3,
						Lobby.getInstance().getLocations().getConfig().getDouble("holo.tdpvp.z"));
				Hologram tdh = HologramsAPI.createHologram(getInstance(), tdl);
				String[] c = Commons.getManager().getRedis().get("tdpvp").split(",");
				tdh.appendTextLine("§a§lKITPVP TOP DEATHS");
				tdh.appendTextLine("§f");
				tdh.appendTextLine(c[0]);
				tdh.appendTextLine(c[1]);
				tdh.appendTextLine(c[2]);
				tdh.appendTextLine(c[3]);
				tdh.appendTextLine(c[4]);
				new BukkitRunnable() {

					@Override
					public void run() {
						String[] b = Commons.getManager().getRedis().get("tkpvp").split(",");
						String[] c = Commons.getManager().getRedis().get("tdpvp").split(",");

						tdh.clearLines();
						tkh.clearLines();

						tdh.appendTextLine("§a§lKITPVP TOP DEATHS");
						tdh.appendTextLine("§f");
						tdh.appendTextLine(c[0]);
						tdh.appendTextLine(c[1]);
						tdh.appendTextLine(c[2]);
						tdh.appendTextLine(c[3]);
						tdh.appendTextLine(c[4]);

						//

						tkh.appendTextLine("§a§lKITPVP TOP KILLS");
						tkh.appendTextLine("§f");
						tkh.appendTextLine(b[0]);
						tkh.appendTextLine(b[1]);
						tkh.appendTextLine(b[2]);
						tkh.appendTextLine(b[3]);
						tkh.appendTextLine(b[4]);
					}
				}.runTaskTimer(Lobby.getInstance(), 0, 300 * 30);
			}
		}.runTaskLater(getInstance(), 80l);

		super.onEnable();
	}

}
