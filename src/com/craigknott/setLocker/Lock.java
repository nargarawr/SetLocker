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

	public boolean removeCellMate(String name){
		cellMates.remove(name);
		return true;
	}
	
	public boolean acquireLock(String name) {
		if ( isLocked() ) {
			return false; 
		} else {
			warden = name;
			cellMates.add(name);
			return true;
		}
			
	} 
	
	public ArrayList<String> getCellMates (){
		return cellMates;
	}
	
	public int getCellMateCount ( ){ 
		return cellMates.size() - 1;
	}
	
	public String addCellMates(String name){
		cellMates.add(name);
		return "Sucessfully added";
	}
	
	public String releaseLock() {
		cellMates.clear();
		warden = null;
		return "Sucesfully released region";
	}

}
