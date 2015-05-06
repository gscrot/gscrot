package com.redpois0n.gscrot;

import iconlib.IconUtils;

import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.JLabel;

import com.redpois0n.gscrot.actions.AfterCaptureAction;
import com.redpois0n.gscrot.ui.MainFrame;

public class Capture extends Thread {
	
	public static enum Type {
		REGION("Region", "region-select"), 
		MONITOR("Monitor", "monitor"), 
		FULL("Fullscreen", null);
		
		private final String textual;
		private final String icon;
		
		private Type(String textual, String icon) {
			this.textual = textual;
			this.icon = icon;
		}
		
		@Override
		public String toString() {
			return this.textual;
		}

		public Icon getIcon() {
			return IconUtils.getIcon(this.icon);
		}
	}
	
	public static enum Status {
		STARTING("Starting", "up-button"),
		COMPLETE("Complete", "tick-button");
		
		private final String textual;
		private final String icon;
		
		private Status(String textual, String icon) {
			this.textual = textual;
			this.icon = icon;
		}
		
		@Override
		public String toString() {
			return this.textual;
		}

		public Icon getIcon() {
			return IconUtils.getIcon(this.icon);
		}
	}
	
	private final Type type;
	private final BufferedImage image;
	
	private Status status = Status.STARTING;
	
	public Capture(Type type, BufferedImage image) {
		this.type = type;
		this.image = image;
	}
	
	/**
	 * Called after screenshot is taken
	 */
	@Override
	public void run() {
		MainFrame.INSTANCE.add(this);
		
		for (AfterCaptureAction aca : AfterCaptureAction.getActions()) {
			aca.process(image);
		}
		
		setStatus(Status.COMPLETE);
	}
	
	public Type getType() {
		return type;
	}

	public BufferedImage getImage() {
		return image;
	}
	
	public void setStatus(Status status) {
		this.status = status;
		MainFrame.INSTANCE.repaint();
	}

	public Status getStatus() {
		return status;
	}
}
