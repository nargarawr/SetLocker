package com.craigknott.setLocker;

import java.util.ArrayList;

public class LockManager {

	private ArrayList<Lock> locks;
	
	public LockManager () {
		locks = new ArrayList<Lock>();
	}

	public void addLock ( Lock l ) {
		locks.add(l);
	}
	
	public ArrayList<Lock> getLocks(){
		return locks;
	}
	
	public String delete( Lock l ){
		locks.remove(l);
		return "Sucessfully Removed";
	}
	
	public Lock getLockByName(String name){
		for ( Lock l : locks ){
			if ( l.getRegion().getName().equals(name)){
				return l; 
			}
		}
		return null;
	}
}
