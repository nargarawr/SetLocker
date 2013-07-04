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
				out.write(l.getRegion().getName());
			}
			out.close();			
		} catch (IOException e) {
			System.err.println("[SetLocker] Error saving to setlockersaves.xml");
		}
	}

}
