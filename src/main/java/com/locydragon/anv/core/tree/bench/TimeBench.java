package com.locydragon.anv.core.tree.bench;

import com.locydragon.anv.api.events.AnimationResolveEvent;
import com.locydragon.anv.core.protocol.EnvironmentManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TimeBench implements Listener {
	@EventHandler
	public void onResolve(AnimationResolveEvent e) {
		if (e.getThisJob().getJobType().equals("设置世界时间")) {
			EnvironmentManager.setTime(e.getPlayer(), Long.valueOf(e.getThisJob().getJobArgs()[0]), Integer.valueOf(e.getThisJob().getJobArgs()[1]));
		}
	}
}
