package com.redpois0n.gscrot.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.redpois0n.gscrot.utils.ImageUtils;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel {
	
	public static final Color COLOR_GRAY = new Color(211, 211, 211);
	
	private BufferedImage image;

	public ImagePanel() {
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		addMouseListener(new ClickListener());
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
			
			while (imageWidth > getWidth() || imageHeight > getHeight()) {
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
	
	private class ClickListener extends MouseAdapter {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2 && image != null) {
				try {
					ImageUtils.open(image);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}
