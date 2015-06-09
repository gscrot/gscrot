package com.redpois0n.gscrot;

import gscrot.api.Plugin;

import java.awt.Rectangle;
import java.awt.SystemTray;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;

import org.jnativehook.GlobalScreen;

import pluginlib.PluginLoader;

import com.redpois0n.gscrot.keys.KeyListener;
import com.redpois0n.gscrot.ui.MainFrame;
import com.redpois0n.gscrot.ui.RegionCapture;
import com.redpois0n.gscrot.ui.ScreenColorPicker;
import com.redpois0n.gscrot.ui.TrayIconHelper;

public class Main {
	
	private static PluginLoader<Plugin> loader;

	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new ShutdownHook());

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			loader = new PluginLoader<Plugin>();
			loader.loadPlugins();
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
		GlobalScreen.addNativeKeyListener(new KeyListener());
	}

	public static void createBackground() throws Exception {
		Rectangle rect = ScreenshotHelper.getWholeDesktop();
		BufferedImage screenShot = ScreenshotHelper.capture(rect);
		
		RegionCapture frame = new RegionCapture(rect, screenShot);
		frame.setVisible(true);
	}
	
	public static void createColorPicker() throws Exception {
		Rectangle rect = ScreenshotHelper.getWholeDesktop();
		BufferedImage screenShot = ScreenshotHelper.capture(rect);
		
		RegionCapture frame = new ScreenColorPicker(rect, screenShot);
		frame.setVisible(true);
	}
}
