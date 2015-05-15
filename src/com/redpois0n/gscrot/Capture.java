package com.redpois0n.gscrot;

import iconlib.IconUtils;

import java.awt.Graphics2D;
import java.awt.TrayIcon.MessageType;
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
	
	private byte[] binary;
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
		
		if (Config.get(Config.KEY_COPY_IMAGE_TO_CLIPBOARD, "true").equalsIgnoreCase("true")) {
			ClipboardHelper.setImage(image);
		}
		
		try {
			loadBinary();

			Graphics2D g = image.createGraphics();
			
			for (GraphicsImageProcessor processor : ImageProcessor.getGraphicsProcessors()) {
				processor.process(g);
			}
			
			for (BinaryImageProcessor processor : ImageProcessor.getBinaryProcessors()) {
				binary = processor.process(binary);
			}
			
			CaptureUploader uploader = CaptureUploader.getSelected();

			if (uploader != null) {
				this.response = uploader.process(this);
			} else {
				throw new Exception("No uploader selected");
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			setStatus(Status.ERROR);
			TrayIconHelper.showMessage("Error: " + e.getClass().getSimpleName() + ": " + e.getMessage(), MessageType.ERROR);
			return;
		} finally {
			TrayIconHelper.setIndeterminate(false);
		}
		
		if (Config.get(Config.KEY_COPY_URL_TO_CLIPBOARD, "true").equalsIgnoreCase("true")) {
			ClipboardHelper.setString(response.getUrl());
			TrayIconHelper.showMessage("URL copied to clipboard", MessageType.INFO);
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
	 * Creates byte array for this image
	 * @throws Exception
	 */
	public void loadBinary() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, format.toString(), baos);
		this.binary = baos.toByteArray();
	}
	
	/**
	 * Returns this capture as a byte array
	 * @return
	 * @throws Exception
	 */
	public byte[] getBinary() throws Exception {
		return binary;
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
