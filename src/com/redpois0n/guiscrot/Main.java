package com.redpois0n.guiscrot;

import org.jnativehook.GlobalScreen;

public class Main {

	public static void main(String[] args) {
		try {
			//GlobalScreen.registerNativeHook();
			
			new RegionFrame().setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
