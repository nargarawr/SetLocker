package com.craigknott.setLocker;

import org.bukkit.ChatColor;
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

	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerMove(final PlayerMoveEvent event) {
		Lock breach = plugin.getCommandManager().checkBreaches(
				event.getPlayer().getLocation());
		if (breach != null) {
			String message = "You are attempting to enter region: "
					+ breach.getRegion().getName()
					+ ". However, this region is locked and you are not permitted to enter";

			//event.getPlayer().sendMessage(
				//	ChatColor.valueOf("RED").toString().concat(message));
			System.out.println("In " + breach.getRegion().getName());
		} else {
			System.out.println("out");
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerLeave(final PlayerQuitEvent event) {
		plugin.getCommandManager().releaseOwnerships(
				event.getPlayer().getName());
	}

}
