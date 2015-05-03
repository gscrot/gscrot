package com.redpois0n.guiscrot;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;

import org.jnativehook.GlobalScreen;

public class Main {
	
	public static final List<CoverFrame> ACTIVE_COVER_FRAMES = new ArrayList<CoverFrame>();

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			registerNativeHook();

			boolean still = true;
			
			createBackground(still);
			//createRegionalScreenshot(still);
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

	public static void createBackground(boolean still) throws Exception {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] screens = ge.getScreenDevices();

		for (GraphicsDevice screen : screens) {
			Rectangle rect = screen.getDefaultConfiguration().getBounds();
			Robot robot = new Robot(screen);
			
			Image image = still ? robot.createScreenCapture(rect) : null;
			
			CoverFrame frame = new CoverFrame(rect, image);
			frame.setVisible(true);
		}
	}

	public static void createRegionalScreenshot(boolean still) {
		RegionFrame frame = new RegionFrame(still);
		GlobalScreen.addNativeMouseMotionListener(frame);
		GlobalScreen.addNativeMouseListener(frame); // TODO remove
		frame.setVisible(true);
	}
}
