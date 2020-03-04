package br.com.hevermc.hardcoregames;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.hevermc.commons.bukkit.Commons;
import br.com.hevermc.commons.bukkit.api.ItemConstructor;
import br.com.hevermc.hardcoregames.api.HGPlayer;
import br.com.hevermc.hardcoregames.api.Timer;
import br.com.hevermc.hardcoregames.enums.States;
import br.com.hevermc.hardcoregames.schematicutils.Schematic;
import lombok.Getter;
import lombok.Setter;

public class Manager {

	public ArrayList<Player> inGame = new ArrayList<Player>();
	public int minPlayerToStart = 5;
	public HashMap<String, HGPlayer> hgplayer = new HashMap<String, HGPlayer>();

	@Getter
	public int hgNumber = Integer.valueOf(Bukkit.getMotd().replace("hg-", ""));

	@Setter
	@Getter
	public States stateGame;

	@Setter
	@Getter
	public Player winner;

	public void log(String log) {
		System.out.println("[HARDCOREGAMES] " + log);
	}

	public void broadcast(String s) {
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage(s);
		Bukkit.broadcastMessage("");
	}

	public String formattedTime(int timer) {
		int min = timer / 60;
		int segs = timer % 60;
		return ((min >= 10) ? Integer.valueOf(min) : ("0" + min)) + "m "
				+ ((segs >= 10) ? (String.valueOf(segs) + "s") : ("0" + segs + "s"));
	}

	public String formattedTimeForScoreboard(int timer) {
		int min = timer / 60;
		int segs = timer % 60;
		return ((min >= 10) ? Integer.valueOf(min) : ("0" + min)) + ":"
				+ ((segs >= 10) ? Integer.valueOf(segs) : ("0" + segs));
	}

	@SuppressWarnings("deprecation")
	public void setup() {
		try {
			spawnar("coliseu", new Location(Bukkit.getWorld("world"), 0, 160, 0));

		    Bukkit.getWorld("world").setTime(0L);
		    Bukkit.getWorld("world").setWeatherDuration(0);
		    ItemStack Resultado = new ItemStack(Material.MUSHROOM_SOUP, 1);
		    ItemMeta Cactos = Resultado.getItemMeta();
		    Resultado.setItemMeta(Cactos);
		    ShapelessRecipe CraftCactos = new ShapelessRecipe(Resultado);
		    CraftCactos.addIngredient(1, Material.CACTUS);
		    CraftCactos.addIngredient(1, Material.BOWL);
		    Bukkit.getServer().addRecipe((Recipe)CraftCactos);
		    ItemMeta Cocoa = Resultado.getItemMeta();
		    Resultado.setItemMeta(Cocoa);
		    ShapelessRecipe CraftCocoa = new ShapelessRecipe(Resultado);
		    CraftCocoa.addIngredient(1, Material.INK_SACK, 3);
		    CraftCocoa.addIngredient(1, Material.BOWL);
		    Bukkit.getServer().addRecipe((Recipe)CraftCocoa);
		    ItemMeta Pumpkin = Resultado.getItemMeta();
		    Resultado.setItemMeta(Pumpkin);
		    ShapelessRecipe CraftPumpkin = new ShapelessRecipe(Resultado);
		    CraftPumpkin.addIngredient(1, Material.PUMPKIN_SEEDS);
		    CraftPumpkin.addIngredient(1, Material.BOWL);
		    Bukkit.getServer().addRecipe((Recipe)CraftPumpkin);
		    ItemMeta Flower = Resultado.getItemMeta();
		    Resultado.setItemMeta(Flower);
		    ShapelessRecipe CraftFlower = new ShapelessRecipe(Resultado);
		    CraftFlower.addIngredient(1, Material.YELLOW_FLOWER);
		    CraftFlower.addIngredient(1, Material.getMaterial(38));
		    CraftFlower.addIngredient(1, Material.BOWL);
		    Bukkit.getServer().addRecipe((Recipe)CraftFlower);
			setStateGame(States.PREJOGO);
			Commons.getManager().getBackend().getRedis().set("hg-" + hgNumber,
					"status:" + getStateGame().toString() + ",timer:" + formattedTime(Timer.tempo) + ",on:0");
			Timer.iniciarTimer();
			log("Initialization completed successfully!");
		} catch (Exception e) {
			log("Initialization completed unsuccessfully!!");
			e.printStackTrace();
		}
	}

	public void loadChunks() {
		Bukkit.getConsoleSender().sendMessage("§cTentando carregar chunks!");
		World world = Bukkit.getWorld("world");

		for (int x = -601; x <= 601; x += 16) {
			for (int z = -601; z <= 601; z += 16) {
				Location location = new Location(world, x, world.getHighestBlockYAt(x, z), z);
				if (!world.getChunkAt(location).isLoaded()) {
					world.getChunkAt(location).load(true);
				}
			}
		}
		log("§aChunks carregados!");
	}

	public void spawnar(String tipo, Location loc) {
		try {
			Schematic schematic = Schematic.carregarSchematic(
					new File(HardcoreGames.getInstance().getDataFolder() + "/schematics/" + tipo + ".schematic"));
			Schematic.spawnarSchematic(tipo, Bukkit.getWorld("world"), loc, schematic);
			log("§a" + tipo + " spawnou!");
		} catch (IOException localIOException) {
			localIOException.printStackTrace();
		}
	}

	public void stop() {

		for (Player on : Bukkit.getOnlinePlayers()) {
			on.sendMessage("§cPartida acabou!\n§7Vencedor: §a" + (winner == null ? "Ninguém" : winner.getName()));
			Commons.getManager().getBungeeChannel().connect(on, "lobby");
		}

		new BukkitRunnable() {
			public void run() {
				Bukkit.shutdown();
			}
		}.runTaskLater(HardcoreGames.getInstance(), 40L);
	}

	public void deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++)
				deleteDir(new File(dir, children[i]));
		}
		dir.delete();
	}

	public void checkWin() {
		if (inGame.size() > 1)
			return;

		if (inGame.size() == 0) {
			Bukkit.shutdown();
			return;
		}

		if (inGame.size() == 1) {
			setStateGame(States.FINALIZANDO);
			Timer.cancelTask();
			Player winner = inGame.get(0);
			setWinner(winner);

			if (winner == null) {
				stop();
				return;
			}

			winner.getWorld().setTime(19000);
			new BukkitRunnable() {
				int segundos = 25;

				public void run() {
					if (segundos == 0) {
						cancel();
						stop();
						return;
					}
					if (segundos % 2 == 0) {
						Bukkit.broadcastMessage("§aO jogador: " + winner.getName() + " ganhou a partida!");
					}
					if (!winner.isOnline()) {
						cancel();
						stop();
						return;
					}
					segundos--;
				}
			}.runTaskTimer(HardcoreGames.getInstance(), 20, 20);

			new BukkitRunnable() {
				public void run() {
					EnderDragon o = (EnderDragon) winner.getWorld().spawn(winner.getLocation().add(0, 70, 0),
							EnderDragon.class);
					o.setCustomName("§aO jogador: " + winner.getName() + " ganhou a partida!");
					o.setCustomNameVisible(false);
					o.setNoDamageTicks(12000);
					o.setPassenger(winner);

					winner.getInventory().clear();
					winner.getInventory().setArmorContents(null);
					winner.getInventory().setItem(0,
							new ItemConstructor(new ItemStack(Material.WATER_BUCKET), "§fBalde de Água").create());
					winner.updateInventory();
				}
			}.runTaskLater(HardcoreGames.getInstance(), 140L);
		}
	}

	public void spawnarArenaFinal() {
		int x = 0, y = 0, z = 0;
		Location loc = Bukkit.getWorld("world").getBlockAt(0, 15, 0).getLocation();
		List<Location> cuboid = new ArrayList<>();
		cuboid.clear();
		for (x = -30; x < 30; x++)
			for (y = 0; y < 80; y++)
				for (z = -30; z < 30; z++)
					loc.clone().add(x, y, z).getBlock().setType(Material.AIR);

		for (int bX = -30; bX <= 30; bX++)
			for (int bZ = -30; bZ <= 30; bZ++)
				for (int bY = -1; bY <= 130; bY++)
					if (bY == -1) {
						cuboid.add(loc.clone().add(bX, bY, bZ));
					} else if ((bX == -30) || (bZ == -30) || (bX == 30) || (bZ == 30)) {
						cuboid.add(loc.clone().add(bX, bY, bZ));
					}

		for (Location loc1 : cuboid)
			loc1.getBlock().setType(Material.BEDROCK);

		for (Item item : Bukkit.getWorld("world").getEntitiesByClass(Item.class)) {
			item.remove();
		}
	}

	public void criarBordas() {
		World world = Bukkit.getWorld("world");
		log("§cTentando criar bordas!");

		for (int x = -301; x <= 301; x++) {
			if ((x == -301) || (x == 301)) {
				for (int z = -301; z <= 301; z++) {
					for (int y = 0; y <= 150; y++) {
						if (new Random().nextBoolean()) {
							world.getBlockAt(x, y, z).setType(Material.GLASS);
						} else {
							world.getBlockAt(x, y, z).setType(Material.BEDROCK);
						}
					}
				}
			}
		}
		for (int z = -501; z <= 301; z++) {
			if ((z == -501) || (z == 501)) {
				for (int x = -501; x <= 501; x++) {
					for (int y = 0; y <= 150; y++) {
						if (new Random().nextBoolean()) {
							world.getBlockAt(x, y, z).setType(Material.GLASS);
						} else {
							world.getBlockAt(x, y, z).setType(Material.BEDROCK);
						}
					}
				}
			}
		}
		log("§aBordas criadas!");
	}

	public String getItemInHand(Material material) {
		String causa = "";
		if (material.equals(Material.WOOD_SWORD))
			causa = "uma Espada de Madeira";
		else if (material.equals(Material.STONE_SWORD))
			causa = "uma Espada de Pedra";
		else if (material.equals(Material.GOLD_SWORD))
			causa = "uma Espada de Ouro";
		else if (material.equals(Material.IRON_SWORD))
			causa = "uma Espada de Ferro";
		else if (material.equals(Material.DIAMOND_SWORD))
			causa = "uma Espada de Diamante";
		else if (material.equals(Material.WOOD_PICKAXE))
			causa = "uma Picareta de Madeira";
		else if (material.equals(Material.STONE_PICKAXE))
			causa = "uma Picareta de Pedra";
		else if (material.equals(Material.GOLD_PICKAXE))
			causa = "uma Picareta de Ouro";
		else if (material.equals(Material.IRON_PICKAXE))
			causa = "uma Picareta de Ferro";
		else if (material.equals(Material.DIAMOND_PICKAXE))
			causa = "uma Picareta de Diamante";
		else if (material.equals(Material.WOOD_AXE))
			causa = "um Machado de Madeira";
		else if (material.equals(Material.STONE_AXE))
			causa = "um Machado de Pedra";
		else if (material.equals(Material.GOLD_AXE))
			causa = "um Machado de Ouro";
		else if (material.equals(Material.IRON_AXE))
			causa = "um Machado de Ferro";
		else if (material.equals(Material.DIAMOND_AXE))
			causa = "um Machado de Diamante";
		else if (material.equals(Material.COMPASS))
			causa = "uma Bussola";
		else if (material.equals(Material.MUSHROOM_SOUP))
			causa = "uma Sopa";
		else if (material.equals(Material.STICK))
			causa = "um Graveto";
		else if (material.equals(Material.AIR))
			causa = "o Punho";
		else
			causa = "o Punho";
		return causa;
	}

	public String getCause(DamageCause deathCause) {
		String cause = "";
		if (deathCause.equals(DamageCause.ENTITY_ATTACK)) {
			cause = "atacado por um monstro";
		} else if (deathCause.equals(DamageCause.CUSTOM)) {
			cause = "de uma forma não conhecida";
		} else if (deathCause.equals(DamageCause.BLOCK_EXPLOSION)) {
			cause = "explodido em mil pedaços";
		} else if (deathCause.equals(DamageCause.ENTITY_EXPLOSION)) {
			cause = "explodido por um monstro";
		} else if (deathCause.equals(DamageCause.CONTACT)) {
			cause = "espetado por um cacto";
		} else if (deathCause.equals(DamageCause.FALL)) {
			cause = "de queda";
		} else if (deathCause.equals(DamageCause.FALLING_BLOCK)) {
			cause = "stompado por um bloco";
		} else if (deathCause.equals(DamageCause.FIRE_TICK)) {
			cause = "pegando fogo";
		} else if (deathCause.equals(DamageCause.LAVA)) {
			cause = "nadando na lava";
		} else if (deathCause.equals(DamageCause.LIGHTNING)) {
			cause = "atingido por um raio";
		} else if (deathCause.equals(DamageCause.MAGIC)) {
			cause = "atingido por uma magia";
		} else if (deathCause.equals(DamageCause.MELTING)) {
			cause = "atingido por um boneco de neve";
		} else if (deathCause.equals(DamageCause.POISON)) {
			cause = "envenenado";
		} else if (deathCause.equals(DamageCause.PROJECTILE)) {
			cause = "atingido por um projétil";
		} else if (deathCause.equals(DamageCause.STARVATION)) {
			cause = "de fome";
		} else if (deathCause.equals(DamageCause.SUFFOCATION)) {
			cause = "sufocado";
		} else if (deathCause.equals(DamageCause.SUICIDE)) {
			cause = "se suicidando";
		} else if (deathCause.equals(DamageCause.THORNS)) {
			cause = "encostando em alguns espinhos";
		} else if (deathCause.equals(DamageCause.VOID)) {
			cause = "pelo void";
		} else if (deathCause.equals(DamageCause.WITHER)) {
			cause = "pelo efeito do whiter";
		} else {
			cause = "por uma causa desconhecida";
		}
		return cause;
	}
}
