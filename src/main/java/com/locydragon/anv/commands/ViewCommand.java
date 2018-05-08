package com.locydragon.anv.commands;

import com.locydragon.anv.api.AnimationViewAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * @author LocyDragon
 */
public class ViewCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
		if (args[0].equalsIgnoreCase("create")) {
			if (args.length == 2) {
				if (AnimationViewAPI.createObject(args[1]) != null) {
					sender.sendMessage(genAnimationMsg("创建动画对象"+args[1]+"完成."));
				} else {
					sender.sendMessage(genAnimationMsg("创建动画对象失败!可能导致的原因是该对象已经存在在你的服务器里了."));
				}
			} else {
				sender.sendMessage(genAnimationMsg("使用/anv create [动画名称] ——创建一个动画对象."));
			}
		} else if (args[0].equalsIgnoreCase("list")) {

		}
		return false;
	}
	public String genAnimationMsg(String info) {
		return new StringBuilder().append("§7[§b§lAnimationView§7]§a"+info).toString();
	}
}
