package com.redpois0n.guiscrot;

import java.awt.image.BufferedImage;

public class Capture extends Thread {
	
	public static enum Type {
		REGION, MONITOR, FULL;
	}
	
	private final Type type;
	private final BufferedImage image;
	
	public Capture(Type type, BufferedImage image) {
		this.type = type;
		this.image = image;
	}
	
	@Override
	public void run() {
		
	}
	
	public Type getType() {
		return type;
	}

	public BufferedImage getImage() {
		return image;
	}
}
