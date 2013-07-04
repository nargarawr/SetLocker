package com.craigknott.setLocker;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class Lock {

	private RegionNamePair region;
	private ArrayList<String> allowedPlayers;

	public Lock(RegionNamePair region) {
		this.region = region;
		allowedPlayers = new ArrayList<String>();
	}

	public RegionNamePair getRegion() {
		return region;
	}

	public boolean isLocked() {
		return (allowedPlayers.size() > 0 );
	}

	public void acquireLock(String name) {
		allowedPlayers.add(name);
	}

	public void releaseLock() {
		allowedPlayers.clear();
	}

}
