package com.redpois0n.guiscrot;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.UIManager;

import org.jnativehook.GlobalScreen;

public class Main {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			registerNativeHook();

			boolean still = true;

			createBackground(still);
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

        Rectangle rect = new Rectangle();
        
        int x = 0;
        int y = 0;
        
        for (GraphicsDevice screen : screens) {
            Rectangle screenBounds = screen.getDefaultConfiguration().getBounds();

            if (rect.x > screenBounds.x) {
                rect.x = screenBounds.x;
            }
            
            if (rect.y > screenBounds.y) {
                rect.y = screenBounds.y;
            }

            if (x < (screenBounds.x + screenBounds.width)) {
                x = screenBounds.x + screenBounds.width;
            }
            
            if (y < (screenBounds.y + screenBounds.height)) {
                y = screenBounds.y + screenBounds.height;
            }
            
            rect.width = x - rect.x;
            rect.height = y - rect.y;
        }
        
        Robot robot = new Robot();
        BufferedImage screenShot = still ? robot.createScreenCapture(rect) : null;
		
		CoverFrame frame = new CoverFrame(rect, screenShot);
		frame.setVisible(true);
	}
}
