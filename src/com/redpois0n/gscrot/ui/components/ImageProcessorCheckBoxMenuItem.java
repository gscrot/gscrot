package com.redpois0n.gscrot.ui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;

import com.redpois0n.gscrot.ImageProcessor;

@SuppressWarnings("serial")
public class ImageProcessorCheckBoxMenuItem extends JCheckBoxMenuItem implements ActionListener {

	private ImageProcessor processor;
	
	public ImageProcessorCheckBoxMenuItem(ImageProcessor processor) {
		this.processor = processor;
		
		setText(processor.getName());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
