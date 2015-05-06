package com.redpois0n.gscrot.util;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageUtils {

	/**
	 * Writes image to disk and opens in default editor
	 * 
	 * @param image
	 */
	public static void open(BufferedImage image) throws Exception {
		File file = File.createTempFile("gscrot", ".png");
		ImageIO.write(image, "png", file);

		Desktop.getDesktop().open(file);
	}

	/**
	 * Creates transparent cursor
	 * http://www.rgagnon.com/javadetails/java-0440.html
	 */
	public static Cursor createTransparentCursor() {
		int[] pixels = new int[16 * 16];
		Image image = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(16, 16, pixels, 0, 16));
		Cursor transparentCursor = Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(0, 0), "invisibleCursor");
		
		return transparentCursor;
	}

}
