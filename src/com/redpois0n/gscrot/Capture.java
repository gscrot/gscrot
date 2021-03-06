package com.redpois0n.gscrot;

import iconlib.IconUtils;

import java.awt.Graphics2D;
import java.awt.TrayIcon.MessageType;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Icon;

import com.redpois0n.gscrot.exceptions.InvalidUploaderException;
import com.redpois0n.gscrot.ui.MainFrame;
import com.redpois0n.gscrot.ui.TrayIconHelper;
import com.redpois0n.gscrot.utils.NameFormatter;

public class Capture extends Thread {
	
	public static enum Type {
		REGION("Region", "region-select"), 
		MONITOR("Monitor", "monitor"), 
		FULL("Fullscreen", null), 
		WINDOW("Window", "window");
		
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
		this(type, Format.getFromString(Config.get(Config.KEY_PREFERRED_FORMAT, "png")), image, filename);
	}
	
	public Capture(Type type, BufferedImage image) {
		this(type, Format.getFromString(Config.get(Config.KEY_PREFERRED_FORMAT, "png")), image, NameFormatter.format(Config.get(Config.KEY_DEFAULT_FILENAME, "image")));
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
		
		if (Config.get(Config.KEY_COPY_IMAGE_TO_CLIPBOARD, true)) {
			ClipboardHelper.setImage(image);
		}
		
		try {
			loadBinary();

			Graphics2D g = image.createGraphics();
			
			List<GraphicsImageProcessor> gips = new ArrayList<GraphicsImageProcessor>();
			List<BinaryImageProcessor> bips = new ArrayList<BinaryImageProcessor>();
			
			for (Object processor : ImageProcessor.getEnabled()) {
				if (processor instanceof GraphicsImageProcessor) {
					GraphicsImageProcessor gip = (GraphicsImageProcessor) processor;
					gips.add(gip);
				} else if (processor instanceof BinaryImageProcessor) {
					BinaryImageProcessor bip = (BinaryImageProcessor) processor;
					bips.add(bip);
				}
			}
			
			for (GraphicsImageProcessor processor : gips) {
				processor.process(g, image.getWidth(), image.getHeight());
			}
			
			for (BinaryImageProcessor processor : bips) {
				binary = processor.process(binary);
			}
			
			CaptureUploader uploader = CaptureUploader.getSelected();

			if (uploader != null) {
				this.response = uploader.process(this);
			} else {
				throw new InvalidUploaderException("No uploader selected");
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
