package com.locydragon.anv.core;

import com.locydragon.anv.AnimationView;
import org.bukkit.configuration.file.FileConfiguration;

public class AnimationLand {
	public static FileConfiguration land = null;
	public static void reloadLand() {
		AnimationView.getInstance().saveConfig();
		AnimationView.getInstance().reloadConfig();
		land = AnimationView.getInstance().getConfig();
	}
}
