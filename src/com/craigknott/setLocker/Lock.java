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

	public synchronized RegionNamePair getRegion() {
		return region;
	}

	public synchronized  String getWarden () {
		return warden;
	}
	
	public synchronized boolean isLocked() {
		return (cellMates.size() > 0 );
	}

	public synchronized boolean removeCellMate(String name){
		cellMates.remove(name);
		return true;
	}
	
	public synchronized boolean acquireLock(String name) {
		if ( isLocked() ) {
			return false; 
		} else {
			warden = name;
			cellMates.add(name);
			return true;
		}
			
	} 
	
	public synchronized ArrayList<String> getCellMates (){
		return cellMates;
	}
	
	public synchronized int getCellMateCount ( ){ 
		return cellMates.size() - 1;
	}
	
	public synchronized String addCellMates(String name){
		cellMates.add(name);
		return "Sucessfully added";
	}
	
	public synchronized String releaseLock() {
		cellMates.clear();
		warden = null;
		return "Sucesfully released region";
	}

}
