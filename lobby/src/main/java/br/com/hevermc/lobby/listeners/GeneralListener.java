package br.com.hevermc.lobby.listeners;

import java.util.Arrays;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.hevermc.commons.bukkit.account.loader.PlayerLoader;
import br.com.hevermc.commons.bukkit.api.BarUtil;
import br.com.hevermc.commons.bukkit.api.ItemConstructor;
import br.com.hevermc.commons.bukkit.api.ReflectionAPI;
import br.com.hevermc.commons.enums.Groups;
import br.com.hevermc.commons.enums.Tags;
import br.com.hevermc.commons.bukkit.Commons;
import br.com.hevermc.commons.bukkit.account.HeverPlayer;

public class GeneralListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		HeverPlayer hp = new PlayerLoader(p).load().getHP();
		if (hp.groupIsLarger(Groups.PRO))
			p.sendMessage(Tags.getTags(hp.getGroup()).getPrefix() + " " + Tags.getTags(hp.getGroup()).getColor()
					+ p.getName() + " §fentrou neste §a§nlobby§f!");
		Commons.getManager().getBungeeChannel().getPlayerCount("kitpvp").whenComplete((result, error) -> {
			p.getInventory().setItem(0,
					new ItemConstructor(new ItemStack(Material.COMPASS), "§fServidores",
							Arrays.asList("", "  §fSelecione seu modo de jogo ",
									" §fpredileto e se junte a §e" + result.intValue() + " §foutros ",
									" §fjogadores que também estão se divertindo ", " §fem nosso servidor", ""))
											.create());
		});

		p.getInventory().setItem(1,
				new ItemConstructor(new ItemStack(Material.REDSTONE_COMPARATOR), "§fPreferências",
						Arrays.asList("", "  §fDeixe sua rotina no servidor mais agradavel  ",
								" §fselecionando suas §apreferências§f de acordo ", " §fcom seu propío gosto! ", " "))
										.create());

		p.getInventory().setItem(4, new ItemConstructor(new ItemStack(Material.ENDER_CHEST), "§fColetáveis",
				Arrays.asList("", "  §fPersonalize sua experiencia no servidor  ",
						" §fcom coletáveis que podem ser coletádos por ", " §ftempo de jogo ou pelo seu cargo! ", " "))
								.create());

		ItemStack is = new ItemStack(Material.SKULL);
		SkullMeta skull = (SkullMeta) is.getItemMeta();
		skull.setDisplayName("§fPerfil");
		skull.setLore(Arrays.asList("", "  §fVeja suas estatísticas e informações  ", " §fsobre sua conta no servidor ",
				" §fde uma forma fácil e prática! ", " "));
		skull.setOwner(p.getName());
		is.setItemMeta(skull);

		p.getInventory().setItem(7, is);

		p.getInventory().setItem(8, new ItemConstructor(new ItemStack(Material.INK_SACK, 1, (short) 10),
				"§cDesabilite os jogadores", Enchantment.WATER_WORKER, 5).create());
		
		
		p.sendMessage(" ");
		p.sendMessage("§6§lHEVER§F§LMC");
		p.sendMessage("  §fSeja §ebem-vindo§f ao servidor, qualquer problema consulte a §eequipe§f via Discord: §ediscord.hevermc.com.br§f, caso tenha algum problema");
		p.sendMessage(" §fcom hackers utilize o comando §c/report§f");
		p.sendMessage(" ");
		
		ReflectionAPI.sendTitle(p, "§e§lLOBBY", "§fSeja bem-vindo ao §elobby§f da rede §6Hever§fMC§f!", 10, 10, 10);
		ReflectionAPI.tab(p,
				"\n§6§lHEVER§f§lMC\n", "\n§fTwitter: §e@HeverNetwork_ §7| §fDiscord: §ediscord.hevermc.com.br §7| §fSite: §ewww.hevermc.com.br\n§fCaso tenha algum problema visite nosso §eDiscord§f!\n");
		BarUtil.setBar(p, "§a§l§k!!!§f§l VOCÊ ESTÁ CONECTADO AO §E§LLOBBY §A§L§K!!!", 100);
		p.teleport(p.getWorld().getSpawnLocation());
		new BukkitRunnable() {
			int i = 0;
			@Override
			public void run() {
				if (i == 0) {
					i++;
					BarUtil.updateText(p, "§a§l§k!!!§f§l LOJA: §E§LLOJA.HEVERMC.COM.BR §A§L§K!!!");
				} else if (i == 1) {
					i++;
					BarUtil.updateText(p, "§a§l§k!!!§f§l SITE: §E§LWWW.HEVERMC.COM.BR §A§L§K!!!");
				} else if (i == 2) {
					i++;
					BarUtil.updateText(p, "§a§l§k!!!§f§l DISCORD: §E§LDISCORD.HEVERMC.COM.BR §A§L§K!!!");
				}else if (i == 3) {
					i=0;
					BarUtil.updateText(p, "§a§l§k!!!§f§l VOCÊ ESTÁ CONECTADO AO §E§LLOBBY §A§L§K!!!");
				}
				
			}
		};
	}

}
