package com.locydragon.anv.core.effect.sendto;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.locydragon.anv.core.effect.EffectType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Random;

public class EffectSender {
	static ProtocolManager manager = ProtocolLibrary.getProtocolManager();
	private static final Integer packetId = 0x22;
	public static boolean sendEffect(EffectType type, Player sendTo, Location location) {
	    PacketContainer newPacket = manager.createPacket(PacketType.Play.Server.WORLD_PARTICLES);
		newPacket.getIntegers().write(0, type.getF());
		newPacket.getIntegers().write(1, 1); //warning
		newPacket.getBooleans().write(0, false);
		newPacket.getFloat().write(0, (float)location.getX());
		newPacket.getFloat().write(1, (float)location.getY());
		newPacket.getFloat().write(2, (float)location.getZ());
		newPacket.getFloat().write(3, (float)(location.getX()+new Random().nextGaussian()));
		newPacket.getFloat().write(4, (float)(location.getY()+new Random().nextGaussian()));
		newPacket.getFloat().write(5, (float)(location.getZ()+new Random().nextGaussian()));
		newPacket.getIntegerArrays().write(0, new int[]{});
		try {
			manager.sendServerPacket(sendTo, newPacket);
		} catch (Exception exc) {
			exc.printStackTrace();
			Bukkit.getLogger().info("[AnimationView]粒子效果发送失败，请检查粒子效果版本.");
		}
		return true;
	}
}
