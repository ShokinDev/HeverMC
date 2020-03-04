package br.com.hevermc.pvp;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.hevermc.pvp.api.File;
import br.com.hevermc.pvp.command.commons.loader.CommandLoader;
import br.com.hevermc.pvp.listeners.GeneralListener;
import br.com.hevermc.pvp.listeners.kits.commons.loader.KitLoader;
import br.com.hevermc.pvp.onevsone.Eventos1v1;
import br.com.hevermc.pvp.onevsone.Menu1v1Custom;
import lombok.Getter;

public class KitPvP extends JavaPlugin {

	@Getter
	public static KitPvP instance;
	@Getter
	public static Manager manager = new Manager();
	@Getter
	public static File warps = new File("warps");

	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() {
		instance = this;
		warps.setup();
		ItemStack result = new ItemStack(Material.MUSHROOM_SOUP, 1);
		ShapelessRecipe recipe = new ShapelessRecipe(result);
		recipe.addIngredient(1, Material.INK_SACK, 3);
		recipe.addIngredient(1, Material.BOWL);
		Bukkit.getServer().addRecipe((Recipe) recipe);
		Bukkit.getPluginManager().registerEvents(new GeneralListener(), this);
		Bukkit.getPluginManager().registerEvents(new Eventos1v1(), this);
		Bukkit.getPluginManager().registerEvents(new Menu1v1Custom(), this);
		new CommandLoader();
		new KitLoader();
		getManager().setup();
		super.onEnable();
	}

}
