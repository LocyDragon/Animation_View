package com.locydragon.anv.core.tree.bench;

import com.locydragon.anv.api.events.AnimationResolveEvent;
import com.locydragon.anv.core.protocol.EnvironmentManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class RainyBench implements Listener {
	@EventHandler
	public void onResolve(AnimationResolveEvent e) {
		if (e.getThisJob().getJobType().equals("下雨")) {
			EnvironmentManager.rainy(e.getPlayer(), Integer.valueOf(e.getThisJob().getJobArgs()[0]));
		}
	}
}
