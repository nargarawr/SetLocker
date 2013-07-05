package com.craigknott.setLocker;

import org.bukkit.plugin.java.JavaPlugin;

public class SetLocker extends JavaPlugin {

	private CommandManager c;

	public SetLocker () {
		c = new CommandManager(this);
	}
	
	public CommandManager getCommandManager() {
		return c;
	}

	@Override
	public void onDisable() {
		(new XMLManager(c)).save();
	}

	@Override
	public void onEnable() {
		(new XMLManager(c)).load();
		
		getServer().getPluginManager().registerEvents(
				new PlayerActionListener(this), this);

		getCommand("lock").setExecutor(c);
	}

}
