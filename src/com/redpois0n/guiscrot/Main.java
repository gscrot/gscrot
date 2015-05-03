package com.redpois0n.guiscrot;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;

import org.jnativehook.GlobalScreen;

public class Main {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			registerNativeHook();

			createBackground();
			createRegionalScreenshot();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Disable JNativeHook logging and enable
	 * 
	 * @throws Exception
	 */
	private static void registerNativeHook() throws Exception {
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);

		GlobalScreen.registerNativeHook();
	}

	public static void createBackground() throws Exception {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] screens = ge.getScreenDevices();

		for (GraphicsDevice screen : screens) {
			Robot robotForScreen = new Robot(screen);
			Rectangle screenBounds = screen.getDefaultConfiguration().getBounds();

			CoverFrame frame = new CoverFrame(screenBounds);
			frame.setVisible(true);
		}
	}

	public static void createRegionalScreenshot() {
		RegionFrame frame = new RegionFrame();
		GlobalScreen.addNativeMouseMotionListener(frame);
		GlobalScreen.addNativeMouseListener(frame); // TODO remove
		frame.setVisible(true);
	}
}
