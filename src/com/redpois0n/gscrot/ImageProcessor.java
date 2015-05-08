package com.redpois0n.gscrot;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public abstract class ImageProcessor {
	
	private static final List<ImageProcessor> UPLOADERS = new ArrayList<ImageProcessor>();
	
	public static final List<ImageProcessor> getAllProcessors() {
		return UPLOADERS;
	}

	public static final void addProcessor(ImageProcessor a) {
		UPLOADERS.add(a);
	}
	
	/**
	 * Remove global action
	 * @param a
	 */
	public static final void removeProcessor(ImageProcessor a) {
		UPLOADERS.remove(a);
	}
	
	private final String name;
	
	public ImageProcessor(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	/**
	 * Called when an image is taken before passed to uploader
	 * @param g
	 */
	public abstract void process(Graphics2D g);
}
