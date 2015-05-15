package com.redpois0n.gscrot;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public abstract class ImageProcessor {
	
	private static final List<BinaryImageProcessor> BINARY_PROCESSORS = new ArrayList<BinaryImageProcessor>();
	private static final List<GraphicsImageProcessor> GRAPHICS_PROCESSORS = new ArrayList<GraphicsImageProcessor>();

	private static final List<Object> ENABLED = new ArrayList<Object>();
	
	public static final List<Object> getEnabled() {
		return ENABLED;
	}
	
	public static final List<BinaryImageProcessor> getBinaryProcessors() {
		return BINARY_PROCESSORS;
	}
	
	public static final List<GraphicsImageProcessor> getGraphicsProcessors() {
		return GRAPHICS_PROCESSORS;
	}
	
	public static final void enable(Object a) {
		ENABLED.add(a);
	}
	
	public static final void disable(Object a) {
		ENABLED.remove(a);
	}

	public static final void addGraphicsProcessor(GraphicsImageProcessor a) {
		GRAPHICS_PROCESSORS.add(a);
	}

	public static final void removeGraphicsProcessor(GraphicsImageProcessor a) {
		GRAPHICS_PROCESSORS.remove(a);
	}
	
	public static final void addBinaryProcessor(BinaryImageProcessor a) {
		BINARY_PROCESSORS.add(a);
	}

	public static final void removeBinaryProcessor(BinaryImageProcessor a) {
		BINARY_PROCESSORS.remove(a);
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
