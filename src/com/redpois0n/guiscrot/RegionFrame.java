package com.redpois0n.guiscrot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseMotionListener;

@SuppressWarnings("serial")
public class RegionFrame extends JFrame implements MouseInputListener, NativeMouseMotionListener, NativeMouseInputListener {
	
	public static final float OPACITY = 0.5F;
	
	private boolean dragging;
	
	public RegionFrame(boolean still) {
		setAlwaysOnTop(true);
		setBounds(0, 0, 450, 450);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				dispose();
				
				System.exit(0); // TODO
			}
		});
		setUndecorated(true);
		setLocationRelativeTo(null);
		setContentPane(new CoverPanel());
		setOpacity(OPACITY);
		addMouseListener(this);
	}
	
	private class CoverPanel extends JPanel {
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
		}
	} 

	@Override
	public void nativeMouseDragged(NativeMouseEvent arg0) {		
		Point loc = super.getLocationOnScreen();
		int sx = (int) loc.getX();
		int sy = (int) loc.getY();
		
		super.setSize(arg0.getX() - sx, arg0.getY() - sy);
	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent arg0) {
				
	}

	@Override
	public void nativeMouseClicked(NativeMouseEvent arg0) {
		
	}

	@Override
	public void nativeMousePressed(NativeMouseEvent arg0) {
		dragging = true;
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent arg0) {
		dragging = false;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		super.setLocation((int) arg0.getLocationOnScreen().getX(), (int) arg0.getLocationOnScreen().getY());
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		
	}

}