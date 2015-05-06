package com.redpois0n.gscrot.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel {
	
	public static final Color COLOR_GRAY = new Color(211, 211, 211);
	
	private BufferedImage image;

	public ImagePanel() {
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int width = getWidth();
		int height = getHeight();
				
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if ((x + y) % 2 == 1) {
					g.setColor(COLOR_GRAY);
				} else {
					g.setColor(Color.white);
				}
				
				g.fillRect(x * 10, y * 10, 10, 10);
			}
		}
		
		if (image != null) {
			int imageWidth = image.getWidth();
			int imageHeight = image.getHeight();
			
			while (imageWidth > getWidth()) {
				imageWidth /= 2;
				imageHeight /= 2;
			}
			
			while (imageHeight > getHeight()) {
				imageWidth /= 2;
				imageHeight /= 2;
			}
			
			g.drawImage(image, width / 2 - imageWidth / 2, height / 2 - imageHeight / 2, imageWidth, imageHeight, null);
		}
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
		repaint();
	}
}
