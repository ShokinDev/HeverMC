package br.com.hevermc.pvp.listeners.kits;

import java.util.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;
import org.bukkit.event.block.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.event.*;

import org.bukkit.metadata.*;
import org.bukkit.*;
import org.bukkit.util.*;
import org.bukkit.util.Vector;

import br.com.hevermc.pvp.KitPvP;
import br.com.hevermc.pvp.enums.Kits;
import br.com.hevermc.pvp.listeners.kits.commons.HeverKit;

import org.bukkit.event.entity.*;
import org.bukkit.potion.*;
import org.bukkit.entity.*;

public class Avatar implements Listener {
	public static List<String> arAvatar;
	public static List<String> águaAvatar;
	public static List<String> terraAvatar;
	public static List<String> fogoAvatar;
	HeverKit kit = new HeverKit(Kits.AVATAR);
	static {
		Avatar.arAvatar = new ArrayList<String>();
		Avatar.águaAvatar = new ArrayList<String>();
		Avatar.terraAvatar = new ArrayList<String>();
		Avatar.fogoAvatar = new ArrayList<String>();
	}

	@EventHandler
	public void aoAvatar(final PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		kit.setPlayer(p);
		if (kit.usingKit()) {
			final ItemStack ar = new ItemStack(Material.WOOL);
			final ItemMeta arm = ar.getItemMeta();
			arm.setDisplayName("§eAvatar§7 (§f§lAR§7)");
			ar.setItemMeta(arm);
			final ItemStack água = new ItemStack(Material.LAPIS_BLOCK);
			final ItemMeta águam = água.getItemMeta();
			águam.setDisplayName("§eAvatar§7 (§1§lÁGUA§7)");
			água.setItemMeta(águam);
			final ItemStack terra = new ItemStack(Material.GRASS);
			final ItemMeta terram = terra.getItemMeta();
			terram.setDisplayName("§eAvatar§7 (§2§lTERRA§7)");
			terra.setItemMeta(terram);
			final ItemStack fogo = new ItemStack(Material.REDSTONE_BLOCK);
			final ItemMeta fogom = fogo.getItemMeta();
			fogom.setDisplayName("§eAvatar§7 (§4§lFOGO§7)");
			fogo.setItemMeta(fogom);
			if ((e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK)
					&& p.getItemInHand().getType() == Material.BEACON) {
				p.setItemInHand(ar);
				Avatar.arAvatar.add(p.getName());
			} else if (Avatar.arAvatar.contains(p.getName())
					&& (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK)
					&& p.getItemInHand().equals((Object) ar)) {
				p.setItemInHand(água);
				Avatar.arAvatar.remove(p.getName());
				Avatar.águaAvatar.add(p.getName());
			} else if (Avatar.águaAvatar.contains(p.getName())
					&& (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK)
					&& p.getItemInHand().equals((Object) água)) {
				p.setItemInHand(terra);
				Avatar.águaAvatar.remove(p.getName());
				Avatar.terraAvatar.add(p.getName());
			} else if (Avatar.terraAvatar.contains(p.getName())
					&& (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK)
					&& p.getItemInHand().equals((Object) terra)) {
				p.setItemInHand(fogo);
				Avatar.terraAvatar.remove(p.getName());
				Avatar.fogoAvatar.add(p.getName());
			} else if (Avatar.fogoAvatar.contains(p.getName())
					&& (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK)
					&& p.getItemInHand().equals((Object) fogo)) {
				p.setItemInHand(ar);
				Avatar.fogoAvatar.remove(p.getName());
				Avatar.arAvatar.add(p.getName());
			}
		}
	}

	@EventHandler
	public void ar(final PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		kit.setPlayer(p);
		if (kit.usingKit() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
				&& p.getItemInHand().getType() == Material.WOOL) {
			e.setCancelled(true);
			p.updateInventory();
			if (kit.verifyCooldown() == true) {
				p.sendMessage("§e§lKIT §fAguarde, você está em §4§lCOOLDOWN§f!");
				return;
			}
			Calendar c = Calendar.getInstance();
			c.add(Calendar.SECOND, 30);
			kit.setCooldown(new Date(c.getTimeInMillis()));
			final Location location = p.getEyeLocation();
			final BlockIterator blocksToAdd = new BlockIterator(location, 0.0, 40);
			while (blocksToAdd.hasNext()) {
				final Location blockToAdd = blocksToAdd.next().getLocation();
				p.getWorld().playEffect(blockToAdd, Effect.STEP_SOUND, (Object) Material.WOOL, 15);
				p.playSound(blockToAdd, Sound.FIREWORK_BLAST, 3.0f, 3.0f);
			}
			final Snowball h = (Snowball) p.launchProjectile((Class<? extends Projectile>) Snowball.class);
			final Vector velo1 = p.getLocation().getDirection().normalize().multiply(10);
			h.setVelocity(velo1);
			h.setMetadata("ar", (MetadataValue) new FixedMetadataValue(KitPvP.getInstance(), (Object) true));

		}
	}

	@EventHandler
	public void danoar(final EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Snowball) {
			final Snowball s = (Snowball) e.getDamager();
			if (s.hasMetadata("ar")) {
				((LivingEntity) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 2),
						true);
				e.setDamage(e.getDamage() + 8.0);
			}
		}
	}

	@EventHandler
	public void água(final PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		kit.setPlayer(p);
		if (kit.usingKit() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
				&& p.getItemInHand().getType() == Material.LAPIS_BLOCK) {
			e.setCancelled(true);
			p.updateInventory();
			if (kit.verifyCooldown() == true) {
				p.sendMessage("§e§lKIT §fAguarde, você está em §4§lCOOLDOWN§f!");
				return;
			}
			Calendar c = Calendar.getInstance();
			c.add(Calendar.SECOND, 30);
			kit.setCooldown(new Date(c.getTimeInMillis()));
			final Location location = p.getEyeLocation();
			final BlockIterator blocksToAdd = new BlockIterator(location, 0.0, 40);
			while (blocksToAdd.hasNext()) {
				final Location blockToAdd = blocksToAdd.next().getLocation();
				p.getWorld().playEffect(blockToAdd, Effect.STEP_SOUND, (Object) Material.LAPIS_BLOCK, 15);
				p.playSound(blockToAdd, Sound.FIREWORK_BLAST, 3.0f, 3.0f);
			}
			final Snowball h = (Snowball) p.launchProjectile((Class<? extends Projectile>) Snowball.class);
			final Vector velo1 = p.getLocation().getDirection().normalize().multiply(10);
			h.setVelocity(velo1);
			h.setMetadata("água", (MetadataValue) new FixedMetadataValue(KitPvP.getInstance(), (Object) true));

		}
	}

	@EventHandler
	public void danoágua(final EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Snowball) {
			final Snowball s = (Snowball) e.getDamager();
			if (s.hasMetadata("água")) {
				((LivingEntity) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 1), true);
				e.setDamage(e.getDamage() + 8.0);
			}
		}
	}

	@EventHandler
	public void terra(final PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		kit.setPlayer(p);
		if (kit.usingKit() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
				&& p.getItemInHand().getType() == Material.GRASS) {
			e.setCancelled(true);
			p.updateInventory();
			if (kit.verifyCooldown() == true) {
				p.sendMessage("§e§lKIT §fAguarde, você está em §4§lCOOLDOWN§f!");
				return;
			}
			Calendar c = Calendar.getInstance();
			c.add(Calendar.SECOND, 30);
			kit.setCooldown(new Date(c.getTimeInMillis()));
			final Location location = p.getEyeLocation();
			final BlockIterator blocksToAdd = new BlockIterator(location, 0.0, 40);
			while (blocksToAdd.hasNext()) {
				final Location blockToAdd = blocksToAdd.next().getLocation();
				p.getWorld().playEffect(blockToAdd, Effect.STEP_SOUND, (Object) Material.GRASS, 15);
				p.playSound(blockToAdd, Sound.FIREWORK_BLAST, 3.0f, 3.0f);
			}
			final Snowball h = (Snowball) p.launchProjectile((Class<? extends Projectile>) Snowball.class);
			final Vector velo1 = p.getLocation().getDirection().normalize().multiply(10);
			h.setVelocity(velo1);
			h.setMetadata("terra", (MetadataValue) new FixedMetadataValue(KitPvP.getInstance(), (Object) true));

		}
	}

	@EventHandler
	public void danoterra(final EntityDamageByEntityEvent e) {
		final Entity Player1 = e.getEntity();
		if (e.getDamager() instanceof Snowball) {
			final Snowball Player2 = (Snowball) e.getDamager();
			if (Player2.hasMetadata("terra")) {
				e.setDamage(8.0);
				final Vector vector = Player1.getLocation().getDirection();
				vector.multiply(-3.2f);
				Player1.setVelocity(vector);
			}
		}
	}

	@EventHandler
	public void fogo(final PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		kit.setPlayer(p);
		if (kit.usingKit() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
				&& p.getItemInHand().getType() == Material.REDSTONE_BLOCK) {
			e.setCancelled(true);
			p.updateInventory();
			if (kit.verifyCooldown() == true) {
				p.sendMessage("§e§lKIT §fAguarde, você está em §4§lCOOLDOWN§f!");
				return;
			}
			Calendar c = Calendar.getInstance();
			c.add(Calendar.SECOND, 30);
			kit.setCooldown(new Date(c.getTimeInMillis()));
			final Location location = p.getEyeLocation();
			final BlockIterator blocksToAdd = new BlockIterator(location, 0.0, 40);
			while (blocksToAdd.hasNext()) {
				final Location blockToAdd = blocksToAdd.next().getLocation();
				p.getWorld().playEffect(blockToAdd, Effect.STEP_SOUND, (Object) Material.REDSTONE_BLOCK, 15);
				p.playSound(blockToAdd, Sound.FIREWORK_BLAST, 3.0f, 3.0f);
			}
			final Snowball h = (Snowball) p.launchProjectile((Class<? extends Projectile>) Snowball.class);
			final Vector velo1 = p.getLocation().getDirection().normalize().multiply(10);
			h.setVelocity(velo1);
			h.setMetadata("fogo", (MetadataValue) new FixedMetadataValue(KitPvP.getInstance(), (Object) true));

		}
	}

	@EventHandler
	public void danofogo(final EntityDamageByEntityEvent e) {
		final Entity player1 = e.getEntity();
		if (e.getDamager() instanceof Snowball) {
			final Snowball player2 = (Snowball) e.getDamager();
			if (player2.hasMetadata("fogo")) {
				e.setDamage(8.0);
				player1.setFireTicks(100);
			}
		}
	}
}
