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
}
