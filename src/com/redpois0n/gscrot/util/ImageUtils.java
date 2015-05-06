package com.redpois0n.gscrot.util;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageUtils {
	
	/**
	 * Writes image to disk and opens in default editor
	 * @param image
	 */
	public static void open(BufferedImage image) throws Exception {
		File file = File.createTempFile("gscrot", ".png");
		ImageIO.write(image, "png", file);
		
		Desktop.getDesktop().open(file);
	}

}
