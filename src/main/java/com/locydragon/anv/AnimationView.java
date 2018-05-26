package com.locydragon.anv;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.injector.GamePhase;
import com.locydragon.anv.commands.ViewCommand;
import com.locydragon.anv.core.AnimationLand;
import com.locydragon.anv.core.protocol.BookManager;
import com.locydragon.anv.core.tree.bench.*;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


/**
 * @author LocyDragon
 */
public class AnimationView extends JavaPlugin {
	public static final String magicValue_ProtocolLib = "ProtocolLib";
	public static AnimationView instance = null;
	public static Vector<String> forbidTimePacket = new Vector<>();
	@Override
	public void onEnable() {
		if (Bukkit.getPluginManager().getPlugin(magicValue_ProtocolLib) == null) {
			Bukkit.getLogger().info("ProtocolLib插件不存在，AnimationView动画插件无法使用.");
			Bukkit.getPluginManager().disablePlugin(this);
			this.onDisable();
		}
		Bukkit.getPluginCommand("anv").setExecutor(new ViewCommand());
		saveDefaultConfig();
		instance = this;
		AnimationLand.land = getConfig();
		Bukkit.getLogger().info("您正在使用的是AnimationView——免费版插件");
		Bukkit.getLogger().info("作者:LocyDragon QQ:2424441676");
		registerEvents();
		Bukkit.getLogger().info("AnimationView: 事件注册完成");
	}
	public static AnimationView getInstance() {
		return instance;
	}
	public static void registerEvents() {
		register(new OpCommandBench());
		register(new PlayerCommandBench());
		register(new SendActionBarBench());
		register(new SendBigTitleBench());
		register(new SendMsgBench());
		register(new SendSmallTitleBench());
		register(new TimeBench());
	}
	public static void register(Listener door) {
		Bukkit.getPluginManager().registerEvents(door, instance);
	}
}
