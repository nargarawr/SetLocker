package com.craigknott.setLocker;

import com.sk89q.worldedit.bukkit.selections.Selection;

public class RegionNamePair {

	private String name;
	private Selection region;
	
	public RegionNamePair (String name, Selection region) {
		this.name = name;
		this.region = region;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Selection getRegion() {
		return region;
	}

	public void setRegion(Selection region) {
		this.region = region;
	}
	
}
