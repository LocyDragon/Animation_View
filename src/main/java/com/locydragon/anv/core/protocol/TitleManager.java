package com.locydragon.anv.core.protocol;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.TitleAction;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.google.common.base.Strings;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;

/**
 * 本类来自项目:UDPLib，项目地址:https://github.com/UnknownStudio/UDPLib
 */
public class TitleManager {
	public static ProtocolManager PROTOCOL_MANAGER = ProtocolLibrary.getProtocolManager();

	public static void title(Player player, @Nullable String title, @Nullable String subTitle, int fadeIn, int stay, int fadeOut){
		setTimeAndDisplay(player, fadeIn, stay, fadeOut);
		if(!Strings.isNullOrEmpty(title)){
			title(player, title);
		}
		if(!Strings.isNullOrEmpty(subTitle)){
            subTitle(player, subTitle);
		}
	}

	/**
	 * Add a title to the title screen and display the title screen
	 */
	public static void title(Player player, String title){
		PacketContainer container = PROTOCOL_MANAGER.createPacket(PacketType.Play.Server.TITLE);
		container.getTitleActions().write(0, TitleAction.TITLE);
		container.getChatComponents().write(0, WrappedChatComponent.fromText(title));
		try {
			PROTOCOL_MANAGER.sendServerPacket(player, container);
		} catch (InvocationTargetException e) {
		}
	}

	/**
	 * Add a subtitle to the next title screen
	 * (The title screen will not display when this command is run).
	 */
	public static void subTitle(Player player, String subTitle){
		PacketContainer container = PROTOCOL_MANAGER.createPacket(PacketType.Play.Server.TITLE);
		container.getTitleActions().write(0, TitleAction.SUBTITLE);
		container.getChatComponents().write(0, WrappedChatComponent.fromText(subTitle));
		try {
			PROTOCOL_MANAGER.sendServerPacket(player, container);
		} catch (InvocationTargetException e) {
		}
	}

	/**
	 * Set the fade-in, stay and fade-out times for the title screen
	 */
	public static void setTimeAndDisplay(Player player, int fadeIn, int stay, int fadeOut){
		PacketContainer container = PROTOCOL_MANAGER.createPacket(PacketType.Play.Server.TITLE);
		container.getTitleActions().write(0, TitleAction.TIMES);
		container.getIntegers().write(0, fadeIn).write(1, stay).write(2, fadeOut);
		try {
			PROTOCOL_MANAGER.sendServerPacket(player, container);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Remove the title screen from the screen.
	 */
	public static void hide(Player player){
		PacketContainer container = PROTOCOL_MANAGER.createPacket(PacketType.Play.Server.TITLE);
		container.getTitleActions().write(0, TitleAction.CLEAR);
		try {
			PROTOCOL_MANAGER.sendServerPacket(player, container);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reset the title screen to the default settings and options
	 */
	public static void reset(Player player){
		PacketContainer container = PROTOCOL_MANAGER.createPacket(PacketType.Play.Server.TITLE);
		container.getTitleActions().write(0, TitleAction.RESET);
		try {
			PROTOCOL_MANAGER.sendServerPacket(player, container);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}