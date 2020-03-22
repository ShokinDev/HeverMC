package br.com.hevermc.pvp.listeners.kits;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import br.com.hevermc.pvp.enums.Kits;
import br.com.hevermc.pvp.listeners.kits.commons.HeverKit;

public class C4 implements Listener {

	public static Map<Player, Item> inbomb = new HashMap<>();
	HeverKit kit_api = new HeverKit(Kits.C4);

	@EventHandler
	public void a(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if (p.getItemInHand().getType() == Material.AIR) {
			return;
		}
		if (p.getInventory().getItemInHand().getType() != Material.SLIME_BALL) {
			return;
		}
		kit_api.setPlayer(p);
		if (!kit_api.usingKit()) {
			return;
		}
		if (e.getAction().name().contains("RIGHT")) {
			if (inbomb.containsKey(p)) {
				return;
			}
			if (kit_api.verifyCooldown() == true) {
				p.sendMessage("§e§lKIT §fAguarde, você está em §4§lCOOLDOWN§f!");
				return;
			}
			Item C4bomb = p.getWorld().dropItem(p.getEyeLocation(), new ItemStack(Material.TNT));
			C4bomb.setVelocity(e.getPlayer().getEyeLocation().getDirection().multiply(0.6D));
			C4bomb.setPickupDelay(999999);
			C4bomb.getItemStack().getItemMeta().setDisplayName("§c§lBomba");
			inbomb.put(p, C4bomb);
			Calendar c = Calendar.getInstance();
			c.add(Calendar.SECOND, 30);
			kit_api.setCooldown(c.getTime());
		} else if (inbomb.containsKey(p)) {
			Item C4bomb = (Item) inbomb.get(p);
			p.getWorld().createExplosion(C4bomb.getLocation(), 5.0F, true);
			p.getWorld().playEffect(C4bomb.getLocation(), Effect.EXPLOSION_HUGE, 20);
			p.getWorld().playEffect(C4bomb.getLocation(), Effect.EXPLOSION_LARGE, 20, 20);
			inbomb.remove(p);
			C4bomb.remove();
			p.sendMessage("§e§lKIT §fSua §c§lC4§f foi §4§lDESARMADA§f, e §c§lEXPLODIU§f!");
		} else {
			p.sendMessage("§e§lKIT §fVocê deve armar sua §c§lC4§f primeiro!");
		}
	}

	@EventHandler
	public void onExplosion(BlockExplodeEvent e) {
		e.setCancelled(true);
	}
}