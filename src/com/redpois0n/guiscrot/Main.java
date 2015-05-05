package com.redpois0n.guiscrot;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.SystemTray;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.UIManager;

import org.jnativehook.GlobalScreen;

import com.redpois0n.guiscrot.ui.RegionCapture;
import com.redpois0n.guiscrot.ui.MainFrame;

public class Main {

	public static void main(String[] args) {
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
