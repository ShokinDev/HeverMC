package br.com.hevermc.pvp.onevsone;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import br.com.hevermc.pvp.KitPvP;
import br.com.hevermc.pvp.api.PlayerLoader;
import br.com.hevermc.pvp.api.PvPPlayer;
import br.com.hevermc.pvp.api.WarpsAPI;
import br.com.hevermc.pvp.enums.Warps;

public final class Eventos1v1 implements Listener {
	
	public static ArrayList<Player> Combate1 = new ArrayList<Player>();

	public static ItemStack searchingItem() {
		ItemStack i = new ItemStack(Material.INK_SACK, 1, (byte) 10);
		ItemMeta ik = i.getItemMeta();
		ik.setDisplayName("§e1v1 Rapido §7(§aProcurando§7)");
		i.setItemMeta(ik);
		return i;
	}

	public static ItemStack customItem() {
		ItemStack i = new ItemStack(Material.IRON_FENCE, 1, (byte) 0);
		ItemMeta ik = i.getItemMeta();
		ik.setDisplayName("§e1v1 Customizado §7(§fClique§7)");
		i.setItemMeta(ik);
		return i;
	}

	public static ItemStack backItem() {
		ItemStack i = new ItemStack(Material.INK_SACK, 1, (byte) 8);
		ItemMeta ik = i.getItemMeta();
		ik.setDisplayName("§e1v1 Rapido §7(§cProcurando§7)");
		i.setItemMeta(ik);
		return i;
	}

	public static final HashMap<Player, String> batalhando = new HashMap<>();

	public static boolean wait = false;

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(final PlayerInteractEvent e) {
		final ItemStack i = e.getPlayer().getItemInHand();
		if (i.getType() == Material.getMaterial(351)) {
			if (i.getItemMeta().getDisplayName().equals("§e1v1 Rapido §7(§cProcurando§7)")) {
				e.getPlayer().updateInventory();
				e.getPlayer().setItemInHand(searchingItem());
				e.getPlayer().updateInventory();
				secondMatch = null;
				if (firstMatch == null) {
					firstMatch = e.getPlayer().getUniqueId();
					return;
				} else {
					Player findToChallenge = Bukkit.getPlayer(firstMatch);
					teleportToFight(e.getPlayer(), findToChallenge);
					fighting.add(e.getPlayer());
					fighting.add(findToChallenge);
					playerfigh.put(e.getPlayer(), findToChallenge.getName());
					playerfigh.put(findToChallenge, e.getPlayer().getName());
					batalhando.put(e.getPlayer(), findToChallenge.getName());
					batalhando.put(findToChallenge, e.getPlayer().getName());
					e.getPlayer().sendMessage("§aJogador encontrado: " + findToChallenge.getName() + "!");
					findToChallenge.sendMessage("§aJogador encontrado: " + e.getPlayer().getName() + "!");
					firstMatch = null;
					secondMatch = null;
				}
			} else if (i.getItemMeta().getDisplayName().equals("§e1v1 Rapido §7(§aProcurando§7)")) {
				firstMatch = null;
				secondMatch = null;
				e.getPlayer().setItemInHand(backItem());
				e.getPlayer().updateInventory();
			}
		}
	}

	@EventHandler
	public void onChallengeInteract(final PlayerInteractEntityEvent e) {
		if (!(e.getRightClicked() instanceof Player)) {
			return;
		}
		final Player challenged = (Player) e.getRightClicked();
		final ItemStack i = e.getPlayer().getItemInHand();
		PvPPlayer eplayer = new br.com.hevermc.pvp.api.PlayerLoader(e.getPlayer()).load().getPvPP();
		PvPPlayer challengedp = new br.com.hevermc.pvp.api.PlayerLoader(challenged).load().getPvPP();
		
		if (eplayer.getWarp() == Warps.ONEVSONE  && challengedp.getWarp() == Warps.ONEVSONE) {
			if (e.getPlayer().getItemInHand().getType() == Material.BLAZE_ROD) {
				if (i.getItemMeta().getDisplayName().equals("§e1v1 Normal §7(§fClique§7)")) {
					if (cooldown.contains(e.getPlayer())) {
						e.getPlayer().sendMessage("§cAguarde para desafiar novamente!");
						return;
					}
					if (challenge.containsKey(challenged) && challenge.get(challenged) == e.getPlayer()) {
						if (firstMatch == e.getPlayer().getUniqueId()) {
							firstMatch = null;
						}
						if (firstMatch == challenged.getUniqueId()) {
							firstMatch = null;
						}
						teleportToFight(e.getPlayer(), challenged);
						fighting.add(e.getPlayer());
						fighting.add(challenged);
						playerfigh.put(e.getPlayer(), challenged.getName());
						playerfigh.put(challenged, e.getPlayer().getName());
						batalhando.put(challenged, e.getPlayer().getName());
						batalhando.put(e.getPlayer(), challenged.getName());
						challenged.sendMessage("§aO jogador: " + e.getPlayer().getName() + " aceitou seu desafio!");
						e.getPlayer().sendMessage("§aVocê aceitou o desafio do jogador: " + challenged.getName() + "!");
						challenge.remove(challenged);
						if (challenge.containsKey(e.getPlayer())) {
							challenge.remove(e.getPlayer());
						}
						return;
					}
					if (playerfigh.containsKey(challenged)) {
						return;
					}
					e.getPlayer().sendMessage("§aVocê desafiou o jogador: " + challenged.getName() + " para um " + "1v1 normal!");
					challenged.sendMessage("§aVocê foi desafiado pelo jogador: " + e.getPlayer().getName() + " para um " + "1v1 normal!");
					cooldown.add(e.getPlayer());
					challenge.put(e.getPlayer(), challenged);
					Bukkit.getScheduler().runTaskLater(KitPvP.getInstance(), new Runnable() {
						@Override
						public void run() {
							if (cooldown.contains(e.getPlayer())) {
								cooldown.remove(e.getPlayer());
							}
							if (challenge.containsKey(e.getPlayer())) {
								challenge.remove(e.getPlayer());
							}
						}
					}, 5 * 20);
				}
			}
			if (e.getPlayer().getItemInHand().getType() == Material.IRON_FENCE) {
				if (i.getItemMeta().getDisplayName().equals("§e1v1 Customizado §7(§fClique§7)")) {
					if (cooldown.contains(e.getPlayer())) {
						e.getPlayer().sendMessage("§cAguarde para desafiar novamente!");
						return;
					}
					if (challengec.containsKey(challenged) && challengec.get(challenged) == e.getPlayer()) {
						if (firstMatch == e.getPlayer().getUniqueId()) {
							firstMatch = null;
						}
						if (firstMatch == challenged.getUniqueId()) {
							firstMatch = null;
						}
						teleportToCustomFight(challenged, e.getPlayer());
						fighting.add(e.getPlayer());
						fighting.add(challenged);
						playerfigh.put(e.getPlayer(), challenged.getName());
						playerfigh.put(challenged, e.getPlayer().getName());
						batalhando.put(challenged, e.getPlayer().getName());
						batalhando.put(e.getPlayer(), challenged.getName());
						challenged.sendMessage("§aO jogador: " + e.getPlayer().getName() + " aceitou o seu desafio!");
						e.getPlayer().sendMessage("§aVocê aceitou o desafio do jogador: " + challenged.getName() + "!");
						challengec.remove(challenged);
						if (challengec.containsKey(e.getPlayer())) {
							challengec.remove(e.getPlayer());
						}
						if (challenge.containsKey(e.getPlayer())) {
							challenge.remove(e.getPlayer());
						}
						if (challenge.containsKey(challenged)) {
							challenge.remove(challenged);
						}
						return;
					}
					if (playerfigh.containsKey(challenged)) {
						return;
					}
					Menu1v1Custom.setDefaultCustoms(e.getPlayer(), challenged.getName());
					Menu1v1Custom.openCustomInventory(e.getPlayer(), challenged);
				}
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public static int itemsInInventory(Inventory inventory, Material[] search) {
		List wanted = Arrays.asList(search);
		int found = 0;
		ItemStack[] arrayOfItemStack;
		int j = (arrayOfItemStack = inventory.getContents()).length;
		for (int i = 0; i < j; i++) {
			ItemStack item = arrayOfItemStack[i];
			if ((item != null) && (wanted.contains(item.getType()))) {
				found += item.getAmount();
			}
		}
		return found;
	}

	public static String cora(Player p) {
		Damageable vida = p;
		return NumberFormat.getCurrencyInstance().format(vida.getHealth() / 2).replace("$", "").replace("R", "").replace(",", ".");
	}

	public static void show(Player p) {
		for (Player t : Bukkit.getOnlinePlayers()) {
			p.showPlayer(t);
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onDamage(final EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			
			PvPPlayer p2 = new br.com.hevermc.pvp.api.PlayerLoader(p).load().getPvPP();
			
			if (p2.getWarp() == Warps.ONEVSONE && !playerfigh.containsKey(p)) {
				e.setCancelled(true);
			}
		}
	}

	public static UUID firstMatch;
	public static UUID secondMatch;
	public static UUID thirdMatch;

	public static final ArrayList<Player> cooldown = new ArrayList<>();	
	public static final HashMap<Player, Player> challenge = new HashMap<>();

	public static final HashMap<Player, Player> challengec = new HashMap<>();

	public static final ArrayList<Player> fighting = new ArrayList<>();
	public static final HashMap<Player, String> playerfigh = new HashMap<>();

	public static ItemStack newItem(Material material, int qnt, byte color) {
		ItemStack i = new ItemStack(material, qnt, (byte) color);
		return i;
	}

	public static ItemStack newItem(Material material, String name, int qnt, byte color) {
		ItemStack i = new ItemStack(material, qnt, (byte) color);
		ItemMeta ik = i.getItemMeta();
		ik.setDisplayName(name);
		i.setItemMeta(ik);
		return i;
	}

	public static ItemStack newItemEnchant(Material material, Enchantment ench, int qnt, byte color) {
		ItemStack i = new ItemStack(material, qnt, (byte) color);
		ItemMeta ik = i.getItemMeta();
		ik.addEnchant(ench, 1, true);
		i.setItemMeta(ik);
		return i;
	}
	
	@EventHandler
	public void cmd(PlayerCommandPreprocessEvent event) {
		Player p = event.getPlayer();
		
		PvPPlayer p2 = new br.com.hevermc.pvp.api.PlayerLoader(p).load().getPvPP();
		
		if (p2.getWarp() == Warps.ONEVSONE && Combate1.contains(p) && event.getMessage().startsWith("/")) {
			
			p.playSound(p.getLocation(), Sound.CLICK, 15.0F, 1.0F);
			event.getPlayer().sendMessage("§cVocê não pode utilizar comandos na warp 1v1!");
			event.setCancelled(true);
		}
	}

	public static void prepareInventory(Player p) {
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.getInventory().setBoots(newItem(Material.IRON_BOOTS, 1, (byte) 0));
		p.getInventory().setLeggings(newItem(Material.IRON_LEGGINGS, 1, (byte) 0));
		p.getInventory().setChestplate(newItem(Material.IRON_CHESTPLATE, 1, (byte) 0));
		p.getInventory().setHelmet(newItem(Material.IRON_HELMET, 1, (byte) 0));
		p.getInventory().setItem(0, newItemEnchant(Material.DIAMOND_SWORD, Enchantment.DAMAGE_ALL, 1, (byte) 0));
		for (int i = 8; i > 0; i--) {
			p.getInventory().setItem(i, newItem(Material.MUSHROOM_SOUP, 1, (byte) 0));
		}
		p.updateInventory();
	}

	public static void defaultItens(Player p) {
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.getInventory().setItem(2, newItem(Material.BLAZE_ROD, "§e1v1 Normal §7(§fClique§7)", 1, (byte) 0));
		p.getInventory().setItem(4, customItem());
		p.getInventory().setItem(6, backItem());
		p.updateInventory();
	}

	public static void teleportToFight(Player p1, Player p2) {
		p1.teleport(new WarpsAPI(Warps.OVPOS1).getLocation());
		p2.teleport(new WarpsAPI(Warps.OVPOS2).getLocation());
		PvPPlayer pvp = new PlayerLoader(p1).load().getPvPP();
		PvPPlayer pvp2 = new PlayerLoader(p2).load().getPvPP();
		pvp.setProtectArea(false);
		pvp2.setProtectArea(false);
		prepareInventory(p1);
		prepareInventory(p2);
		Combate1.add(p2);
		Combate1.add(p1);
        for (Player todos2: Bukkit.getOnlinePlayers()) {
            p1.hidePlayer(todos2);
			p2.hidePlayer(todos2);
        }
		p1.showPlayer(p2);
		p2.showPlayer(p1);
		pvp.setCombat(true);
		pvp2.setCombat(true);
		pvp.setInCombat(p2);
		pvp2.setInCombat(p1);
	}

	public static void teleportToCustomFight(Player p1, Player p2) {
		p1.teleport(new WarpsAPI(Warps.OVPOS1).getLocation());
		p2.teleport(new WarpsAPI(Warps.OVPOS2).getLocation());
		PvPPlayer pvp = new PlayerLoader(p1).load().getPvPP();
		PvPPlayer pvp2 = new PlayerLoader(p2).load().getPvPP();
		pvp.setProtectArea(false);
		pvp2.setProtectArea(false);
		Menu1v1Custom.loadItensCustom(p1, p2);
        for (Player todos2: Bukkit.getOnlinePlayers()) {
            p1.hidePlayer(todos2);
			p2.hidePlayer(todos2);
        }
		p1.showPlayer(p2);
		p2.showPlayer(p1);
		Combate1.add(p2);
		Combate1.add(p1);
		pvp.setCombat(true);
		pvp2.setCombat(true);
		pvp.setInCombat(p2);
		pvp2.setInCombat(p1);
	}
}
