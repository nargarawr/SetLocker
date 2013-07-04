package com.craigknott.setLocker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(
					"setlockersaves.txt"));
			out.close();
		} catch (IOException e) {
			System.err.println("Error occured saving SetLocker data");
		}
	}

	@Override
	public void onEnable() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(
					"setlockersaves.txt"));

			String line;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
			in.close();
			System.out.println("SetLocker data loaded sucessfully");
		} catch (IOException e) {
			System.out.println("No SetLocker data detected, creating now");
			try {
				BufferedWriter out = new BufferedWriter(new FileWriter(
						"setlockersaves.txt"));
				out.close();
				System.out.println("SetLocker data created sucessfully");
			} catch (IOException e1) {
				System.err.println("Error occured saving SetLocker data");
			}
		}

		getServer().getPluginManager().registerEvents(
				new PlayerActionListener(this), this);

		getCommand("lock").setExecutor(c);
	}

}
