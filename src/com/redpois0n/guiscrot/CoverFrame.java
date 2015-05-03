package com.redpois0n.guiscrot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CoverFrame extends JFrame {
	
	public static final float OPACITY = 0.5F;
	
	public CoverFrame(Rectangle rect) {
		setUndecorated(true);
		setBounds(rect);
		setContentPane(new CoverPanel());
		setOpacity(OPACITY);
	}
	
	private class CoverPanel extends JPanel {
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			g.setColor(Color.black);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
	}

}
