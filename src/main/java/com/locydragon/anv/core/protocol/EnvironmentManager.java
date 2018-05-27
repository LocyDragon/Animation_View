package com.locydragon.anv.core.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.entity.Player;


import java.util.concurrent.atomic.AtomicInteger;

public class EnvironmentManager {
	static AtomicInteger nextEntityID = new AtomicInteger(Integer.MAX_VALUE);
	public static void setTime(Player who, long tick, int second) {
		int wentTime = second*500;
		PacketContainer packet = BookManager.PROTOCOL_MANAGER.createPacket(PacketType.Play.Server.UPDATE_TIME);
		packet.getLongs().write(0, who.getWorld().getFullTime() + tick);
		packet.getLongs().write(1, tick);
		Thread sendThread = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < wentTime;i++) {
					try {
						BookManager.PROTOCOL_MANAGER.sendServerPacket(who, packet);
					} catch (Exception exc) {
						exc.printStackTrace();
					} finally {
						try {
							Thread.sleep(2);
						} catch (Exception exc) {
							exc.printStackTrace();
						} finally {
							continue;
						}
					}
				}
			}
		});
		sendThread.start();
	}
	public static void rainy(Player who, int second) {
		int wentTime = second*500;
		PacketContainer packet = BookManager.PROTOCOL_MANAGER.createPacket(PacketType.Play.Server.GAME_STATE_CHANGE);
		packet.getIntegers().write(0, 2);
		packet.getFloat().write(0, (float)0);
        Thread sendThread = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < wentTime; i++) {
					try {
						BookManager.PROTOCOL_MANAGER.sendServerPacket(who, packet);
					} catch (Exception exc) {
						exc.printStackTrace();
					} finally {
						try {
							Thread.sleep(2);
						} catch (Exception exc) {
							exc.printStackTrace();
						} finally {
							continue;
						}
					}
				}
				PacketContainer packet2 = BookManager.PROTOCOL_MANAGER.createPacket(PacketType.Play.Server.GAME_STATE_CHANGE);
				packet2.getIntegers().write(0, 1);
				packet2.getFloat().write(0, (float)0);
				try {
					BookManager.PROTOCOL_MANAGER.sendServerPacket(who, packet2);
				} catch (Exception exc) {
					exc.printStackTrace();
				}
			}
		});
        sendThread.start();
	}
}
