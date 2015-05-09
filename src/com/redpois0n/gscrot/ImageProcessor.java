package com.redpois0n.gscrot;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public abstract class ImageProcessor {
	
	private static final List<ImageProcessor> UPLOADERS = new ArrayList<ImageProcessor>();
	private static final List<ImageProcessor> ENABLED = new ArrayList<ImageProcessor>();
	
	public static final List<ImageProcessor> getEnabled() {
		return ENABLED;
	}
	
	public static final List<ImageProcessor> getAllProcessors() {
		return UPLOADERS;
	}
	
	public static final void enable(ImageProcessor a) {
		ENABLED.add(a);
	}
	
	public static final void disable(ImageProcessor a) {
		ENABLED.remove(a);
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
	private final ImageIcon icon;
	
	public ImageProcessor(String name, ImageIcon icon) {
		this.name = name;
		this.icon = icon;
	}
	
	public String getName() {
		return name;
	}
	
	public ImageIcon getIcon() {
		return icon;
	}

	/**
	 * Called when an image is taken before passed to uploader
	 * @param g
	 */
	public abstract void process(Graphics2D g);
}
