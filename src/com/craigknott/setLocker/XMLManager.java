package com.craigknott.setLocker;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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
			for ( Lock l : lm.getLocks() ){
				out.write("<lock>\n");
				out.write("<warden>\n");
				if ( l.isLocked() ){
					out.write(l.getWarden() + "\n");
				} else {
					out.write("N/A\n");
				}
				out.write("</warden>\n");
				out.write("<cellmates>\n");
				for ( String s : l.getCellMates() ){
					out.write("<cellmate>\n");
					out.write(s + "\n");
					out.write("</cellmate>\n");
				}
				out.write("</cellmates>\n");
				out.write("<region>\n");
				out.write("<name>\n");
				out.write(l.getRegion().getName() +"\n");
				out.write("</name>\n");
				out.write("<min_point>\n");
				out.write("<world>" + l.getRegion().getMin_point().getWorld() + "</world>\n");
				out.write("<x>"+ l.getRegion().getMin_point().getX() +"</x>\n");
				out.write("<y>"+ l.getRegion().getMin_point().getY() +"</y>\n");
				out.write("<z>"+ l.getRegion().getMin_point().getZ() +"</z>\n");
				out.write("</min_point>\n");
				out.write("<max_point>\n");
				out.write("<world>" + l.getRegion().getMax_point().getWorld() + "</world>\n");
				out.write("<x>"+ l.getRegion().getMax_point().getX() +"</x>\n");
				out.write("<y>"+ l.getRegion().getMax_point().getY() +"</y>\n");
				out.write("<z>"+ l.getRegion().getMax_point().getZ() +"</z>\n");
				out.write("</max_point>\n");
				out.write("</region>\n");
				out.write("</lock>\n");
			}
			
			out.close();			
		} catch (IOException e) {
			System.err.println("[SetLocker] Error saving to setlockersaves.xml");
		}
	}

}
