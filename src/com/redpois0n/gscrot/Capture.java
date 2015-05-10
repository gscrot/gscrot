package com.redpois0n.gscrot;

import iconlib.IconUtils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import javax.swing.Icon;

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
	private final Format format;
	private final BufferedImage image;
	
	private Status status;
	
	private UploadResponse response;
	
	public Capture(Type type, BufferedImage image) {
		this(type, Format.PNG, image);
	}
	
	public Capture(Type type, Format format, BufferedImage image) {
		this.type = type;
		this.format = format;
		this.image = image;
	}
	
	/**
	 * Called after screenshot is taken
	 */
	@Override
	public void run() {
		setStatus(Status.STARTING);
		MainFrame.INSTANCE.add(this);
		
		Graphics2D g = image.createGraphics();
		
		for (ImageProcessor processor : ImageProcessor.getEnabled()) {
			processor.process(g);
		}
		
		CaptureUploader uploader = CaptureUploader.getSelected();

		if (uploader != null) {
			try {
				this.response = uploader.process(this);
			} catch (Exception e) {
				// TODO
				e.printStackTrace();
			}
		}
		
		
		setStatus(Status.COMPLETE);
	}
	
	public Type getType() {
		return type;
	}

	public BufferedImage getImage() {
		return image;
	}
	
	/**
	 * Returns this capture as a byte array
	 * @return
	 * @throws Exception
	 */
	public byte[] getBinary() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, format.toString(), baos);
		return baos.toByteArray();
	}
	
	public void setStatus(Status status) {
		this.status = status;
		MainFrame.INSTANCE.repaint();
		
		if (status == Status.COMPLETE) {
			MainFrame.INSTANCE.setTitle(null);
		} else {
			MainFrame.INSTANCE.setTitle(status.toString());
		}
	}
	
	public UploadResponse getResponse() {
		return this.response;
	}
	
	public Format getFormat() {
		return this.format;
	}

	public Status getStatus() {
		return status;
	}
}
