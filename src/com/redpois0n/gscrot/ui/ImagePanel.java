package com.redpois0n.gscrot.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel {
	
	private BufferedImage image;

	public ImagePanel() {
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (image != null) {
			g.drawImage(image, getWidth() / 2 - image.getWidth() / 2, getHeight() / 2 - image.getHeight() / 2, image.getWidth(), image.getHeight(), null);
			
			if (getWidth() > image.getWidth()) {
				
			}
		}
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
		repaint();
	}
}
