package com.redpois0n.gscrot.actions;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.redpois0n.gscrot.Logger;

public class SaveAction extends Action {
	
	private File dir;

	public SaveAction(String s) {
		this(new File(s));
	}
	
	public SaveAction(File dir) {
		this.dir = dir;
	}

	@Override
	public void process(BufferedImage image) {
		File file = new File(dir, Logger.getTimeFileString() + ".png");
		Logger.log("Saving image to  " + file);

		try {
			ImageIO.write(image, "png", file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
