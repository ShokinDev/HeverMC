package br.com.hevermc.pvp.listeners.kits;

import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import br.com.hevermc.pvp.KitPvP;
import br.com.hevermc.pvp.api.PlayerLoader;
import br.com.hevermc.pvp.enums.Kits;
import br.com.hevermc.pvp.listeners.kits.commons.HeverKit;

public class Phantom implements Listener {
	public static ItemStack peito;
	public static LeatherArmorMeta peitometa;

	HeverKit kit = new HeverKit(Kits.PHANTOM);

	@EventHandler
	public void onInteractPhantom(PlayerInteractEvent event) {
		Player p = event.getPlayer();

		kit.setPlayer(p);
		if (event.getAction().name().contains("RIGHT") && kit.isItem() && kit.usingKit()) {
			ItemStack chest = new ItemStack(Material.LEATHER_HELMET, 1);
			LeatherArmorMeta chestp = (LeatherArmorMeta) chest.getItemMeta();
			chestp.setColor(Color.WHITE);
			chest.setItemMeta(chestp);
			ItemStack chest1 = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
			LeatherArmorMeta chestp1 = (LeatherArmorMeta) chest1.getItemMeta();
			chestp1.setColor(Color.WHITE);
			chest1.setItemMeta(chestp1);
			ItemStack calca = new ItemStack(Material.LEATHER_LEGGINGS, 1);
			LeatherArmorMeta chestp11 = (LeatherArmorMeta) calca.getItemMeta();
			chestp11.setColor(Color.WHITE);
			calca.setItemMeta(chestp11);
			ItemStack chest11 = new ItemStack(Material.LEATHER_BOOTS, 1);
			LeatherArmorMeta chestp111 = (LeatherArmorMeta) chest11.getItemMeta();
			chestp111.setColor(Color.WHITE);
			chest11.setItemMeta(chestp111);
			if (new PlayerLoader(p).load().getPvPP().isProtectArea()) {
				p.sendMessage("§cVocê está em uma área protegida, saia para usar o Phantom!");
				return;
			}
			final Player p1 = event.getPlayer();
			if (kit.verifyCooldown() == true) {
				p1.sendMessage("§cAguarde, você está em cooldown!");
				return;
			}

			Calendar c = Calendar.getInstance();
			c.add(Calendar.SECOND, 20);
			kit.setCooldown(c.getTime());

			p1.setAllowFlight(true);
			p1.setFlying(true);
			p1.getInventory().setHelmet(new ItemStack(chest));
			p1.getInventory().setChestplate(new ItemStack(chest1));
			p1.getInventory().setLeggings(new ItemStack(calca));
			p1.getInventory().setBoots(new ItemStack(chest11));
			p1.updateInventory();
			p1.sendMessage("§eVocê usou seu §aphantom§e!");

			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(KitPvP.getInstance(), new Runnable() {
				public void run() {
					p1.sendMessage("§eVocê tem §a5 §esegundos para §avoar§e!");
				}
			}, 20L);
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(KitPvP.getInstance(), new Runnable() {
				public void run() {
					p1.sendMessage("§eVocê tem §a4 §esegundos para §avoar§e!");
				}
			}, 40L);

			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(KitPvP.getInstance(), new Runnable() {
				public void run() {
					p1.sendMessage("§eVocê tem §a3 §esegundos para §avoar§e!");
				}
			}, 60L);
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(KitPvP.getInstance(), new Runnable() {
				public void run() {
					p1.sendMessage("§eVocê tem §a2 §esegundos para §avoar§e!");
				}
			}, 80L);
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(KitPvP.getInstance(), new Runnable() {
				public void run() {
					p1.sendMessage("§eVocê tem §a1 §esegundos para §avoar§e!");
				}
			}, 100L);

			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(KitPvP.getInstance(), new Runnable() {
				public void run() {
					p1.setAllowFlight(false);
					p1.sendMessage("§eVocê não pode mais §avoar§e!");
					p1.getInventory().setHelmet(null);
					p1.getInventory().setChestplate(null);
					p1.getInventory().setLeggings(null);
					p1.getInventory().setBoots(null);
					p1.updateInventory();
				}
			}, 120L);
		}
	}

	@EventHandler
	public void armorInteract(InventoryClickEvent e) {
		kit.setPlayer((Player) e.getWhoClicked());
		if (e.getCurrentItem() == null) {
			return;
		}
		if (e.getCurrentItem().getType().toString().contains("LEATHER") && kit.usingKit()) {
			e.setCancelled(true);
		}
	}
}