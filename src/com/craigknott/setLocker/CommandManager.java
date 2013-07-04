package com.craigknott.setLocker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;

public class CommandManager implements CommandExecutor {

	private SetLocker plugin;

	public CommandManager(SetLocker plugin) {
		this.plugin = plugin;
	}

	public void sendError(CommandSender sender, String message) {
		sender.sendMessage(ChatColor.valueOf("RED").toString().concat(message));
	}

	public boolean getCurrentSelection(CommandSender sender, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sendError(sender, "This command may only be invoked by a player");
			return true;
		}

		if (args.length > 0) {
			sendError(sender,
					"Incorrect number of arguments given (expected 0, given "
							+ args.length + ")");
			return true;
		}

		WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer()
				.getPluginManager().getPlugin("WorldEdit");
		Selection selection = worldEdit.getSelection((Player) sender);

		if (selection != null) {
			sender.sendMessage("X1 " + selection.getMaximumPoint().getBlockX()
					+ " Y1 " + selection.getMaximumPoint().getBlockY() + " Z1 "
					+ selection.getMaximumPoint().getBlockZ() + "\n" + "X2 "
					+ selection.getMinimumPoint().getBlockX() + " Y2 "
					+ selection.getMinimumPoint().getBlockY() + " Z2 "
					+ selection.getMinimumPoint().getBlockZ());
		} else {
			sendError(sender, "No selection has been made");
		}
		return true;
	}

	public boolean setLock(CommandSender sender, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sendError(sender, "This command may only be invoked by a player");
			return true;
		}

		if (args.length != 2) {
			sendError(sender,
					"Incorrect number of arguments given (expected 0, given "
							+ args.length + ")");
			return true;
		}

		switch (args[0]) {
			case ("new"):
				sender.sendMessage("new was typed");
				break;
			default:
				sendError(sender,
						"The first argument was invalid, please specify either: new");
				break;
		}

		return true;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		if (cmd.getName().equalsIgnoreCase("getCurrentSelection")) {
			return getCurrentSelection(sender, label, args);
		}

		if (cmd.getName().equalsIgnoreCase("setLock")) {
			return setLock(sender, label, args);
		}
		return false;
	}

}
