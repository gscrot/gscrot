package com.redpois0n.guiscrot;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

public class ScreenshotHelper {
	
	/**
	 * Capture all monitors 
	 * Tested on 3 + 1 configuration
	 * @return
	 * @throws Exception
	 */
	public static Rectangle getWholeDesktop() throws Exception {
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
        
        return rect;
	}
	
	public static BufferedImage capture(Rectangle rect) throws Exception {
		Robot robot = new Robot();
        BufferedImage screenShot = robot.createScreenCapture(rect);
        
        return screenShot;
	}

}
