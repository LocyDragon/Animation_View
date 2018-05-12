package com.locydragon.anv.core.tree.bench;

import com.locydragon.anv.api.events.AnimationResolveEvent;
import com.locydragon.anv.core.tree.Bench;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SendMsgBench implements Listener{
	static {
		Bench.benchMe(SendMsgBench.class);
	}
    @EventHandler
	public void onResolve(AnimationResolveEvent e) {
		if (e.getThisJob().getJobType().equals("发送信息")) {
			e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', e.getThisJob().getJobArgs()[0]));
		}
	}
}
