package com.redpois0n.guiscrot;

import java.awt.image.BufferedImage;

public class Capture extends Thread {
	
	public static enum Type {
		REGION, MONITOR, FULL;
	}
	
	private final BufferedImage image;
	
	public Capture(BufferedImage image) {
		this.image = image;
	}
	
	@Override
	public void run() {
		
	}

	public BufferedImage getImage() {
		return image;
	}
}
