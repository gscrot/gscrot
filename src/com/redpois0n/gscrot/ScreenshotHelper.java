package com.redpois0n.gscrot;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import com.redpois0n.gscrot.Capture.Type;

public class ScreenshotHelper {
	
	/**
	 * Capture all monitors 
	 * Tested on 3 + 1 configuration
	 * @return
	 * @throws Exception
	 */
	public static Rectangle getWholeDesktop() throws Exception {
        Rectangle rect = new Rectangle();
        
        int x = 0;
        int y = 0;
        
        for (GraphicsDevice screen : getScreens()) {
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
        
        return rect;
	}
	
	public static void captureScreen(GraphicsDevice screen) {
		try {
			BufferedImage image = ScreenshotHelper.getScreen(screen);
			process(image);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void process(BufferedImage image) {
		Capture p = new Capture(Type.MONITOR, image);
		p.start();
	}
	
	public static BufferedImage getScreen(GraphicsDevice screen) throws Exception {
		return capture(screen.getDefaultConfiguration().getBounds());
	}
	
	public static BufferedImage capture(Rectangle rect) throws Exception {
		Robot robot = new Robot();
        BufferedImage screenShot = robot.createScreenCapture(rect);
        
        return screenShot;
	}
	
	/**
	 * Gets current screen bounds where mouse pointer is
	 * @return
	 * @throws Exception
	 */
	public static Rectangle getCurrentScreen() throws Exception {
		PointerInfo i = MouseInfo.getPointerInfo();
		GraphicsDevice p = i.getDevice();
				
		return p.getDefaultConfiguration().getBounds();
	}
	
	public static GraphicsDevice[] getScreens() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screens = ge.getScreenDevices();

        return screens;
	}

}
