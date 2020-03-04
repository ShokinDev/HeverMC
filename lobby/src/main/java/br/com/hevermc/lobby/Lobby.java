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
				Location l = new Location(Bukkit.getWorld("world"),
						Lobby.getInstance().getLocations().getConfig().getDouble("npc.pvp.x"),
						Lobby.getInstance().getLocations().getConfig().getDouble("npc.pvp.y") + 1,
						Lobby.getInstance().getLocations().getConfig().getDouble("npc.pvp.z"));
				Hologram h = HologramsAPI.createHologram(Lobby.getInstance(),
						new Location(l.getWorld(), l.getX(), l.getY() + 2.5, l.getZ()));
				h.appendTextLine("§a§lKITPVP");
				h.appendTextLine("");
				h.appendTextLine("§fJogando: §a0");

				Location l2 = new Location(Bukkit.getWorld("world"),
						Lobby.getInstance().getLocations().getConfig().getDouble("npc.hg.x"),
						Lobby.getInstance().getLocations().getConfig().getDouble("npc.hg.y") + 1,
						Lobby.getInstance().getLocations().getConfig().getDouble("npc.hg.z"));
				Hologram h2 = HologramsAPI.createHologram(Lobby.getInstance(),
						new Location(l2.getWorld(), l2.getX(), l2.getY() + 2.5, l2.getZ()));
				h2.appendTextLine("§a§lHARDCOREGAMES");
				h2.appendTextLine("");
				h2.appendTextLine("§fJogando: §a0");

				Location l3 = new Location(Bukkit.getWorld("world"),
						Lobby.getInstance().getLocations().getConfig().getDouble("npc.glad.x"),
						Lobby.getInstance().getLocations().getConfig().getDouble("npc.glad.y") + 1,
						Lobby.getInstance().getLocations().getConfig().getDouble("npc.glad.z"));
				Hologram h3 = HologramsAPI.createHologram(Lobby.getInstance(),
						new Location(l3.getWorld(), l3.getX(), l3.getY() + 2.5, l3.getZ()));
				h3.appendTextLine("§a§lGLADIATOR");
				h3.appendTextLine("");
				h3.appendTextLine("§fJogando: §a0");

				new BukkitRunnable() {
					String htext = "§fJogando: §a0";
					
					@Override
					public void run() {

						int ult = Integer.valueOf(htext.replace("§fJogando: §a", ""));
						int atual = Commons.getManager().getBackend().getRedis().get("kitpvp") != null
								? Integer.valueOf(Commons.getManager().getBackend().getRedis().get("kitpvp").replace("on:", ""))
								: 0;
						if (ult != atual) {
							htext = "§fJogando: §a" + (Commons.getManager().getBackend().getRedis().get("kitpvp") != null
									? Commons.getManager().getBackend().getRedis().get("kitpvp").replace("on:", "")
									: 0);
							h.removeLine(2);
							h.appendTextLine(htext);
						}

						String ult2 = htext;
						htext = "§fJogando: §a"
								+ (Commons.getManager().getBackend().getRedis().get("hardcoregames") != null ? Commons
										.getManager().getBackend().getRedis().get("hardcoregames").replace("on:", "")
										: 0);
						if (!htext.equalsIgnoreCase(ult2)) {
							h2.removeLine(2);
							h2.appendTextLine(htext);
						}

						String ult3 = htext;
						htext = "§fJogando: §a" + (Commons.getManager().getBackend().getRedis().get("gladiator") != null
								? Commons.getManager().getBackend().getRedis().get("gladiator").replace("on:", "")
								: 0);
						if (!htext.equalsIgnoreCase(ult3)) {
							h3.removeLine(2);
							h3.appendTextLine(htext);
						}
					}

				}.runTaskTimer(Lobby.getInstance(), 0, 20);

				Location tkl = new Location(Bukkit.getWorld("world"),
						Lobby.getInstance().getLocations().getConfig().getDouble("holo.tkpvp.x"),
						Lobby.getInstance().getLocations().getConfig().getDouble("holo.tkpvp.y") + 4,
						Lobby.getInstance().getLocations().getConfig().getDouble("holo.tkpvp.z"));
				Location rank = new Location(Bukkit.getWorld("world"),
						Lobby.getInstance().getLocations().getConfig().getDouble("holo.rank.x"),
						Lobby.getInstance().getLocations().getConfig().getDouble("holo.rank.y") + 3,
						Lobby.getInstance().getLocations().getConfig().getDouble("holo.rank.z"));

				Hologram topranks = HologramsAPI.createHologram(getInstance(), rank);
				String[] ab = Commons.getManager().getBackend().getRedis().get("rank").split(",");
				topranks.appendTextLine("§a§lTOP RANKS");
				topranks.appendTextLine("§f");
				topranks.appendTextLine(ab[0]);
				topranks.appendTextLine(ab[1]);
				topranks.appendTextLine(ab[2]);
				topranks.appendTextLine(ab[3]);
				topranks.appendTextLine(ab[4]);
				topranks.appendTextLine(ab[5]);
				topranks.appendTextLine(ab[6]);
				topranks.appendTextLine(ab[7]);
				topranks.appendTextLine(ab[8]);
				topranks.appendTextLine(ab[9]);

				Hologram tkh = HologramsAPI.createHologram(getInstance(), tkl);
				String[] a = Commons.getManager().getBackend().getRedis().get("tkpvp").split(",");
				tkh.appendTextLine("§a§lKITPVP TOP KILLS");
				tkh.appendTextLine("§f");
				tkh.appendTextLine(a[0]);
				tkh.appendTextLine(a[1]);
				tkh.appendTextLine(a[2]);
				tkh.appendTextLine(a[3]);
				tkh.appendTextLine(a[4]);
				tkh.appendTextLine(a[5]);
				tkh.appendTextLine(a[6]);
				tkh.appendTextLine(a[7]);
				tkh.appendTextLine(a[8]);
				tkh.appendTextLine(a[9]);

				Location tdl = new Location(Bukkit.getWorld("world"),
						Lobby.getInstance().getLocations().getConfig().getDouble("holo.tdpvp.x"),
						Lobby.getInstance().getLocations().getConfig().getDouble("holo.tdpvp.y") + 4,
						Lobby.getInstance().getLocations().getConfig().getDouble("holo.tdpvp.z"));
				Hologram tdh = HologramsAPI.createHologram(getInstance(), tdl);
				String[] c = Commons.getManager().getBackend().getRedis().get("tdpvp").split(",");
				tdh.appendTextLine("§a§lKITPVP TOP DEATHS");
				tdh.appendTextLine("§f");
				tdh.appendTextLine(c[0]);
				tdh.appendTextLine(c[1]);
				tdh.appendTextLine(c[2]);
				tdh.appendTextLine(c[3]);
				tdh.appendTextLine(c[4]);
				tdh.appendTextLine(c[5]);
				tdh.appendTextLine(c[6]);
				tdh.appendTextLine(c[7]);
				tdh.appendTextLine(c[8]);
				tdh.appendTextLine(c[9]);
				new BukkitRunnable() {

					@Override
					public void run() {
						String[] b = Commons.getManager().getBackend().getRedis().get("tkpvp").split(",");
						String[] c = Commons.getManager().getBackend().getRedis().get("tdpvp").split(",");

						tdh.clearLines();
						tkh.clearLines();

						tdh.appendTextLine("§a§lKITPVP TOP DEATHS");
						tdh.appendTextLine("§f");
						tdh.appendTextLine(c[0]);
						tdh.appendTextLine(c[1]);
						tdh.appendTextLine(c[2]);
						tdh.appendTextLine(c[3]);
						tdh.appendTextLine(c[4]);
						tdh.appendTextLine(c[5]);
						tdh.appendTextLine(c[6]);
						tdh.appendTextLine(c[7]);
						tdh.appendTextLine(c[8]);
						tdh.appendTextLine(c[9]);

						//

						tkh.appendTextLine("§a§lKITPVP TOP KILLS");
						tkh.appendTextLine("§f");
						tkh.appendTextLine(b[0]);
						tkh.appendTextLine(b[1]);
						tkh.appendTextLine(b[2]);
						tkh.appendTextLine(b[3]);
						tkh.appendTextLine(b[4]);
						tkh.appendTextLine(b[5]);
						tkh.appendTextLine(b[6]);
						tkh.appendTextLine(b[7]);
						tkh.appendTextLine(b[8]);
						tkh.appendTextLine(b[9]);

						String[] ab = Commons.getManager().getBackend().getRedis().get("rank").split(",");
						topranks.clearLines();

						topranks.appendTextLine("§a§lTOP RANKS");
						topranks.appendTextLine("§f");
						topranks.appendTextLine(ab[0]);
						topranks.appendTextLine(ab[1]);
						topranks.appendTextLine(ab[2]);
						topranks.appendTextLine(ab[3]);
						topranks.appendTextLine(ab[4]);
						topranks.appendTextLine(ab[5]);
						topranks.appendTextLine(ab[6]);
						topranks.appendTextLine(ab[7]);
						topranks.appendTextLine(ab[8]);
						topranks.appendTextLine(ab[9]);
					}
				}.runTaskTimer(Lobby.getInstance(), 0, 300 * 30);
			}
		}.runTaskLater(getInstance(), 80l);

		super.onEnable();
	}

}
