package com.locydragon.anv.core.tree.bench;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.locydragon.anv.api.events.AnimationResolveEvent;
import com.locydragon.anv.core.protocol.BookManager;
import com.locydragon.anv.core.tree.Bench;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SendActionBarBench implements Listener {
	static {
		Bench.benchMe(SendActionBarBench.class);
	}
	@EventHandler
	public void onResolve(AnimationResolveEvent e) {
		if (e.getThisJob().getJobType().equals("发送ActionBar")) {
			ProtocolManager manager = BookManager.PROTOCOL_MANAGER;
			PacketContainer container = manager.createPacket(PacketType.Play.Server.CHAT);
			container.getChatComponents().write(0, WrappedChatComponent.fromJson("{\"text\": \"" + e.getThisJob().getJobArgs()[0] + "\"}"));
			container.getBytes().write(0, (byte)2);
			try {
				manager.sendServerPacket(e.getPlayer(), container);
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		}
	}
}
