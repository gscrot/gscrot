package com.redpois0n.gscrot.ui;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class ScreenColorPicker extends RegionCapture {

	public ScreenColorPicker(Rectangle rect, Image image) {
		super(rect, image);
	}
	
	@Override
	public void mousePressed(MouseEvent arg0) {
		System.out.println("clik");
	}

}
