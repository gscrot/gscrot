package com.redpois0n.gscrot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class RenderUtils {

	/**
	 * Draws moving rectangle used for cross and borders in cover
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param g
	 * @param seed
	 */
	public static void drawMovingRect(int x, int y, int width, int height, Graphics g, int seed) {		
		if (width > 1) {
			for (int i = x, pos = 0; i < x + width; i += 10, pos++) {
				if (pos % 2 == 0) {
					g.setColor(Color.black);
				} else {
					g.setColor(Color.white);
				}
				
				int z = i + seed + 10;
				if (z > x + width) {
					continue;
				}
				
				g.drawLine(i + seed, y, z, y);
				g.drawLine(i + seed, y + height, z, y + height);
			}
		}

		if (height > 1) {
			for (int i = y, pos = 0; i < y + height; i += 10, pos++) {
				if (pos % 2 == 0) {
					g.setColor(Color.black);
				} else {
					g.setColor(Color.white);
				}
				
				int z = i + seed + 10;
				if (z > y + height) {
					continue;
				}
				
				g.drawLine(x, i + seed, x, z);
				g.drawLine(x + width, i + seed, x + width, z);
			}
		}
	}
	
	/**
	 * Scales to center
	 * @param sbi
	 * @param imageType
	 * @param w
	 * @param h
	 * @param scale
	 * @return
	 */
	public static BufferedImage scale(BufferedImage sbi, int imageType, int w, int h, float scale) {
	    BufferedImage dbi = new BufferedImage(w, h, imageType);
	    Graphics2D g = dbi.createGraphics();
	    AffineTransform at = AffineTransform.getScaleInstance(scale, scale);
	    g.drawRenderedImage(sbi, at);
	    
	    AffineTransform f = new AffineTransform();
		f.translate((w / 2) - (w * (scale)) / 2, (h / 2) - (w * (scale)) / 2);
		f.scale(scale, scale);
		g.setTransform(f);

	    
	    return dbi;
	}

	/**
	 * Creates circle with transparent background from image (and border)
	 * https://stackoverflow.com/questions/10852959/java-how-to-draw-a-transparent-shape-using-a-graphics-object-g
	 * @param image
	 * @return
	 */
	public static BufferedImage createCircle(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();
		BufferedImage i1 = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);


		Ellipse2D.Double ellipse1 = new Ellipse2D.Double(w / 16, h / 16, 7 * w / 8, 7 * h / 8);
		Area circle = new Area(ellipse1);

		Graphics2D g = i1.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.setClip(circle);
		g.drawImage(image, 0, 0, null);
		g.setClip(null);
		Stroke s = new BasicStroke(2);
		g.setStroke(s);
		g.setColor(Color.BLACK);
		g.draw(circle);
		g.dispose();

		return i1;
	}

	/**
	 * Draws basic nice looking outlined string
	 * @param s
	 * @param x
	 * @param y
	 * @param fill
	 * @param outline
	 * @param g
	 */
	public static void drawOutlinedString(String s, int x, int y, Color fill, Color outline, Graphics g) {
		g.setColor(outline);
		g.drawString(s, x + 1, y - 1);
		g.drawString(s, x - 1, y + 1);
		g.drawString(s, x + 1, y - 1);
		g.drawString(s, x + 1, y + 1);
		g.setColor(fill);
		g.drawString(s, x, y);
	}
	
	public static void grid(BufferedImage image, int between) {
		Graphics g = image.getGraphics();
		
		g.setColor(Color.gray.brighter());
		
		for (int x = 0; x < image.getWidth(); x += between) {
			g.drawRect(x, 0, 0, image.getHeight());
		}
		
		for (int y = 0; y < image.getHeight(); y += between) {
			g.drawRect(0, y, image.getWidth(), 0);
		}
	}

}
