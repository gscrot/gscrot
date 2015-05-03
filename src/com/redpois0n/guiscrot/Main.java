package com.redpois0n.guiscrot;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;

import org.jnativehook.GlobalScreen;

public class Main {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			registerNativeHook();
			
			createRegionalScreenshot();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Disable JNativeHook logging and enable
	 * @throws Exception
	 */
	private static void registerNativeHook() throws Exception {
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);
		
		GlobalScreen.registerNativeHook();
	}
	
	public static void createRegionalScreenshot() {
		RegionFrame frame = new RegionFrame();
		GlobalScreen.addNativeMouseMotionListener(frame);
		GlobalScreen.addNativeMouseListener(frame);
		frame.setVisible(true);
	}
}
