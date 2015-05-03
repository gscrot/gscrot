package com.redpois0n.guiscrot;

import java.awt.Color;
import java.awt.Graphics;

public class RendererUtils {
	
	public static void drawMovingRect(int x, int y, int width, int height, Graphics g, int seed) {		
		for (int i = x, pos = 0; i < x + width; i += 10, pos++) {
			if (pos % 2 == 0) {
				g.setColor(Color.black);
			} else {
				g.setColor(Color.white);
			}
			
			g.drawLine(i + seed, y, i + seed + 10, y);
		}
		
		for (int i = y, pos = 0; i < y + height; i += 10, pos++) {
			if (pos % 2 == 0) {
				g.setColor(Color.black);
			} else {
				g.setColor(Color.white);
			}
			
			g.drawLine(x, i + seed, x, i + seed + 10);
		}
	}

}
