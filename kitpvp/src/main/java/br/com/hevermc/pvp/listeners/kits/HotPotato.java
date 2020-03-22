package br.com.hevermc.pvp.listeners.kits;

import java.util.ArrayList;
import java.util.Calendar;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.hevermc.commons.bukkit.api.ItemConstructor;
import br.com.hevermc.pvp.KitPvP;
import br.com.hevermc.pvp.api.PlayerLoader;
import br.com.hevermc.pvp.enums.Kits;
import br.com.hevermc.pvp.listeners.kits.commons.HeverKit;

public class HotPotato implements Listener {

	HeverKit kit = new HeverKit(Kits.HOTPOTATO);
	ArrayList<String> cooldown2 = new ArrayList<>();

	@EventHandler
	public void onInteract(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		if (e.getRightClicked() instanceof Player) {
			kit.setPlayer(p);
			Player k = (Player) e.getRightClicked();
			if (kit.isItem() && kit.usingKit()) {
				if (new PlayerLoader(k).load().getPvPP().isProtectArea()) {
					return;
				}
				if (kit.verifyCooldown() == true) {
					p.sendMessage("§e§lKIT §fAguarde, você está em §4§lCOOLDOWN§f!");
				} else {
					Calendar c = Calendar.getInstance();
					c.add(Calendar.SECOND, 20);
					kit.setCooldown(c.getTime());
					cooldown2.add(k.getName());
					p.sendMessage("§e§lKIT §fVocê §a§lUSOU§f sua batata!");

					k.sendMessage("§e§lKIT §fHá uma §c§lTNT§f em sua cabeça!");
					k.getInventory()
							.setHelmet(new ItemConstructor(new ItemStack(Material.TNT), "§cHotPotato").create());
					new BukkitRunnable() {

						@Override
						public void run() {
							if (cooldown2.contains(k.getName())) {
								k.sendMessage("§cTNT explodindo em 5 segundos!");
							}

						}
					}.runTaskLater(KitPvP.getInstance(), 20L);

					new BukkitRunnable() {

						@Override
						public void run() {
							if (cooldown2.contains(k.getName())) {

								k.sendMessage("§cTNT explodindo em 4 segundos!");
							}

						}
					}.runTaskLater(KitPvP.getInstance(), 40L);

					new BukkitRunnable() {

						@Override
						public void run() {
							if (cooldown2.contains(k.getName())) {

								k.sendMessage("§cTNT explodindo em 3 segundos!");
							}

						}
					}.runTaskLater(KitPvP.getInstance(), 60L);

					new BukkitRunnable() {

						@Override
						public void run() {
							if (cooldown2.contains(k.getName())) {
								k.sendMessage("§cTNT explodindo em 2 segundos!");
							}

						}
					}.runTaskLater(KitPvP.getInstance(), 80L);

					new BukkitRunnable() {

						@Override
						public void run() {
							if (cooldown2.contains(k.getName())) {
								k.sendMessage("§cTNT explodindo em 1 segundo!");
							}

						}
					}.runTaskLater(KitPvP.getInstance(), 100L);

					new BukkitRunnable() {
						@Override
						public void run() {
							if (cooldown2.contains(k.getName())
									&& !new PlayerLoader(p).load().getPvPP().isProtectArea()) {
								k.getWorld().createExplosion(k.getLocation(), 3.0F, true);
								k.getWorld().playEffect(k.getLocation(), Effect.EXPLOSION_HUGE, 20);
								k.getWorld().playEffect(k.getLocation(), Effect.EXPLOSION_LARGE, 20, 20);
								k.sendMessage("§e§lKIT §fA sua §4§lTNT §fda sua cabeça §c§lEXPLODIU§f!");
								k.getInventory().setHelmet(null);
								k.setLastDamage(9999.0D);
								cooldown2.remove(k.getName());
							}
						}
					}.runTaskLater(KitPvP.getInstance(), 120L);
				}
			}
		}
	}

	@EventHandler
	public void onRemoverTNT(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		kit.setPlayer(p);
		if (!kit.usingKit()) {
			if (e.getSlot() == 39 && e.getCurrentItem().getType().equals(Material.TNT)) {
				if (cooldown2.contains(p.getName())) {
					cooldown2.remove(p.getName());
					p.playSound(p.getLocation(), Sound.FIRE_IGNITE, 2.0F, 2.0F);
					p.getInventory().setHelmet(null);
					e.setCancelled(true);
				}
			}
		}
	}

}
