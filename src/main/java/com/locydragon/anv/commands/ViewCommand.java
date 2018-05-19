package com.locydragon.anv.commands;

import com.google.common.collect.Lists;
import com.locydragon.anv.api.AnimationViewAPI;
import com.locydragon.anv.api.util.AnimationJob;
import com.locydragon.anv.api.util.AnimationObject;
import com.locydragon.anv.core.protocol.BookManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
		if (args.length <= 0) {
			sendHelp(sender);
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
		} else if (args[0].equalsIgnoreCase("help")) {
			sendHelp(sender);
			return false;
		} else if (args[0].equalsIgnoreCase("play")) {
			if (args.length == 3) {
				String who = args[1];
				String animationName = args[2];
				Player playTo = Bukkit.getPlayer(who);
				if (playTo == null) {
					sender.sendMessage(genAnimationMsg("你输入的玩家"+args[1]+"不在线或不存在."));
					return false;
				}
				AnimationObject object = AnimationViewAPI.getAnimationObject(animationName);
				if (object == null) {
					sender.sendMessage(genAnimationMsg("你输入的动画"+args[2]+"不在线或不存在."));
					return false;
				}
				if (!object.playFor(playTo)) {
					sender.sendMessage("§c错误: 玩家正在播放一个动画.");
				}
				sender.sendMessage(genAnimationMsg("为"+playTo.getName()+"播放"+animationName+"成功."));
			} else {
				sender.sendMessage(genAnimationMsg("使用/anv play [玩家] [动画名称] ——给玩家播放一个动画."));
			}
		} else if (args[0].equalsIgnoreCase("addSendMsg")) {
			if (args.length >= 4) {
				String targetName = args[1];
				String location = args[2];
				StringBuilder magicValue = new StringBuilder();
				for (int i = 3;i < args.length;i++) {
					magicValue.append(args[i]).append(" ");
				}
				String msg = magicValue.toString().trim();
				AnimationObject animationObject = AnimationViewAPI.getAnimationObject(targetName);
				if (animationObject == null) {
					sender.sendMessage(genAnimationMsg("您指定的动画不存在."));
					return false;
				}
				if (!isInt(location)) {
					sender.sendMessage("您输入的位置不是个阿拉伯数字!");
					return false;
				}
				int where = Integer.valueOf(location);
				where++;
				AnimationJob job = new AnimationJob("发送信息", new String[]{ msg });
				if (where >= animationObject.getJobList().size()) {
					animationObject.addJob(job);
				} else {
					animationObject.addJob(job, where);
				}
				sender.sendMessage(genAnimationMsg("添加任务成功."));
			} else {
				sender.sendMessage(genAnimationMsg("使用/anv addSendMsg [动画名称] [位置] [信息] ——插入动画:发送信息"));
				sender.sendMessage(genAnimationMsg("位置的意思是在第几个任务§c§l后面§a执行这个动画，可以使用/anv jobs指令来查看动画的任务列表."));
			}
		} else if (args[0].equalsIgnoreCase("jobs")) {
			if (args.length == 2) {
				String animationName = args[1];
				AnimationObject object = AnimationViewAPI.getAnimationObject(animationName);
				if (object == null) {
					sender.sendMessage(genAnimationMsg("对象动画不存在."));
					return false;
				}
				ItemStack bookItem = new ItemStack(Material.WRITTEN_BOOK);
				BookMeta meta = (BookMeta) bookItem.getItemMeta();
				List<String> pages = new ArrayList<>();
				int i = 0;
				for (AnimationJob job : object.getJobList()) {
					StringBuilder builder = new StringBuilder();
					builder.append(ChatColor.BLUE);
					builder.append("任务序号: ").append(i).append("\n");
					builder.append(ChatColor.GREEN);
					builder.append("任务名称: ").append(job.getJobArgs()).append("\n");
					builder.append(ChatColor.AQUA);
					builder.append("任务属性: ").append(Arrays.asList(job.getJobArgs()).toString()).append("\n");
					pages.add(builder.toString());
					i++;
				}
				if (pages.isEmpty()) {
					pages.add(ChatColor.GREEN+"这个动画还没有任何任务呢!快去添加一些吧!");
				}
				meta.setPages(pages);
				meta.setTitle(ChatColor.BLUE+"动画"+animationName+"的任务列表");
				meta.setDisplayName(ChatColor.BLUE+"动画"+animationName+"的任务列表");
				bookItem.setItemMeta(meta);
				BookManager.open((Player)sender, bookItem);
				sender.sendMessage(genAnimationMsg("为您呈现"+animationName+"的动画列表."));
			} else {
				sender.sendMessage(genAnimationMsg("使用/anv jobs [动画名称] ——查看一个动画的所有任务."));
			}
		} else if (args[0].equalsIgnoreCase("sendActionBar")) {
			if (args.length >= 4) {
				String targetName = args[1];
				String location = args[2];
				StringBuilder magicValue = new StringBuilder();
				for (int i = 3;i < args.length;i++) {
					magicValue.append(args[i]).append(" ");
				}
				String msg = magicValue.toString().trim();
				AnimationObject animationObject = AnimationViewAPI.getAnimationObject(targetName);
				if (animationObject == null) {
					sender.sendMessage(genAnimationMsg("您指定的动画不存在."));
					return false;
				}
				if (!isInt(location)) {
					sender.sendMessage("您输入的位置不是个阿拉伯数字!");
					return false;
				}
				int where = Integer.valueOf(location);
				where++;
				AnimationJob job = new AnimationJob("发送ActionBar", new String[]{ msg });
				if (where >= animationObject.getJobList().size()) {
					animationObject.addJob(job);
				} else {
					animationObject.addJob(job, where);
				}
				sender.sendMessage(genAnimationMsg("添加任务成功."));
			} else {
				sender.sendMessage(genAnimationMsg("使用/anv addActionBar [动画名称] [位置] [信息] ——插入动画:发送ActionBar(仅在1.9+版本可以使用)"));
				sender.sendMessage(genAnimationMsg("位置的意思是在第几个任务§c§l后面§a执行这个动画，可以使用/anv jobs指令来查看动画的任务列表."));
			}
		} else if (args[0].equalsIgnoreCase("wait")) {
			if (args.length == 4) {

			} else {
				sender.sendMessage(genAnimationMsg("使用/anv wait [动画名称] [位置] [秒] ——设置在某个任务后面等待几秒"));

			}
		}
		return false;
	}
	public String genAnimationMsg(String info) {
		return new StringBuilder().append("§7[§b§lAnimationView§7]§a"+info).toString();
	}
	public void sendHelp(CommandSender sender) {
		sender.sendMessage(genAnimationMsg("/anv help ——查看帮助"));
		sender.sendMessage(genAnimationMsg("/anv list ——查看所有动画对象及其注释"));
		sender.sendMessage(genAnimationMsg("/anv mark [动画名称] [注释文字] ——给动画加注释,以标记这个动画是做什么用的(这仅仅是为了自己方便)"));
		sender.sendMessage(genAnimationMsg("/anv play [玩家] [动画名称] ——给玩家播放一个动画."));
		sender.sendMessage(genAnimationMsg("/anv addSendMsg [动画名称] [位置] [信息] ——插入动画:发送信息"));
		sender.sendMessage(genAnimationMsg("/anv addActionBar [动画名称] [位置] [信息] ——插入动画:发送ActionBar(仅在1.9+版本可以使用)"));
		sender.sendMessage(genAnimationMsg("/anv create [动画名称] ——创建一个动画对象."));
		sender.sendMessage(genAnimationMsg("/anv jobs [动画名称] ——查看一个动画的所有任务."));
	}
	public boolean isInt(String intment) {
		try {
			Integer.valueOf(intment);
			return true;
		} catch (Exception exc) {
			return false;
		}
	}
}
