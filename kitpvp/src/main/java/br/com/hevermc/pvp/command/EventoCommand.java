package br.com.hevermc.pvp.command;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.hevermc.commons.bukkit.Commons;
import br.com.hevermc.commons.bukkit.account.HeverPlayer;
import br.com.hevermc.commons.bukkit.command.commons.HeverCommand;
import br.com.hevermc.commons.enums.Groups;
import br.com.hevermc.pvp.KitPvP;
import br.com.hevermc.pvp.api.PlayerLoader;
import br.com.hevermc.pvp.api.PvPPlayer;
import br.com.hevermc.pvp.api.WarpsAPI;
import br.com.hevermc.pvp.enums.Kits;
import br.com.hevermc.pvp.enums.Warps;
import br.com.hevermc.pvp.listeners.GeneralListener;
import br.com.hevermc.pvp.score.ScoreboardManager;

public class EventoCommand extends HeverCommand {

	public EventoCommand() {
		super("evento");
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (isPlayer(sender)) {
			Player p = toPlayer(sender);
			PvPPlayer pvp = new PlayerLoader(p).load().getPvPP();
			if (hasGroup(p, Groups.MODPLUS, true)) {
				if (args.length == 0) {
					if (KitPvP.getManager().startedEvent == false) {
						p.sendMessage("�aVoc� deve utilizar �e/evento <on|ir>");
					} else {
						p.sendMessage("�aVoc� deve utilizar �e/evento <start|finish|build>");
					}
				} else {
					if (args[0].equalsIgnoreCase("on")) {
						if (KitPvP.getManager().eventOcurring == true) {
							p.sendMessage("�cO evento j� est� ativo!");
						} else {
							p.sendMessage("�eVoc� ativou o evento!");
							pvp.setWarp(Warps.EVENTO);
							KitPvP.getManager().startedEvent = true;
							KitPvP.getManager().eventOcurring = false;
							KitPvP.getManager().joinInEvent = true;
							Commons.getManager().getBungeeChannel().sendPluginMessage(p, "kitpvpEvent", "BungeeCord");
						}
					} else if (args[0].equalsIgnoreCase("start")) {

						KitPvP.getManager().eventOcurring = true;
						KitPvP.getManager().joinInEvent = false;
						p.sendMessage("�aVoc� iniciou o evento!");
						Bukkit.broadcastMessage("�6�lHEVERMC �7� �fO evento iniciou.");

						Bukkit.getOnlinePlayers().forEach(ps -> {
							if (KitPvP.getManager().inEvent.contains(ps)) {
								PvPPlayer psvp = new PlayerLoader(ps).load().getPvPP();
								psvp.setProtectArea(false);
								ps.teleport(new WarpsAPI(Warps.EVENTO).getLocation());
							}
						});

					} else if (args[0].equalsIgnoreCase("build")) {
						if (KitPvP.getManager().eventOcurring == false) {
							p.sendMessage("�cVoc� s� pode executar esse comando com o evento ativado!");
						} else {
							KitPvP.getManager().buildInEvent = !KitPvP.getManager().buildInEvent;
							p.sendMessage("�aVoc� " + (KitPvP.getManager().buildInEvent ? "�eativou" : "�cdesativou")
									+ " �aa constru��o no evento!");
						}
					} else if (args[0].equalsIgnoreCase("ir")) {
						p.teleport(new WarpsAPI(Warps.EVENTO).getLocation());
						p.sendMessage("�aVoc� entrou na warp �eevento�a!");
						KitPvP.getManager().inEvent.add(p);
						p.getInventory().clear();
						new ScoreboardManager().build(p);
						p.setHealth(20);
						pvp.setCombat(false);
						pvp.setInCombat(null);
						pvp.setKit(Kits.NENHUM);
						pvp.setProtectArea(true);
						pvp.setWarp(Warps.EVENTO);
					} else if (args[0].equalsIgnoreCase("mod")) {
						p.teleport(new WarpsAPI(Warps.EVENTO).getLocation());
						p.chat("/admin");
						p.sendMessage("�aVoc� entrou na warp �eevento�a!");
						pvp.setWarp(Warps.EVENTO);
					} else if (args[0].equalsIgnoreCase("finish")) {
						if (KitPvP.getManager().eventOcurring == false) {
							p.sendMessage("�cO evento n�o est� ocorrendo");
						} else {
							KitPvP.getManager().buildInEvent = false;
							KitPvP.getManager().eventOcurring = false;
							KitPvP.getManager().joinInEvent = false;
							KitPvP.getManager().startedEvent = false;
							if (KitPvP.getManager().inEvent.size() == 1) {
								int i = 0;
								for (Block b : GeneralListener.blocks) {
									i++;
									b.setType(Material.AIR);
									GeneralListener.blocks.remove(i);
								}
								Player winner = KitPvP.getManager().inEvent.get(0);
								winner.sendMessage(
										"�aVoc� ganhou o evento, adicionamos mais �e50 �axps e �e100 �ade cash em sua conta!");
								HeverPlayer whp = toHeverPlayer(winner);
								whp.setXp(whp.getXp() + 50);
								whp.setCash(whp.getCash() + 100);
								winner.teleport(winner.getWorld().getSpawnLocation());
								new PlayerLoader(winner).load().getPvPP().setWarp(Warps.SPAWN);
								new PlayerLoader(p).load().getPvPP().setKit(Kits.NENHUM);
								winner.chat("/spawn");
								KitPvP.getManager().inEvent.remove(winner);
								Bukkit.broadcastMessage(
										"�aO evento foi finalizado, �e" + winner.getName() + " ganhou!");
							} else {
								for (int i = 0; i < KitPvP.getManager().inEvent.size(); i++) {
									for (Block b : GeneralListener.blocks) {
										b.setType(Material.AIR);
									}
									Player ps = KitPvP.getManager().inEvent.get(i);
									ps.sendMessage("�cO evento foi finalizado.");
									KitPvP.getManager().inEvent.remove(ps);
									ps.chat("/spawn");
								}
							}
						}

						p.sendMessage("�cVoc� finalizou o evento!");
					}
				}
			} else {
				if (pvp.isCombat()) {
					p.sendMessage("�cVoc� est� em combate!");

				} else {
					if (KitPvP.getManager().startedEvent == false && KitPvP.getManager().eventOcurring == false) {
						p.sendMessage("�cN�o h� nenhum evento ativo no momento");
					} else {
						if (KitPvP.getManager().joinInEvent == false) {
							p.teleport(new WarpsAPI(Warps.SPECEVENTO).getLocation());
							p.sendMessage("�eO evento j� �ainiciou�e, voc� est� na sala de espectadores!");
							pvp.setWarp(Warps.SPECEVENTO);
							p.getInventory().clear();
							new ScoreboardManager().build(p);
							p.setHealth(20);
							pvp.setCombat(false);
							pvp.setInCombat(null);
							pvp.setKit(Kits.NENHUM);
							pvp.setProtectArea(true);
							KitPvP.getManager().specEvent.add(p);
						} else {
							p.teleport(new WarpsAPI(Warps.EVENTO).getLocation());
							p.sendMessage("�aVoc� entrou na warp �eevento�a!");
							KitPvP.getManager().inEvent.add(p);
							p.getInventory().clear();
							new ScoreboardManager().build(p);
							p.setHealth(20);
							pvp.setCombat(false);
							pvp.setInCombat(null);
							pvp.setKit(Kits.NENHUM);
							pvp.setProtectArea(true);
							pvp.setWarp(Warps.EVENTO);
						}
					}
				}
			}
		}
		return false;
	}

}
