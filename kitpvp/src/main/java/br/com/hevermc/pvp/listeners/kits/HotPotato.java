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
					p.sendMessage("§cAguarde, você está em cooldown!");
				} else {
					Calendar c = Calendar.getInstance();
					c.add(Calendar.SECOND, 20);
					kit.setCooldown(c.getTime());
					cooldown2.add(k.getName());
					p.sendMessage("§eVocê usou sua §abatata§e!");
					k.sendMessage("§eVocê está com a §ctnt§e no seu inventario, §cexplodindo §eem §65§e segundos! (§6§lCLIQUE NELA COM O BOTÃO DIREITO§e)!");
					k.getInventory().setHelmet(new ItemConstructor(new ItemStack(Material.TNT), "§cHotPotato").create());
					new BukkitRunnable() {

						@Override
						public void run() {
							if (cooldown2.contains(k.getName())) {
								k.sendMessage("§eVocê está com a §ctnt§e no seu inventario, §cexplodindo §eem §65§e segundos! (§6§lCLIQUE NELA COM O BOTÃO DIREITO§e)!");
							}

						}
					}.runTaskLater(KitPvP.getInstance(), 20L);

					new BukkitRunnable() {

						@Override
						public void run() {
							if (cooldown2.contains(k.getName())) {
								k.sendMessage("§eVocê está com a §ctnt§e no seu inventario, §cexplodindo §eem §64§e segundos! (§6§lCLIQUE NELA COM O BOTÃO DIREITO§e)!");
							}

						}
					}.runTaskLater(KitPvP.getInstance(), 40L);

					new BukkitRunnable() {

						@Override
						public void run() {
							if (cooldown2.contains(k.getName())) {
								k.sendMessage("§eVocê está com a §ctnt§e no seu inventario, §cexplodindo §eem §63§e segundos! (§6§lCLIQUE NELA COM O BOTÃO DIREITO§e)!");
							}

						}
					}.runTaskLater(KitPvP.getInstance(), 60L);

					new BukkitRunnable() {

						@Override
						public void run() {
							if (cooldown2.contains(k.getName())) {
								k.sendMessage("§eVocê está com a §ctnt§e no seu inventario, §cexplodindo §eem §62§e segundos! (§6§lCLIQUE NELA COM O BOTÃO DIREITO§e)!");
							}

						}
					}.runTaskLater(KitPvP.getInstance(), 80L);
					
					new BukkitRunnable() {

						@Override
						public void run() {
							if (cooldown2.contains(k.getName())) {
								k.sendMessage("§eVocê está com a §ctnt§e no seu inventario, §cexplodindo §eem §61§e segundos! (§6§lCLIQUE NELA COM O BOTÃO DIREITO§e)!");
							}

						}
					}.runTaskLater(KitPvP.getInstance(), 100L);
					

					new BukkitRunnable() {
						@Override
						public void run() {
							if (cooldown2.contains(k.getName()) && !new PlayerLoader(p).load().getPvPP().isProtectArea()) {
								k.getWorld().createExplosion(k.getLocation(), 3.0F, true);
								k.getWorld().playEffect(k.getLocation(), Effect.EXPLOSION_HUGE, 20);
								k.getWorld().playEffect(k.getLocation(), Effect.EXPLOSION_LARGE, 20, 20);
								k.sendMessage("§eA §abatata§e explodiu!");
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
					p.sendMessage("§eVocê §cremoveu§e a §abatata§e!");
					p.getInventory().setHelmet(null);
					e.setCancelled(true);
				}
			}
		}
	}

}
