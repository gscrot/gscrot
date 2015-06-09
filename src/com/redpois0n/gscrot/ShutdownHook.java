package com.redpois0n.gscrot;

import com.redpois0n.gscrot.keys.KeyBindings;

public class ShutdownHook extends Thread {
	
	@Override
	public void run() {
		try {
			Config.save();
			KeyBindings.save();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
