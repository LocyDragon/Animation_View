package com.locydragon.anv.commands;

import com.locydragon.anv.api.AnimationViewAPI;
import com.locydragon.anv.api.util.AnimationJob;
import com.locydragon.anv.api.util.AnimationObject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * @author LocyDragon
 */
public class ViewCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
		if (!sender.isOp()) {
			sender.sendMessage(genAnimationMsg("你没有权限."));
			return false;
		}
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
			sender.sendMessage(genAnimationMsg("所有已经存在在该服务器的动画:"));
            StringBuilder desBuilder = new StringBuilder();
            desBuilder.append("[");
            for (String animatioName : AnimationViewAPI.objectList()) {
            	desBuilder.append(animatioName);
				AnimationObject object = AnimationViewAPI.getAnimationObject(animatioName);
				if (object.hasJob("Mark")) {
					AnimationJob targetJob = object.getJobByType("Mark").get(0);
					desBuilder.append("(").append(targetJob.getJobArgs()).append(")");
				}
				desBuilder.append(",");
			}
			String des = desBuilder.toString();
            if (des.endsWith(",")) {
            	des = des.substring(0, des.length() - 1);
			}
			des += "]";
            sender.sendMessage(des);
		} else if (args[0].equalsIgnoreCase("mark")) {
			if (args.length >= 3) {
				if (AnimationViewAPI.getAnimationObject(args[1]) == null) {
					sender.sendMessage(genAnimationMsg("对象动画不存在."));
					return false;
				}
				AnimationObject object = AnimationViewAPI.getAnimationObject(args[1]);
				object.removeJob(new AnimationJob("Mark", null));
				StringBuilder sb = new StringBuilder();
				for (int i = 2;i < args.length;i++) {
					sb.append(args[i]).append(" ");
				}
				String toString = sb.toString();
				object.addJob(new AnimationJob("Mark", toString.trim().split(" ")));
				sender.sendMessage(genAnimationMsg("注释添加完成."));
			} else {
				sender.sendMessage(genAnimationMsg("使用/anv mark [动画名称] [注释文字] ——给动画加注释,以标记这个动画是做什么用的(这仅仅是为了自己方便)"));
			}
		}
		return false;
	}
	public String genAnimationMsg(String info) {
		return new StringBuilder().append("§7[§b§lAnimationView§7]§a"+info).toString();
	}
}
