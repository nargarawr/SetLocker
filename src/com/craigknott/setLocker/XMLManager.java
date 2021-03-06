package com.craigknott.setLocker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.World;

public class XMLManager {

	private CommandManager c;

	public XMLManager(CommandManager c) {
		this.c = c;
	}

	public void save() {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(
					"setlockersaves.xml"));
			LockManager lm = c.getLockManager();
			for (Lock l : lm.getLocks()) {
				out.write("<lock>\n");
				if (l.isLocked()) {
					out.write(" <warden>" + l.getWarden() + "</warden>\n");
				} else {
					out.write(" <warden>N/A</warden>\n");
				}
				out.write(" <cellmates>\n");
				for (String s : l.getCellMates()) {
					out.write("  <cellmate>" + s + "</cellmate>\n");
				}
				out.write(" </cellmates>\n");
				out.write(" <region>\n");
				out.write("  <name>" + l.getRegion().getName() + "</name>\n");
				if (l.hasEntrance()) {
					out.write("  <entrance>\n");
					out.write("   <x>" + l.getEntranceAsArray()[0] + "</x>\n");
					out.write("   <y>" + l.getEntranceAsArray()[1] + "</y>\n");
					out.write("   <z>" + l.getEntranceAsArray()[2] + "</z>\n");
					out.write("  </entrance>\n");
				} else {
					out.write("  <no_entrance/>\n");
				}
				out.write("  <min_point>\n");
				out.write("   <world>"
						+ l.getRegion().getMin_point().getWorld()
						+ "</world>\n");
				out.write("   <x>" + l.getRegion().getMin_point().getX()
						+ "</x>\n");
				out.write("   <y>" + l.getRegion().getMin_point().getY()
						+ "</y>\n");
				out.write("   <z>" + l.getRegion().getMin_point().getZ()
						+ "</z>\n");
				out.write("  </min_point>\n");
				out.write("  <max_point>\n");
				out.write("   <world>"
						+ l.getRegion().getMax_point().getWorld()
						+ "</world>\n");
				out.write("   <x>" + l.getRegion().getMax_point().getX()
						+ "</x>\n");
				out.write("   <y>" + l.getRegion().getMax_point().getY()
						+ "</y>\n");
				out.write("   <z>" + l.getRegion().getMax_point().getZ()
						+ "</z>\n");
				out.write("  </max_point>\n");
				out.write(" </region>\n");
				out.write("</lock>\n");
			}

			out.close();
		} catch (IOException e) {
			System.err
					.println("[SetLocker] Error saving to setlockersaves.xml");
		}
	}

	public String openTags(String line, String tag) {
		String[] splits = line.split(tag + ">|</" + tag);
		return splits[1];
	}

	public String getWorldName(String line) {
		String[] split = line.split("name=|}");
		return split[1];
	}

	public void load() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(
					"setlockersaves.xml"));
			ArrayList<String> strings = new ArrayList<String>();
			String currentLine;
			while ((currentLine = in.readLine()) != null) {
				strings.add(currentLine);
			}

			int endCount = 0;
			int loopPoint = 0;

			for (int i = 0; i < strings.size(); i++) {
				if (strings.get(i).equals("</lock>")) {
					endCount++;
				}
			}

			for (int j = 0; j < endCount; j++) {
				String warden = "";
				String name = "";
				String worldName = "";
				boolean hasEntrance = false;
				double ent_x = 0;
				double ent_y = 0;
				double ent_z = 0;
				double min_x = 0;
				double min_y = 0;
				double min_z = 0;
				double max_x = 0;
				double max_y = 0;
				double max_z = 0;

				ArrayList<String> cellMates = new ArrayList<String>();
				RegionNamePair r;
				Lock l;
				boolean crew = false;

				for (int i = loopPoint; i < strings.size(); i++) {
					String line = strings.get(i);

					if (line.matches("(\\s*)<\\/cellmates>")) {
						crew = false;
					}

					if (crew) {
						String member = openTags(strings.get(i), "cellmate");
						if (!(member.equals(warden))) {
							cellMates.add(member);
						}

					}

					if (line.matches("(\\s*)<cellmates>")) {
						crew = true;
					}

					if (line.matches("(\\s*)<warden>.*<\\/warden>")) {
						warden = openTags(line, "warden");
					} else if (line.matches("(\\s*)<name>.*<\\/name>")) {
						name = openTags(line, "name");
					} else if (line.matches("(\\s*)<entrance>")) {
						ent_x = Double
								.valueOf(openTags(strings.get(i + 1), "x"));
						ent_y = Double
								.valueOf(openTags(strings.get(i + 2), "y"));
						ent_z = Double
								.valueOf(openTags(strings.get(i + 3), "z"));
						hasEntrance = true;
					} else if (line.matches("(\\s*)<min_point>")) {
						worldName = getWorldName(strings.get(i + 1));
						min_x = Double
								.valueOf(openTags(strings.get(i + 2), "x"));
						min_y = Double
								.valueOf(openTags(strings.get(i + 3), "y"));
						min_z = Double
								.valueOf(openTags(strings.get(i + 4), "z"));
					} else if (line.matches("(\\s*)<max_point>")) {
						max_x = Double
								.valueOf(openTags(strings.get(i + 2), "x"));
						max_y = Double
								.valueOf(openTags(strings.get(i + 3), "y"));
						max_z = Double
								.valueOf(openTags(strings.get(i + 4), "z"));
					}

					if (strings.get(i).equals("</lock>")) {
						loopPoint = i + 1;
						break;
					}
				}

				World w = c.getPlugin().getServer().getWorld(worldName);
				Location min_point = new Location(w, min_x, min_y, min_z);
				Location max_point = new Location(w, max_x, max_y, max_z);
				r = new RegionNamePair(name, min_point, max_point);
				l = new Lock(r);
				if (hasEntrance) {
					l.setEntrance(ent_x, ent_y, ent_z);
				}

				l.acquireLock(warden);
				for (String s : cellMates) {
					l.addCellMates(s);
				}
				c.getLockManager().addLock(l);
			}
			in.close();
			System.out
					.println("[SetLocker] Sucessfully loaded setlockersaves.xml");
		} catch (IOException e) {
			System.err.println("[SetLocker] Error reading setlockersaves.xml");
			try {
				BufferedWriter out = new BufferedWriter(new FileWriter(
						"setlockersaves.xml"));
				out.close();
				System.err
						.println("[SetLocker] Sucessfully created setlockersaves.xml");
			} catch (IOException e1) {
				System.err
						.println("[SetLocker] Error creating setlockersaves.xml");
			}
		}
	}

}
