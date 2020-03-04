package br.com.hevermc.commons.bungee.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import br.com.hevermc.commons.backend.mysql.SQLManager;
import br.com.hevermc.commons.bungee.Commons;
import br.com.hevermc.commons.bungee.account.HeverPlayer;
import br.com.hevermc.commons.bungee.command.common.HeverCommand;
import br.com.hevermc.commons.clan.Hierarchy;
import br.com.hevermc.commons.enums.Groups;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ClanCommand extends HeverCommand {

	public ClanCommand() {
		super("clan");
	}

	public ArrayList<String> inviting = new ArrayList<String>();
	public HashMap<String, String> isInvited = new HashMap<String, String>();
	public ArrayList<UUID> coowdownReport = new ArrayList<>();

	public void addCoowdown(ProxiedPlayer proxiedPlayer) {
		coowdownReport.add(proxiedPlayer.getUniqueId());
		ProxyServer.getInstance().getScheduler().schedule(Commons.getInstance(), new Runnable() {
			public void run() {
				coowdownReport.remove(proxiedPlayer.getUniqueId());
			}
		}, 30, TimeUnit.SECONDS);
	}

	public boolean inCoowdown(ProxiedPlayer proxiedPlayer) {
		return coowdownReport.contains(proxiedPlayer.getUniqueId());
	}

	public int vagaInt(String clanName) {
		SQLManager s = Commons.getManager().getBackend().getSql();
		if (s.getString("clans", "name", "member1", clanName).equalsIgnoreCase("Ninguem")) {
			return 1;
		} else if (s.getString("clans", "name", "member2", clanName).equalsIgnoreCase("Ninguem")) {
			return 2;
		} else if (s.getString("clans", "name", "member3", clanName).equalsIgnoreCase("Ninguem")) {
			return 3;
		} else if (s.getString("clans", "name", "member4", clanName).equalsIgnoreCase("Ninguem")) {
			return 4;
		} else if (s.getString("clans", "name", "member5", clanName).equalsIgnoreCase("Ninguem")) {
			return 5;
		}
		return 0;
	}

	public boolean vagafAdmin(String clanName) {
		SQLManager s = Commons.getManager().getBackend().getSql();
		if (s.getString("clans", "name", "admin1", clanName).equalsIgnoreCase("Ninguem")) {
			return true;
		} else if (s.getString("clans", "name", "admin2", clanName).equalsIgnoreCase("Ninguem")) {
			return true;
		}
		return false;
	}

	public boolean vaga(String clanName) {
		SQLManager s = Commons.getManager().getBackend().getSql();
		if (s.getString("clans", "name", "member1", clanName).equalsIgnoreCase("Ninguem")) {
			return true;
		} else if (s.getString("clans", "name", "member2", clanName).equalsIgnoreCase("Ninguem")) {
			return true;
		} else if (s.getString("clans", "name", "member3", clanName).equalsIgnoreCase("Ninguem")) {
			return true;
		} else if (s.getString("clans", "name", "member4", clanName).equalsIgnoreCase("Ninguem")) {
			return true;
		} else if (s.getString("clans", "name", "member5", clanName).equalsIgnoreCase("Ninguem")) {
			return true;
		}
		return false;
	}

	public void accept() {

	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (isPlayer(sender)) {
			if (requiredGroup(toPlayer(sender), Groups.DONO, false)) {

				HeverPlayer hp = toHeverPlayer(toPlayer(sender));
				ProxiedPlayer p = toPlayer(sender);
				if (args.length == 0) {
					if (hp.getClan() == null) {
						p.sendMessage(TextComponent
								.fromLegacyText("§eVocê não possui/está em uma clan, comandos disponíveis: "));
						p.sendMessage(TextComponent.fromLegacyText(" "));
						p.sendMessage(TextComponent
								.fromLegacyText("§a/clan create (nome) (tag) §7- Você precisa de §3200 de cash§7!"));
						p.sendMessage(TextComponent.fromLegacyText("§a/clan top §7- Você vê os top clans!"));
						p.sendMessage(TextComponent
								.fromLegacyText("§a/clan info (nome) §7- Você vê as informações de uma clan!"));
						p.sendMessage(TextComponent
								.fromLegacyText("§a/clan accept (nome) §7- Você aceita um convite para clan!"));
						p.sendMessage(TextComponent.fromLegacyText(" "));
					} else {
						if (hp.getClan_hierarchy() == Hierarchy.MEMBER) {
							p.sendMessage(TextComponent
									.fromLegacyText("§eVocê é membro de uma clan, comandos disponíveis: "));
							p.sendMessage(TextComponent.fromLegacyText(" "));
							p.sendMessage(TextComponent
									.fromLegacyText("§a/clan sair §7- Você precisa de sai de sua clan atual§7!"));
							p.sendMessage(TextComponent.fromLegacyText("§a/clan top §7- Você vê os top clans!"));
							p.sendMessage(TextComponent
									.fromLegacyText("§a/clan info §7- Você vê as informações da sua clan!"));
							p.sendMessage(TextComponent
									.fromLegacyText("§a/clan info (nome) §7- Você vê as informações de uma clan!"));
							p.sendMessage(TextComponent.fromLegacyText(" "));
						} else if (hp.getClan_hierarchy() == Hierarchy.ADMIN) {
							p.sendMessage(
									TextComponent.fromLegacyText("§eVocê é admin de uma clan, comandos disponíveis: "));
							p.sendMessage(TextComponent.fromLegacyText(" "));
							p.sendMessage(TextComponent
									.fromLegacyText("§a/clan sair §7- Você precisa de sai de sua clan atual§7!"));
							p.sendMessage(TextComponent.fromLegacyText("§a/clan top §7- Você vê os top clans!"));
							p.sendMessage(TextComponent
									.fromLegacyText("§a/clan info §7- Você vê as informações da sua clan!"));
							p.sendMessage(TextComponent
									.fromLegacyText("§a/clan info (nome) §7- Você vê as informações de uma clan!"));
							p.sendMessage(TextComponent
									.fromLegacyText("§a/clan info (nome) §7- Você vê as informações de uma clan!"));
							p.sendMessage(TextComponent
									.fromLegacyText("§a/clan kick (member) §7- Você expulsa um membro da sua clan!"));
							p.sendMessage(TextComponent.fromLegacyText(
									"§a/clan invite (member) §7- Você convida um membro para sua clan!"));
							p.sendMessage(TextComponent.fromLegacyText(" "));
						} else if (hp.getClan_hierarchy() == Hierarchy.OWNER) {
							p.sendMessage(
									TextComponent.fromLegacyText("§eVocê é admin de uma clan, comandos disponíveis: "));
							p.sendMessage(TextComponent.fromLegacyText(" "));
							p.sendMessage(TextComponent
									.fromLegacyText("§a/clan sair §7- Você precisa de sai de sua clan atual§7!"));
							p.sendMessage(TextComponent.fromLegacyText("§a/clan top §7- Você vê os top clans!"));
							p.sendMessage(TextComponent
									.fromLegacyText("§a/clan info §7- Você vê as informações da sua clan!"));
							p.sendMessage(TextComponent
									.fromLegacyText("§a/clan info (nome) §7- Você vê as informações de uma clan!"));
							p.sendMessage(TextComponent
									.fromLegacyText("§a/clan info (nome) §7- Você vê as informações de uma clan!"));
							p.sendMessage(TextComponent
									.fromLegacyText("§a/clan kick (member) §7- Você expulsa um membro da sua clan!"));
							p.sendMessage(TextComponent.fromLegacyText(
									"§a/clan invite (member) §7- Você convida um membro para sua clan!"));
							p.sendMessage(TextComponent.fromLegacyText(
									"§a/clan promote (member) §7- Você promove um membro da sua clan!"));
							p.sendMessage(TextComponent
									.fromLegacyText("§a/clan demote (member) §7- Você rebaixa um membro da sua clan!"));
							p.sendMessage(TextComponent.fromLegacyText("§a/clan delete §7- Você deleta sua clan!"));
							p.sendMessage(TextComponent.fromLegacyText(" "));
						}
					}
				} else {
					if (args[0].equalsIgnoreCase("top")) {
						if (inCoowdown(p)) {
							p.sendMessage(TextComponent
									.fromLegacyText("§aClassificação top 10 de clans: §3(Ordenado pelo xp)"));
							p.sendMessage(TextComponent.fromLegacyText(" "));
							List<String> top = Commons.getManager().getBackend().getSql().getTopFive("clans", "xp");
							for (int i = 0; i < 10; i++) {
								p.sendMessage(TextComponent
										.fromLegacyText(top.get(i) != null ? top.get(i) : i + "° - Nenhuma"));
							}
							p.sendMessage(TextComponent.fromLegacyText(" "));
							p.sendMessage(TextComponent
									.fromLegacyText("§eOBS: O xp da clan é igual a soma de xp dos membros da mesma!"));
							p.sendMessage(TextComponent.fromLegacyText(" "));
							addCoowdown(p);
						} else {
							p.sendMessage(TextComponent.fromLegacyText("§cAguarde para ver o toprank novamente."));
						}
					} else if (args[0].equalsIgnoreCase("info")) {
						if (hp.getClan() == null) {
							if (args.length == 1) {
								p.sendMessage(TextComponent
										.fromLegacyText("§a/clan info (nome) §7- Você vê as informações de uma clan!"));
							} else {
								if (inCoowdown(p)) {
									String claninfo = args[1];
									if (Commons.getManager().getBackend().getSql().checkString("clans", "name",
											claninfo)) {
										String namec = claninfo;
										String owner = Commons.getManager().getBackend().getSql().getString("clans",
												"name", "owner", claninfo);
										String admin1 = Commons.getManager().getBackend().getSql().getString("clans",
												"name", "admin1", claninfo);
										String admin2 = Commons.getManager().getBackend().getSql().getString("clans",
												"name", "admin2", claninfo);
										String member1 = Commons.getManager().getBackend().getSql().getString("clans",
												"name", "member1", claninfo);
										String member2 = Commons.getManager().getBackend().getSql().getString("clans",
												"name", "member2", claninfo);
										String member3 = Commons.getManager().getBackend().getSql().getString("clans",
												"name", "member3", claninfo);
										String member4 = Commons.getManager().getBackend().getSql().getString("clans",
												"name", "member4", claninfo);
										String member5 = Commons.getManager().getBackend().getSql().getString("clans",
												"name", "member5", claninfo);
										p.sendMessage(TextComponent
												.fromLegacyText("§aInformações sobre a clan §e" + namec + "§a:"));
										p.sendMessage(TextComponent.fromLegacyText(""));
										p.sendMessage(TextComponent.fromLegacyText("§4Dono: §a" + owner + "!"));
										p.sendMessage(TextComponent
												.fromLegacyText("§cAdmins: §a" + admin1 + ", " + admin2 + "!"));
										p.sendMessage(TextComponent.fromLegacyText(""));
										p.sendMessage(TextComponent.fromLegacyText("§7Membros: §a" + member1 + ", "
												+ member2 + ", " + member3 + ", " + member4 + ", " + member5));
										p.sendMessage(TextComponent.fromLegacyText(""));
										p.sendMessage(TextComponent.fromLegacyText("§fXP: " + hp.getClan().getXp()));
										p.sendMessage(TextComponent.fromLegacyText(""));
									} else if (Commons.getManager().getBackend().getSql().checkString("clans", "tag",
											claninfo)) {
										String namec = claninfo;
										String owner = Commons.getManager().getBackend().getSql().getString("clans",
												"tag", "owner", claninfo);
										String admin1 = Commons.getManager().getBackend().getSql().getString("clans",
												"tag", "admin1", claninfo);
										String admin2 = Commons.getManager().getBackend().getSql().getString("clans",
												"tag", "admin2", claninfo);
										String member1 = Commons.getManager().getBackend().getSql().getString("clans",
												"tag", "member1", claninfo);
										String member2 = Commons.getManager().getBackend().getSql().getString("clans",
												"tag", "member2", claninfo);
										String member3 = Commons.getManager().getBackend().getSql().getString("clans",
												"tag", "member3", claninfo);
										String member4 = Commons.getManager().getBackend().getSql().getString("clans",
												"tag", "member4", claninfo);
										String member5 = Commons.getManager().getBackend().getSql().getString("clans",
												"tag", "member5", claninfo);
										p.sendMessage(TextComponent
												.fromLegacyText("§aInformações sobre a clan §e" + namec + "§a:"));
										p.sendMessage(TextComponent.fromLegacyText(""));
										p.sendMessage(TextComponent.fromLegacyText("§4Dono: §a" + owner + "!"));
										p.sendMessage(TextComponent
												.fromLegacyText("§cAdmins: §a" + admin1 + ", " + admin2 + "!"));
										p.sendMessage(TextComponent.fromLegacyText(""));
										p.sendMessage(TextComponent.fromLegacyText("§7Membros: §a" + member1 + ", "
												+ member2 + ", " + member3 + ", " + member4 + ", " + member5));
										p.sendMessage(TextComponent.fromLegacyText(""));
										p.sendMessage(TextComponent.fromLegacyText("§fXP: " + hp.getClan().getXp()));
										p.sendMessage(TextComponent.fromLegacyText(""));

									} else {
										p.sendMessage(TextComponent.fromLegacyText("§cEssa clan não existe!"));
									}
									addCoowdown(p);
								}
							}
						} else {
							if (args.length == 1) {
								p.sendMessage(TextComponent.fromLegacyText(
										"§aInformações sobre a clan §e" + hp.getClan().getName() + "§a:"));
								p.sendMessage(TextComponent.fromLegacyText(""));
								p.sendMessage(
										TextComponent.fromLegacyText("§4Dono: §a" + hp.getClan().getOwner() + "!"));
								p.sendMessage(TextComponent.fromLegacyText("§cAdmins: §a" + hp.getClan().getAdmin1()
										+ ", " + hp.getClan().getAdmin2() + "!"));
								p.sendMessage(TextComponent.fromLegacyText(""));
								p.sendMessage(TextComponent.fromLegacyText("§7Membros: §a" + hp.getClan().getMember1()
										+ ", " + hp.getClan().getMember2() + ", " + hp.getClan().getMember3() + ", "
										+ hp.getClan().getMember4() + ", " + hp.getClan().getMember5()));
								p.sendMessage(TextComponent.fromLegacyText(""));
								p.sendMessage(TextComponent.fromLegacyText("§fXP: " + hp.getClan().getXp()));

							} else {
								if (inCoowdown(p)) {
									String claninfo = args[1];
									if (Commons.getManager().getBackend().getSql().checkString("clans", "name",
											claninfo)) {
										String namec = claninfo;
										String owner = Commons.getManager().getBackend().getSql().getString("clans",
												"name", "owner", claninfo);
										String admin1 = Commons.getManager().getBackend().getSql().getString("clans",
												"name", "admin1", claninfo);
										String admin2 = Commons.getManager().getBackend().getSql().getString("clans",
												"name", "admin2", claninfo);
										String member1 = Commons.getManager().getBackend().getSql().getString("clans",
												"name", "member1", claninfo);
										String member2 = Commons.getManager().getBackend().getSql().getString("clans",
												"name", "member2", claninfo);
										String member3 = Commons.getManager().getBackend().getSql().getString("clans",
												"name", "member3", claninfo);
										String member4 = Commons.getManager().getBackend().getSql().getString("clans",
												"name", "member4", claninfo);
										String member5 = Commons.getManager().getBackend().getSql().getString("clans",
												"name", "member5", claninfo);
										p.sendMessage(TextComponent
												.fromLegacyText("§aInformações sobre a clan §e" + namec + "§a:"));
										p.sendMessage(TextComponent.fromLegacyText(""));
										p.sendMessage(TextComponent.fromLegacyText("§4Dono: §a" + owner + "!"));
										p.sendMessage(TextComponent
												.fromLegacyText("§cAdmins: §a" + admin1 + ", " + admin2 + "!"));
										p.sendMessage(TextComponent.fromLegacyText(""));
										p.sendMessage(TextComponent.fromLegacyText("§7Membros: §a" + member1 + ", "
												+ member2 + ", " + member3 + ", " + member4 + ", " + member5));
										p.sendMessage(TextComponent.fromLegacyText(""));
										p.sendMessage(TextComponent.fromLegacyText("§fXP: " + hp.getClan().getXp()));
										p.sendMessage(TextComponent.fromLegacyText(""));
									} else if (Commons.getManager().getBackend().getSql().checkString("clans", "tag",
											claninfo)) {
										String namec = claninfo;
										String owner = Commons.getManager().getBackend().getSql().getString("clans",
												"tag", "owner", claninfo);
										String admin1 = Commons.getManager().getBackend().getSql().getString("clans",
												"tag", "admin1", claninfo);
										String admin2 = Commons.getManager().getBackend().getSql().getString("clans",
												"tag", "admin2", claninfo);
										String member1 = Commons.getManager().getBackend().getSql().getString("clans",
												"tag", "member1", claninfo);
										String member2 = Commons.getManager().getBackend().getSql().getString("clans",
												"tag", "member2", claninfo);
										String member3 = Commons.getManager().getBackend().getSql().getString("clans",
												"tag", "member3", claninfo);
										String member4 = Commons.getManager().getBackend().getSql().getString("clans",
												"tag", "member4", claninfo);
										String member5 = Commons.getManager().getBackend().getSql().getString("clans",
												"tag", "member5", claninfo);
										p.sendMessage(TextComponent
												.fromLegacyText("§aInformações sobre a clan §e" + namec + "§a:"));
										p.sendMessage(TextComponent.fromLegacyText(""));
										p.sendMessage(TextComponent.fromLegacyText("§4Dono: §a" + owner + "!"));
										p.sendMessage(TextComponent
												.fromLegacyText("§cAdmins: §a" + admin1 + ", " + admin2 + "!"));
										p.sendMessage(TextComponent.fromLegacyText(""));
										p.sendMessage(TextComponent.fromLegacyText("§7Membros: §a" + member1 + ", "
												+ member2 + ", " + member3 + ", " + member4 + ", " + member5));
										p.sendMessage(TextComponent.fromLegacyText(""));
										p.sendMessage(TextComponent.fromLegacyText("§fXP: " + hp.getClan().getXp()));
										p.sendMessage(TextComponent.fromLegacyText(""));

									} else {
										p.sendMessage(TextComponent.fromLegacyText("§cEssa clan não existe!"));
									}

									addCoowdown(p);
								}

							}

						}
					} else if (args[0].equalsIgnoreCase("create")) {
						if (args.length < 3) {
							p.sendMessage(TextComponent.fromLegacyText(
									"§a/clan create (nome) (tag) §7- Você precisa de §3200 de cash§7!"));
							return;
						}
						String name = args[1];
						String tag = args[2];
						if (hp.getClan() != null) {
							p.sendMessage(TextComponent.fromLegacyText("§cVocê já está em um clan!"));
						} else {

							if (hp.getCash() < 300) {
								p.sendMessage(TextComponent
										.fromLegacyText("§cVocê precisa de §3300 cash's§c para criar um clan!"));
								return;
							}
							if (name.length() > 12) {
								p.sendMessage(TextComponent
										.fromLegacyText("§co nome do clan não pode passar de 12 caracteries!"));
								return;
							}
							if (tag.contains("%")) {
								p.sendMessage(TextComponent.fromLegacyText("§cA tag do clan é invalida!"));
								return;
							}
							if (tag.length() != 3) {
								p.sendMessage(TextComponent.fromLegacyText("§cA tag do clan deve ter 3 caracteries!"));
								return;
							}
							if (!(Commons.getManager().getBackend().getSql().checkString("clans", "name", name)
									|| Commons.getManager().getBackend().getSql().checkString("clans", "tag", tag))) {
								p.sendMessage(TextComponent.fromLegacyText("§cEsta clan já existe!"));
							} else {
								Commons.getManager().getBackend().getSql().createClan(name, p.getName().toLowerCase(),
										tag, hp.getXp());
								hp.clan();
								hp.setCash(hp.getCash() - 300);
								Commons.getManager().getBackend().getRedis().set(p.getName().toLowerCase() + ":update",
										"sla");
								p.sendMessage(TextComponent.fromLegacyText(
										"§aVocê criou a clan §3" + name + " §7[" + tag.toUpperCase() + "]"));
							}
						}
					} else if (args[0].equalsIgnoreCase("invite")) {
						if (hp.getClan() != null) {
							ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[1]);
							if (hp.getClan_hierarchy() == Hierarchy.ADMIN
									|| hp.getClan_hierarchy() == Hierarchy.OWNER) {
								if (!!inviting.contains(hp.getClan().getName())) {
									if (!vaga(hp.getClan().getName())) {
										p.sendMessage(
												TextComponent.fromLegacyText("§cSua clan já está lotada! §7(5/5)"));
										return;
									}

									p.sendMessage(TextComponent.fromLegacyText(
											"§aVocê convidou o jogador " + target.getName() + " para seu clan."));

									isInvited.put(hp.getClan().getName(), target.getName());
									inviting.add(target.getName()); // clan accept nome
									inviting.add(hp.getClan().getName());
									target.sendMessage(TextComponent.fromLegacyText("§aVocê foi convidado para o clan "
											+ hp.getClan().getName() + " pelo jogador " + p.getName() + "."));
									target.sendMessage(TextComponent.fromLegacyText(
											"§aPara entrar digite /clan entrar " + hp.getClan().getName() + "."));
									Commons.getInstance().getProxy().getScheduler().schedule(Commons.getInstance(),
											new Runnable() {

												@Override
												public void run() {
													if (inviting.contains(hp.getClan().getName())
															&& isInvited.containsKey(hp.getClan().getName())) {
														inviting.remove(hp.getClan().getName());
														isInvited.remove(hp.getClan().getName());
													}
												}
											}, 1, TimeUnit.MINUTES);
								} else {
									p.sendMessage(TextComponent.fromLegacyText(
											"§cSua clan já está convidando um jogador, aguarde para convidar!"));
								}

							} else {
								p.sendMessage(TextComponent.fromLegacyText("§cVocê não pode convidar para clan!"));

							}
						} else {
							p.sendMessage(TextComponent.fromLegacyText("§cVocê não tem/está em uma clan!"));
						}
					} else if (args[0].equalsIgnoreCase("accept")) {
						String name = args[1];
						if (hp.getClan() != null) {
							p.sendMessage(TextComponent.fromLegacyText("§cVocê já está em uma clan!"));
							return;
						}
						if (args.length < 2) {
							p.sendMessage(TextComponent
									.fromLegacyText("§a/clan accept (nome) §7- Você aceita um convite para clan!"));
						} else {
							if (isInvited.containsKey(name) && isInvited.get(name).equalsIgnoreCase(p.getName())) {
								Commons.getManager().getBackend().getSql().updateString("clans",
										"member" + vagaInt(name), "name", p.getName(), name);
								p.sendMessage(TextComponent.fromLegacyText("§aVocê aceitou este convite!"));
								isInvited.remove(name);
								inviting.remove(name);
							} else {
								p.sendMessage(TextComponent.fromLegacyText("§cConvite inexistente!"));
							}
						}
					} else if (args[0].equalsIgnoreCase("promote")) {
						
					}

				}
			}
		}

	}

}
