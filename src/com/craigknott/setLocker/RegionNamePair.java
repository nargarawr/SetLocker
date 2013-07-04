package com.craigknott.setLocker;

import org.bukkit.Location;

public class RegionNamePair {

	private String name;
	private Location min_point;
	private Location max_point;

	public RegionNamePair(String name, Location min_point, Location max_point) {
		this.name = name;
		this.min_point = min_point;
		this.max_point = max_point;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getMin_point() {
		return min_point;
	}

	public void setMin_point(Location min_point) {
		this.min_point = min_point;
	}

	public Location getMax_point() {
		return max_point;
	}

	public void setMax_point(Location max_point) {
		this.max_point = max_point;
	}

}
