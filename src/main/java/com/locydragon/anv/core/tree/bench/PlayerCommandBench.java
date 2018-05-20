package com.locydragon.anv.core.tree.bench;

import com.locydragon.anv.api.events.AnimationResolveEvent;
import com.locydragon.anv.core.tree.Bench;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerCommandBench implements Listener {
	static {
		Bench.benchMe(PlayerCommandBench.class);
	}
	@EventHandler
	public void onResolve(AnimationResolveEvent e) {
		if (e.getThisJob().getJobType().equals("玩家指令")) {
			e.getPlayer().chat(e.getThisJob().getJobArgs()[0]);
		}
	}
}
