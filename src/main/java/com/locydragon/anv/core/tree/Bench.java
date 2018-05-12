package com.locydragon.anv.core.tree;

import com.locydragon.anv.AnimationView;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class Bench {
	@Deprecated
	private static final Void ž = null;
	public static void benchMe(Class<? extends Listener> targetClass) {
		try {
			Listener listener = (Listener)targetClass.newInstance();
			Bukkit.getPluginManager().registerEvents(listener, AnimationView.getInstance());
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
}
