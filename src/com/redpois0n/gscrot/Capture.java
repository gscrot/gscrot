package com.redpois0n.gscrot;

import iconlib.IconUtils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import javax.swing.Icon;

import com.redpois0n.gscrot.ui.MainFrame;
import com.redpois0n.gscrot.util.NameFormatter;

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
		COMPLETE("Complete", "tick-button"),
		ERROR("Error", "cross");
		
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
	
	private String filename;
	
	private Status status;
	
	private UploadResponse response;
	
	public Capture(Type type, BufferedImage image, String filename) {
		this(type, Format.getFromString(Config.get("preferred-format", "png")), image, filename);
	}
	
	public Capture(Type type, BufferedImage image) {
		this(type, Format.getFromString(Config.get("preferred-format", "png")), image, NameFormatter.format(Config.get("filename", "image")));
	}
	
	public Capture(Type type, Format format, BufferedImage image, String filename) {
		this.type = type;
		this.format = format;
		this.image = image;
		this.filename = filename + "." + format.toString();
	}
	
	/**
	 * Called after screenshot is taken
	 */
	@Override
	public void run() {
		TrayIconHelper.setIndeterminate(true);
		setStatus(Status.STARTING);
		MainFrame.INSTANCE.add(this);
		
		try {
			Graphics2D g = image.createGraphics();
			
			for (ImageProcessor processor : ImageProcessor.getEnabled()) {
				processor.process(g);
			}
			
			CaptureUploader uploader = CaptureUploader.getSelected();

			if (uploader != null) {
				this.response = uploader.process(this);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			setStatus(Status.ERROR);
			return;
		} finally {
			TrayIconHelper.setIndeterminate(false);
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
		return this.status;
	}
	
	public String getFilename() {
		return this.filename;
	}
}
