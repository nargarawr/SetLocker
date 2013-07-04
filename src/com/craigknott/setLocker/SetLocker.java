package com.craigknott.setLocker;

import org.bukkit.plugin.java.JavaPlugin;

public class SetLocker extends JavaPlugin {

	@Override
	public void onDisable() {
	 
	}
	 
	@Override
	public void onEnable() {
		CommandManager c = new CommandManager(this);

		getCommand("lock").setExecutor(c);
	}

}
