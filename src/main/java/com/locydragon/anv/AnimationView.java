package com.locydragon.anv;

import com.locydragon.anv.commands.ViewCommand;
import com.locydragon.anv.core.AnimationLand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author LocyDragon
 */
public class AnimationView extends JavaPlugin {
	public static final String magicValue_ProtocolLib = "ProtocolLib";
	public static AnimationView instance = null;
	@Override
	public void onEnable() {
		if (Bukkit.getPluginManager().getPlugin(magicValue_ProtocolLib) == null) {
			Bukkit.getLogger().info("ProtocolLib插件不存在，AnimationView动画插件无法使用.");
			Bukkit.getPluginManager().disablePlugin(this);
			this.onDisable();
		}
		Bukkit.getPluginCommand("anv").setExecutor(new ViewCommand());
		saveDefaultConfig();
		instance = this;
		AnimationLand.land = getConfig();
	}
	public static AnimationView getInstance() {
		return instance;
	}
}
