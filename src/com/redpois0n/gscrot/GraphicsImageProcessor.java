package com.redpois0n.gscrot;

import java.awt.Graphics2D;

import javax.swing.ImageIcon;

public abstract class GraphicsImageProcessor extends ImageProcessor {
	
	public GraphicsImageProcessor(String name, ImageIcon icon) {
		super(name, icon);
	}

	public abstract void process(Graphics2D g);

}
