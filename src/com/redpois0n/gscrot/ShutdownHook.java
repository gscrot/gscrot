package com.redpois0n.gscrot;

public class ShutdownHook extends Thread {
	
	@Override
	public void run() {
		try {
			Config.save();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
