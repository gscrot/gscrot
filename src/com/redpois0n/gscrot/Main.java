package com.redpois0n.gscrot;

import gscrot.api.PluginLoader;

import java.awt.Rectangle;
import java.awt.SystemTray;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;

import org.jnativehook.GlobalScreen;

import com.redpois0n.gscrot.ui.MainFrame;
import com.redpois0n.gscrot.ui.RegionCapture;

public class Main {

	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new ShutdownHook());
		
		try {
			PluginLoader.loadPlugins();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Config.load();
		} catch (FileNotFoundException e) {

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			if (!SystemTray.isSupported()) {
				System.err.println("SystemTray is not supported");
			} else {
				TrayIconHelper.initialize();
			}
			
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
			MainFrame.INSTANCE.setVisible(true);
			
			registerNativeHook();
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
		Rectangle rect = ScreenshotHelper.getWholeDesktop();
		BufferedImage screenShot = ScreenshotHelper.capture(rect);
		
		RegionCapture frame = new RegionCapture(rect, screenShot);
		frame.setVisible(true);
	}
}
