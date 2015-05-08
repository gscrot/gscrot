package com.redpois0n.gscrot;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public abstract class CaptureUploader {
	
	private static final List<CaptureUploader> UPLOADERS = new ArrayList<CaptureUploader>();
	
	/**
	 * Gets selected uploader (From top of the list)
	 * @return
	 */
	public static final CaptureUploader getSelected() {
		return UPLOADERS.get(0);
	}

	/**
	 * Sets selected uploader (adds it to top of list)
	 * @param uploader
	 */
	public static void setSelected(CaptureUploader uploader) {
		UPLOADERS.remove(uploader);
		UPLOADERS.add(0, uploader);
	}
	
	public static final List<CaptureUploader> getAllUploaders() {
		return UPLOADERS;
	}

	public static final void addUploader(CaptureUploader a) {
		UPLOADERS.add(a);
	}
	
	/**
	 * Remove global action
	 * @param a
	 */
	public static final void removeUploader(CaptureUploader a) {
		UPLOADERS.remove(a);
	}
	
	private final String name;
	private final ImageIcon icon;
	
	public CaptureUploader(String name) {
		this(name, null);
	}
	
	public CaptureUploader(String name, ImageIcon icon) {
		this.name = name;
		this.icon = icon;
	}
	
	public String getName() {
		return this.name;
	}
	
	public ImageIcon getIcon() {
		return this.icon;
	}
	
	/**
	 * Called each time a new screenshot is taken
	 * @param image
	 */
	public abstract UploadResponse process(Capture capture) throws Exception;

}
