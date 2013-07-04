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
	
	public CommandManager(SetLocker plugin){
			this.plugin = plugin;
	}

	public void sendError(CommandSender sender, String message){
		sender.sendMessage(ChatColor.valueOf("RED").toString().concat(message));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("getCurrentSelection")){
			if (!(sender instanceof Player)) {
				sendError(sender, "This command may only be invoked by a player");
				return true;
			} 
			
			if ( args.length > 0 ){
				sendError(sender, "Incorrect number of arguments given (expected 0, given " + args.length + ")");
				return true;
			}
				
			WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
			Selection selection = worldEdit.getSelection((Player) sender);
			sender.sendMessage("Max: " + selection.getMaximumPoint() + ", Min: " + selection.getMinimumPoint());
			
			
			return true;
		}
			
		return false;
	}
	
}
