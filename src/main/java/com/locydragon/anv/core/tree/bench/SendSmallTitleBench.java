package com.locydragon.anv.core.tree.bench;

import com.locydragon.anv.api.events.AnimationResolveEvent;
import com.locydragon.anv.core.protocol.TitleManager;
import com.locydragon.anv.core.tree.Bench;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SendSmallTitleBench implements Listener {
	static {
		Bench.benchMe(SendSmallTitleBench.class);
	}
	@EventHandler
	public void onResolve(AnimationResolveEvent e) {
		if (e.getThisJob().getJobType().equals("小Title")) {
			TitleManager.title(e.getPlayer(), null, ChatColor.translateAlternateColorCodes('&', e.getThisJob().getJobArgs()[3]),
					Integer.valueOf(e.getThisJob().getJobArgs()[0])*20, Integer.valueOf(e.getThisJob().getJobArgs()[1])*20 ,
					Integer.valueOf(e.getThisJob().getJobArgs()[2])*20);
		}
	}
}
