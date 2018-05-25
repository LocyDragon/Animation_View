package com.locydragon.anv.core.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.utility.MinecraftReflection;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 本类的代码来自项目: UDPLib
 */
public class BookManager {
	public static ProtocolManager PROTOCOL_MANAGER = ProtocolLibrary.getProtocolManager();

	public AtomicReference<PacketContainer> BOOK_OPEN_PACKET = new AtomicReference<>();

	/**
	 * 本方法来自项目:UDPLib，项目地址:https://github.com/UnknownStudio/UDPLib
	 * 给玩家发包打开一本书
	 * @param player 玩家对象
	 * @param book 需要打开的书
	 */
	public void open(Player player, ItemStack book){
		ItemStack held = player.getItemInHand();
		player.getInventory().setItemInHand(book);

		PacketContainer container = BOOK_OPEN_PACKET.get();
		if(container == null) {
			container = PROTOCOL_MANAGER.createPacket(PacketType.Play.Server.CUSTOM_PAYLOAD);
			container.getStrings().write(0, "MC|BOpen");
			ByteBuf byteBuf = Unpooled.buffer();
			byteBuf.writeByte(0); // Main Hand.
			Object serializer = MinecraftReflection.getPacketDataSerializer(byteBuf);
			container.getModifier().withType(ByteBuf.class).write(0, serializer);
			BOOK_OPEN_PACKET.set(container);
		}

		try {
			PROTOCOL_MANAGER.sendServerPacket(player, container);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} finally {
			player.setItemInHand(held);
		}
	}
}
