package com.locydragon.anv.core.tree.bench;

import com.locydragon.anv.api.events.AnimationResolveEvent;
import com.locydragon.anv.core.tree.Bench;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OpCommandBench implements Listener {
	static {
		Bench.benchMe(OpCommandBench.class);
	}
	@EventHandler
	public void onResolve(AnimationResolveEvent e) {
		if (e.getThisJob().getJobType().equals("OP指令")) {
			boolean isOp = e.getPlayer().isOp();
			e.getPlayer().setOp(true);
			e.getPlayer().chat(e.getThisJob().getJobArgs()[0]);
			e.getPlayer().setOp(isOp);
		}
	}
}
