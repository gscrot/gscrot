package com.redpois0n.gscrot;

import java.awt.image.BufferedImage;

public class Capture extends Thread {
	
	public static enum Type {
		REGION("Region"), 
		MONITOR("Monitor"), 
		FULL("Fullscreen");
		
		private String textual;
		
		private Type(String textual) {
			this.textual = textual;
		}
		
		@Override
		public String toString() {
			return this.textual;
		}
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
