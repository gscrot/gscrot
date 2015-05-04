package com.redpois0n.guiscrot;

import java.awt.Color;
import java.awt.Graphics;

public class RendererUtils {

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
				
				g.drawLine(i + seed, y, i + seed + 10, y);
				g.drawLine(i + seed, y + height, i + seed + 10, y + height);
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
				
				g.drawLine(x, i + seed, x, i + seed + 10);
				g.drawLine(x + width, i + seed, x + width, i + seed + 10);
			}
		}
	}

	public static void drawOutlinedString(String s, int x, int y, Color fill, Color outline, Graphics g) {
		g.setColor(outline);
		g.drawString(s, x + 1, y - 1);
		g.drawString(s, x - 1, y + 1);
		g.drawString(s, x + 1, y - 1);
		g.drawString(s, x + 1, y + 1);
		g.setColor(fill);
		g.drawString(s, x, y);
	}

}
