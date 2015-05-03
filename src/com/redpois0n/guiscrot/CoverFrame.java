package com.redpois0n.guiscrot;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CoverFrame extends JFrame {
	
	public CoverFrame(Rectangle rect) {
		setUndecorated(true);
		setBounds(rect);
		setContentPane(new CoverPanel());
	}
	
	private class CoverPanel extends JPanel {
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
		}
	}

}
