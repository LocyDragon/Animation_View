package com.locydragon.anv.commands;

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

		}

		return false;
	}
}
