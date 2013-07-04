package com.craigknott.setLocker;

import org.bukkit.plugin.java.JavaPlugin;

public class SetLocker extends JavaPlugin {

	// save text file
	
	@Override
	public void onDisable() {
	
	}
	 
	@Override
	public void onEnable() {
		// load text file
		CommandManager c = new CommandManager(this);
		getCommand("lock").setExecutor(c);
	}

}
