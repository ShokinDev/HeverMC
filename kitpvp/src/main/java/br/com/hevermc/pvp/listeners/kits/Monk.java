package br.com.hevermc.pvp.listeners.kits;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import br.com.hevermc.pvp.enums.Kits;
import br.com.hevermc.pvp.listeners.kits.commons.HeverKit;

public class Monk implements Listener {

	HeverKit kit_api = new HeverKit(Kits.MONK);

	@EventHandler
	public void monk(PlayerInteractEntityEvent event) {
		Player p = event.getPlayer();
		kit_api.setPlayer(p);
		if (event.getRightClicked() instanceof Player) {
			if (kit_api.usingKit() && kit_api.isItem()) {
				Player target = (Player) event.getRightClicked();
				if (kit_api.verifyCooldown() == false) {
					Calendar c = Calendar.getInstance();
					c.add(Calendar.SECOND, 15);
					kit_api.setCooldown(new Date(c.getTimeInMillis()));
					int a = new Random().nextInt(target.getInventory().getSize());
					ItemStack b = target.getItemInHand();
					target.getInventory().setItemInHand(target.getInventory().getItem(a));
					target.getInventory().setItem(a,
							b);
					p.sendMessage("§aVocê §abagunçou §ao inventario de §e" + target.getName() + "§a!");
					target.sendMessage("§cSeu inventario foi bagunçado por §4" + p.getName() + "§c!");
				} else {
					p.sendMessage("§cAguarde, você está em cooldown!");
				}
			}

		}
	}

}
