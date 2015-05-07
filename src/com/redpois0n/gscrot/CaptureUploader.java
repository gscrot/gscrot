package com.redpois0n.gscrot;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

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

	public CaptureUploader(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	/**
	 * Called each time a new screenshot is taken
	 * @param image
	 */
	public abstract void process(BufferedImage image);

}
