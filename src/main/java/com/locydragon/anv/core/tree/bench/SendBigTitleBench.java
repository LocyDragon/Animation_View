package com.locydragon.anv.core.tree.bench;

import com.locydragon.anv.api.events.AnimationResolveEvent;
import com.locydragon.anv.core.protocol.TitleManager;
import com.locydragon.anv.core.tree.Bench;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SendBigTitleBench implements Listener {
	static {
		Bench.benchMe(SendBigTitleBench.class);
	}
	@EventHandler
	public void onResolve(AnimationResolveEvent e) {
		if (e.getThisJob().getJobType().equals("大Title")) {
			TitleManager.title(e.getPlayer(), e.getThisJob().getJobArgs()[3], null,
					Integer.valueOf(e.getThisJob().getJobArgs()[0]), Integer.valueOf(e.getThisJob().getJobArgs()[1]) ,
					Integer.valueOf(e.getThisJob().getJobArgs()[2]));

		}
	}
}
