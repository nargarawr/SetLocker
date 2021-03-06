package com.craigknott.setLocker;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerActionListener implements Listener {

	private SetLocker plugin;

	public PlayerActionListener(SetLocker plugin) {
		this.plugin = plugin;
	}

	public void preventAccess(Player player, Lock l){
		player.teleport(l.getEntranceAsLocation());
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerMove(final PlayerMoveEvent event) {
		if (event.getPlayer().hasPermission("setManager")) {
			return;
		} else {
			Lock breach = plugin.getCommandManager().checkBreaches(
					event.getPlayer().getLocation());

			if (breach != null) {
				if (breach.isLocked()) {
					if (!(breach.checkForCellMate(event.getPlayer().getName()))) {
						String message = "You are attempting to enter region: "
								+ breach.getRegion().getName()
								+ ". However, this region is locked and you are not permitted to enter";
						event.getPlayer().sendMessage(
								ChatColor.valueOf("RED").toString()
										.concat(message));
						preventAccess(event.getPlayer(), breach);
					}

				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerLeave(final PlayerQuitEvent event) {
		plugin.getCommandManager().releaseOwnerships(
				event.getPlayer().getName());
	}

}
