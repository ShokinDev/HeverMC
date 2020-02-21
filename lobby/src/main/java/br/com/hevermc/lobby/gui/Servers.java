package br.com.hevermc.lobby.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import br.com.hevermc.commons.bukkit.api.ItemConstructor;
import br.com.hevermc.lobby.Lobby;
import br.com.hevermc.lobby.score.BungeeChannelApi;

public class Servers {

	public Servers(Player p) {
		Inventory inv = Bukkit.createInventory(null, 3 * 9, "§eServidores");
		inv.setItem(11,
				new ItemConstructor(new ItemStack(Material.DIAMOND_SWORD), "§aKitPvP",
						Arrays.asList("", " §fModo §a§lKITPVP §fcom §6SIMULATOR§f, ",
								" §ftreine suas habilidade com nossa warp §c§lLAVA§F!"," ",
								"§e... §fjogando agora!")).create());
		new BungeeChannelApi(Lobby.getInstance()).getPlayerCount("kitpvp").whenComplete((result, error) -> {
			inv.setItem(11,
					new ItemConstructor(new ItemStack(Material.DIAMOND_SWORD), "§aKitPvP",
							Arrays.asList("", " §fModo §a§lKITPVP §fcom §6SIMULATOR§f, ",
									" §ftreine suas habilidade com nossa warp §c§lLAVA§F!"," ",
									"§e" + result.intValue() + " §fjogando agora!")).create());
		});
		inv.setItem(13,
				new ItemConstructor(new ItemStack(Material.IRON_FENCE), "§cGladiator",
						Arrays.asList(" ", " §fModo §a§lGLADIATOR§f, este modo ",
								" §festá em §c§lDESENVOLVIMENTO§f, aguarde para jogar.", " ")).create());
		inv.setItem(15,
				new ItemConstructor(new ItemStack(Material.MUSHROOM_SOUP), "§cDoubleKit",
						Arrays.asList(" ", " §fModo §a§lDOUBLEKIT§f, este modo ",
								" §festá em §c§lDESENVOLVIMENTO§f, aguarde para jogar.", " ")).create());

		p.openInventory(inv);
	}

}
