package com.craigknott.setLocker;

import java.util.ArrayList;

import org.bukkit.Location;

public class Lock {

	private RegionNamePair region;
	private ArrayList<String> cellMates;
	private String warden;
	private OneBlockLocation entranceLocation;
	private boolean entrance;

	public Lock(RegionNamePair region) {
		entrance = false;
		this.region = region;
		cellMates = new ArrayList<String>();
	}

	public double[] getEntranceAsArray(){
		double[] d = new double[3];
		d[0] = entranceLocation.getX();
		d[1] = entranceLocation.getY();
		d[2] = entranceLocation.getZ();
		return d;
	}
	
	public boolean hasEntrance (){
		return entrance;
	}
	
	public String getEntranceAsText (){
		return "(" + entranceLocation.getX() + ", " + entranceLocation.getY() + ", " + entranceLocation.getZ() + ")";
	}
	
	public Location getEntranceAsLocation() throws NullPointerException{
		Location loc = new Location(region.getMax_point().getWorld(), entranceLocation.getX(), entranceLocation.getY(), entranceLocation.getZ());
		return loc;
	}
	
	public void setEntrance (double x, double y, double z){
		entranceLocation = new OneBlockLocation(x,y,z);
		entrance = true;
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

	public void setOwner ( String newOwner ) {
		warden = newOwner;
	}
	
	public synchronized boolean removeCellMate(String name){
		cellMates.remove(name);
		return true;
	}
	
	public String getCellMateByIndex(int i ){
		return cellMates.get(i);
	}
	
	public synchronized boolean acquireLock(String name) {
		if ( isLocked() || name.equals("N/A")) {
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
		if ( name.equals("N/A") ){
			return null;
		}
		cellMates.add(name);
		return "Sucessfully added";
	}
	
	public synchronized String releaseLock() {
		cellMates.clear();
		warden = null;
		return "Sucesfully released region";
	}

}
