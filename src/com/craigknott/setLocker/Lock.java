package com.craigknott.setLocker;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class Lock {

	private RegionNamePair region;
	private ArrayList<String> cellMates;
	private String warden;

	public Lock(RegionNamePair region) {
		this.region = region;
		cellMates = new ArrayList<String>();
	}

	public RegionNamePair getRegion() {
		return region;
	}

	public String getWarden () {
		return warden;
	}
	
	public boolean isLocked() {
		return (cellMates.size() > 0 );
	}

	public String acquireLock(String name) {
		if ( isLocked() ) {
			return "This region is already locked and cannot be acquired"; 
		} else {
			warden = name;
			cellMates.add(name);
			return "Sucesfully locked region";
		}
			
	} 
	
	public String addCellMates(String name){
		return null;
	}
	
	public String releaseLock() {
		cellMates.clear();
		warden = null;
		return "Sucesfully released region";
	}

}
