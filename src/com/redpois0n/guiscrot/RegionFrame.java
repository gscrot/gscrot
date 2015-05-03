package com.redpois0n.guiscrot;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseMotionListener;

@SuppressWarnings("serial")
public class RegionFrame extends JFrame implements NativeMouseMotionListener, NativeMouseInputListener {
	
	public RegionFrame() {
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
		setOpacity(0.5F);
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
		super.setLocation(arg0.getX(), arg0.getY());
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent arg0) {
		
	}

}