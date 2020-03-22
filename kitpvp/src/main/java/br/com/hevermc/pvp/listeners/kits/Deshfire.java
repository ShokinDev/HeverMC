package br.com.hevermc.pvp.listeners.kits;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.util.Vector;

import br.com.hevermc.pvp.KitPvP;
import br.com.hevermc.pvp.api.PlayerLoader;
import br.com.hevermc.pvp.api.PvPPlayer;
import br.com.hevermc.pvp.enums.Kits;
import br.com.hevermc.pvp.listeners.kits.commons.HeverKit;

public class Deshfire implements Listener {
	public int boost = 6;
	public static ArrayList<String> sonic = new ArrayList<>();
	ArrayList<String> fall = new ArrayList<>();
	public static HashMap<String, ItemStack[]> Armadura = new HashMap<>();
	public static ItemStack peito;
	public static LeatherArmorMeta peitometa;
	HeverKit kit = new HeverKit(Kits.DESHFIRE);

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

	@EventHandler
	public void VelotrolClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		kit.setPlayer(p);
		if ((e.getPlayer().getItemInHand().getType() == Material.REDSTONE_BLOCK) && (kit.usingKit())) {
			if ((e.getAction() == Action.LEFT_CLICK_AIR) || (e.getAction() == Action.LEFT_CLICK_BLOCK)
					|| (e.getAction() == Action.RIGHT_CLICK_BLOCK) || (e.getAction() == Action.RIGHT_CLICK_AIR)) {
				e.setCancelled(true);
				p.playSound(p.getLocation(), Sound.ANVIL_LAND, 2.0F, 2.0F);
			}
			if (kit.verifyCooldown() == true) {
				p.sendMessage("§e§lKIT §fAguarde, você está em §4§lCOOLDOWN§f!");
				return;
			}
			p.setVelocity(p.getEyeLocation().getDirection().multiply(this.boost).add(new Vector(0, 0, 0)));
			p.getPlayer().getWorld().playEffect(p.getPlayer().getLocation(), Effect.MOBSPAWNER_FLAMES, 10, 0);
		
			for (Entity pertos : p.getNearbyEntities(8.0D, 8.0D, 8.0D)) {
				if ((pertos instanceof Player)) {
					Player perto = (Player) pertos;
					PvPPlayer pvpp = new PlayerLoader(perto).load().getPvPP();
					if (pvpp.isProtectArea()) {
						return;
					}
					perto.sendMessage("§e§lKIT §fUm §c§lDESHFIRE §fpassou por perto!");
					((Player) pertos).damage(10.0D);
					pertos.setVelocity(new Vector(0.1D, 0.0D, 0.1D));
					((Player) pertos).setFireTicks(150);
				}
			}
			Calendar c = Calendar.getInstance();
			c.add(Calendar.SECOND, 30);
			kit.setCooldown(c.getTime());
			ItemStack Capacete = new ItemStack(Material.LEATHER_HELMET);
			LeatherArmorMeta kCapacete = (LeatherArmorMeta) Capacete.getItemMeta();
			kCapacete.setColor(Color.RED);
			Capacete.setItemMeta(kCapacete);

			ItemStack Peitoral = new ItemStack(Material.LEATHER_CHESTPLATE);
			LeatherArmorMeta kPeitoral = (LeatherArmorMeta) Peitoral.getItemMeta();
			kPeitoral.setColor(Color.RED);
			Peitoral.setItemMeta(kPeitoral);

			ItemStack Calça = new ItemStack(Material.LEATHER_LEGGINGS);
			LeatherArmorMeta kCalça = (LeatherArmorMeta) Calça.getItemMeta();
			kCalça.setColor(Color.RED);
			Calça.setItemMeta(kCalça);

			ItemStack Bota = new ItemStack(Material.LEATHER_BOOTS);
			LeatherArmorMeta kBota = (LeatherArmorMeta) Capacete.getItemMeta();
			kBota.setColor(Color.RED);
			Bota.setItemMeta(kBota);

			Armadura.put(p.getName(), p.getInventory().getArmorContents());

			p.getInventory().setHelmet(Capacete);
			p.getInventory().setChestplate(Peitoral);
			p.getInventory().setLeggings(Calça);
			p.getInventory().setBoots(Bota);
			p.updateInventory();

			Bukkit.getScheduler().scheduleSyncDelayedTask(KitPvP.getInstance(), (Runnable) new Runnable() {
				public void run() {
					p.getInventory().setHelmet(null);
					p.getInventory().setChestplate(null);
					p.getInventory().setLeggings(null);
					p.getInventory().setBoots(null);
					p.updateInventory();
				}
			}, 50L);

		}
	}

	@EventHandler
	public void onPlayerDamageSponge(EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		Player p = (Player) e.getEntity();

		if ((e.getCause() == EntityDamageEvent.DamageCause.FALL) && (this.fall.contains(p.getName()))) {
			this.fall.remove(p.getName());
			e.setDamage(4.0D);
			return;
		}
	}
}