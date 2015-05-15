package com.redpois0n.gscrot;

import javax.swing.ImageIcon;

public abstract class BinaryImageProcessor extends ImageProcessor {

	public BinaryImageProcessor(String name, ImageIcon icon) {
		super(name, icon);
	}

	public abstract byte[] process(byte[] b);

}
