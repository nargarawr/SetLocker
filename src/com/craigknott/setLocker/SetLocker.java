package com.craigknott.setLocker;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

public class SetLocker extends JavaPlugin {

	public void onEnable () {
		getLogger().info("test");
	}
	
	@Override
    public void onDisable() {
        // When the plugin is disabled, reset all locks
    }
	
	public void onPlayerJoin(PlayerJoinEvent evt) {
		Player player = evt.getPlayer();
		PlayerInventory inventory = player.getInventory();
		inventory.clear();
	}
	
}
