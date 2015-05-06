package com.redpois0n.gscrot;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class CaptureUploader {
	
	private static final List<CaptureUploader> UPLOADERS = new ArrayList<CaptureUploader>();
	
	public static final CaptureUploader getSelected() {
		return UPLOADERS.get(0);
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
	
	/**
	 * Called each time a new screenshot is taken
	 * @param image
	 */
	public abstract void process(BufferedImage image);

}
