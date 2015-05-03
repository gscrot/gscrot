package com.redpois0n.guiscrot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jnativehook.GlobalScreen;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseMotionListener;

@SuppressWarnings("serial")
public class CoverFrame extends JFrame implements NativeMouseMotionListener {
	
	public static final float OPACITY = 0.5F;
	
	private Rectangle rect;
	
	private int x;
	private int y;
	
	public CoverFrame(Rectangle rect) {
		this.rect = rect;
		GlobalScreen.addNativeMouseMotionListener(this);
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
			
			g.setColor(Color.white);
			g.fillRect(x, 0, 1, getHeight());
			g.fillRect(0, y, getWidth(), 1);
		}
	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent arg0) {
		
	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent arg0) {
		x = arg0.getX() - rect.x;
		y = arg0.getY() - rect.y;

		repaint();
	}

}
