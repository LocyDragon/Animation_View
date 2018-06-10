package com.locydragon.anv.core.protocol.entity;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.google.common.base.Strings;
import com.locydragon.anv.AnimationView;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
public class FakeEntity {
	static ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
	static AtomicInteger nextEntityID = new AtomicInteger(Integer.MAX_VALUE);

	private String entityName = null;
	private boolean spawned = false;
	private Location current = null;
	private int entityId = -1;
	public FakeEntity(String name) {
		this.entityName = name;
		this.spawned = false;
		this.entityId = nextEntityID.getAndDecrement();
	}
    public void moveTo(Player who, Location location) {
		Validate.notNull(this.current);
		if (this.spawned == false) {
			return;
		}
		sendMovePacket(who, this.entityId, this.current, location, location.getYaw(), location.getPitch());
		this.current = location;
	}
    static void sendMovePacket(Player player, int entityId, Location current, Location prev, float yaw, float angle) {
		Validate.notNull(current);
		Validate.notNull(prev);
		Validate.notNull(player);
		PacketContainer container = protocolManager.createPacket(PacketType.Play.Server.REL_ENTITY_MOVE_LOOK);
		container.getIntegers().write(0, entityId);
		if (AnimationView.versionNum >= 190) {
			container.getShorts().write(0, (short) ((current.getBlockX() * 32 - prev.getBlockX() * 32) * 128));
			container.getShorts().write(1, (short) ((current.getBlockY() * 32 - prev.getBlockY() * 32) * 128));
			container.getShorts().write(2, (short) ((current.getBlockZ() * 32 - prev.getBlockZ() * 32) * 128));
			container.getBytes().write(0, (byte) yaw);
			container.getBytes().write(1, (byte) angle);
		} else {
			container.getBytes().write(0, (byte) ((current.getBlockX() * 32 - prev.getBlockX() * 32) * 128));
			container.getBytes().write(1, (byte) ((current.getBlockY() * 32 - prev.getBlockY() * 32) * 128));
			container.getBytes().write(2, (byte) ((current.getBlockZ() * 32 - prev.getBlockZ() * 32) * 128));
			container.getBytes().write(3, (byte) yaw);
			container.getBytes().write(4, (byte) angle);
		}
		container.getBooleans().write(0, true);
		try {
			protocolManager.sendServerPacket(player, container);
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	@Deprecated
	static void sendFakeEntity(Player player, ItemStack itemStack, Location location, String displayName){
		Validate.notNull(player);
		Validate.notNull(itemStack);
		Validate.notNull(location);
		int entityID = nextEntityID.getAndDecrement();
		PacketContainer spawnEntityLiving = protocolManager.createPacket(PacketType.Play.Server.SPAWN_ENTITY_LIVING);
		spawnEntityLiving.getIntegers().write(0, entityID); //Entity ID
		if (AnimationView.versionNum >= 190) {
			spawnEntityLiving.getUUIDs().write(0, UUID.randomUUID()); //Entity UUID
			spawnEntityLiving.getIntegers().write(1, 2); //Entity Type
		} else {
			spawnEntityLiving.getIntegers().write(1, UUID.randomUUID().variant()); //Entity UUID
			spawnEntityLiving.getIntegers().write(2, 2); //Entity Type
		}
		spawnEntityLiving.getDoubles().write(0, location.getX())
				.write(1, location.getY())
				.write(2, location.getZ());
		spawnEntityLiving.getIntegers().write(2, 0) //Pitch
				.write(3, 0)	//Yaw
				//Data
				.write(4, 1)
				//Velocity(X,Y,Z)
				.write(5, 0)
				.write(6, 0)
				.write(7, 0);

		PacketContainer entityMetadata = protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
		entityMetadata.getIntegers().write(0, entityID);
		WrappedDataWatcher dataWatcher = new WrappedDataWatcher();
		dataWatcher.setObject(0, (byte) 0);
		dataWatcher.setObject(1, 300);
		dataWatcher.setObject(2, Strings.nullToEmpty(displayName));
		dataWatcher.setObject(3, !Strings.isNullOrEmpty(displayName));
		dataWatcher.setObject(4, false);
		dataWatcher.setObject(5, true);
		dataWatcher.setObject(6, itemStack);
		entityMetadata.getWatchableCollectionModifier().write(0, dataWatcher.getWatchableObjects());
		try {
			protocolManager.sendServerPacket(player, spawnEntityLiving);
			protocolManager.sendServerPacket(player, entityMetadata);
		} catch (InvocationTargetException e) {
		}
	}
}
