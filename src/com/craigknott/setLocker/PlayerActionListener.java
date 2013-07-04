package com.craigknott.setLocker;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerActionListener implements Listener {

	private SetLocker plugin;
	
	public PlayerActionListener (SetLocker plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerMove(final PlayerMoveEvent event) {
		
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerLeave(final PlayerQuitEvent event) {
		plugin.getCommandManager().releaseOwnerships(event.getPlayer().getName());
	}
	
}
