package com.redpois0n.guiscrot;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import org.jnativehook.GlobalScreen;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseMotionListener;

@SuppressWarnings("serial")
public class CoverFrame extends JFrame implements MouseMotionListener, MouseInputListener, NativeMouseMotionListener {
	
	public static final float OPACITY = 0.5F;
	
	private Rectangle rect;
	private Image image;
	
	private int x;
	private int y;
	
	private int x2;
	private int y2;
	
	private boolean dragging;
	private int seed;
	
	private RepaintThread thread;
	

	public CoverFrame(Rectangle rect) {
		this(rect, null);
	}
	
	public CoverFrame(Rectangle rect, Image image) {
		this.rect = rect;
		this.image = image;
		GlobalScreen.addNativeMouseMotionListener(this);
		setUndecorated(true);
		setBounds(rect);
		setContentPane(new CoverPanel());

		if (image == null) {
			setOpacity(OPACITY);
		}
		
		thread = new RepaintThread();
		thread.start();
	}
	
	private class RepaintThread extends Thread {
		@Override
		public void run() {
			while (!interrupted()) {
				if (seed++ >= 10) {
					seed = 0;
				}
				try {
					Thread.sleep(100L);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				
				repaint();
			}
		}
	}
	
	private class CoverPanel extends JPanel {
		
		@Override
		public void paintComponent(Graphics g) {		
			super.paintComponent(g);
			
			if (x > x2) {
				int temp = x;
				x = x2;
				x2 = temp;
			}
			
			if (y > y2) {
				int temp = y;
				y = y2;
				y2 = temp;
			}
			
			/** If nothing is selected, default to x and y**/
			int tx = x2 == 0 ? x : x2;
			int ty = y2 == 0 ? y : y2;
			
			if (image != null) {
				/** Draw image over frame **/
				g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
				
				/** Set color to transparent black **/
				g.setColor(new Color(0, 0, 0, 100));		
				
				/** Draw black transparent color over all areas that isn't being selected **/
				g.fillRect(0, 0, x, getHeight());
				g.fillRect(x, 0, getWidth(), y);			

				g.fillRect(tx, y, getWidth(), getHeight());
				g.fillRect(x, ty, tx - x, getHeight());
			} else {
				g.setColor(Color.black);
				g.fillRect(0, 0, getWidth(), getHeight());
			}	
						
			g.setColor(Color.white);
			RendererUtils.drawMovingRect(x, 0, 1, getHeight(), g, seed);
			RendererUtils.drawMovingRect(0, y, getWidth(), 1, g, seed);
			
			if (dragging) {
				g.drawString("X: " + (x + rect.x) + ", Y: " + (y + rect.y), x, y);
			}
			
			if (x2 != 0 && y2 != 0) {
				g.setColor(Color.red);
				g.drawRect(x, y, tx - x, ty - y);
				RendererUtils.drawMovingRect(x, y, tx - x, ty - y, g, seed);
			}			
		}
	}

	@Override
	protected void finalize() {
		thread.interrupt();
	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent arg0) {
		x2 = arg0.getX() - rect.x;
		y2 = arg0.getY() - rect.y;
		
		repaint();
	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent arg0) {
		if (!dragging) {
			x = arg0.getX() - rect.x;
			y = arg0.getY() - rect.y;
		}

		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		
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
		dragging = true;
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		dragging = false;
		x2 = 0;
		y2 = 0;
		repaint();
	}

}
