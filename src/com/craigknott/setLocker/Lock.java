package com.craigknott.setLocker;

import java.util.ArrayList;

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

	public boolean swapOwner ( String newOwner ) {
		warden = newOwner;
		return true;
	}
	
	public synchronized boolean removeCellMate(String name){
		cellMates.remove(name);
		return true;
	}
	
	public String getCellMateByIndex(int i ){
		return cellMates.get(i);
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
	
	public boolean checkForCellMate (String name) {
		for ( String s : cellMates ){
			if ( s.equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<String> getCellMates (){
		return cellMates;
	}
	
	public int getCellMateCount ( ){ 
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
