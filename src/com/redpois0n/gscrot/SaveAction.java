package com.redpois0n.gscrot;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class SaveAction extends AfterCaptureAction {
	
	private File dir;

	public SaveAction(String s) {
		this(new File(s));
	}
	
	public SaveAction(File dir) {
		this.dir = dir;
	}

	@Override
	public void process(BufferedImage image) {
		try {
			ImageIO.write(image, "png", new File(dir, Logger.getTimeString() + ".png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
