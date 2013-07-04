package com.craigknott.setLocker;

public class Lock {

	private RegionNamePair region;
	private boolean locked;

	public Lock(RegionNamePair region) {
		this.region = region;
		locked = false;
	}

	public RegionNamePair getRegion() {
		return region;
	}

	public boolean isLocked() {
		return locked;
	}

	public void acquireLock() {
		this.locked = true;
	}

	public void releaseLock() {
		this.locked = false;
	}

}
