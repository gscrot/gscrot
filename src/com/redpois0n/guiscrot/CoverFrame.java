package com.redpois0n.guiscrot;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import org.jnativehook.GlobalScreen;

@SuppressWarnings("serial")
public class CoverFrame extends JFrame implements KeyListener, MouseMotionListener, MouseInputListener {
	
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
		setUndecorated(true);
		setBounds(rect);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		
		CoverPanel cp = new CoverPanel();
		cp.addKeyListener(this);
		setContentPane(cp);

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
				if (seed++ >= 20) {
					seed = 0;
				}
				
				repaint();

				try {
					Thread.sleep(100L);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	private class CoverPanel extends JPanel {
		
		public CoverPanel() {
			setFocusable(true);
		}
		
		@Override
		public void paintComponent(Graphics g) {		
			super.paintComponent(g);
			
			if (g instanceof Graphics2D) {
				((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
			}
			
			if (x > x2 && x2 != 0) {
				int temp = x;
				x = x2;
				x2 = temp;
			}
			
			if (y > y2 && y2 != 0) {
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
			
			g.setFont(new Font("Arial", Font.BOLD, 16));

			RendererUtils.drawOutlinedString("X " + (x + rect.x) + " / Y " + (y + rect.y), x + 2, y - 2, Color.white, Color.black, g);	

			if (x2 != 0 && y2 != 0) {
				RendererUtils.drawOutlinedString("Width " + (x2 - x) + " / Height " + (y2 - y), x + 2, y - 4 - g.getFontMetrics().getHeight(), Color.white, Color.black, g);	
			
				g.setColor(Color.red);
				g.drawRect(x, y, tx - x, ty - y);
				RendererUtils.drawMovingRect(x, y, tx - x, ty - y, g, seed);
			}		
		}
	}
	
	/**
	 * Called when enter is pressed or mouse released, if preferred
	 */
	public void submit() {
		setVisible(false);
		dispose();
	}

	/**
	 * Closes thread when object is gb'd
	 */
	@Override
	protected void finalize() {
		thread.interrupt();
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		dragging = true;

		x2 = arg0.getX();
		y2 = arg0.getY();

		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		if (!dragging && x2 == 0 && y2 == 0) {
			x = arg0.getX();
			y = arg0.getY();
		}

		repaint();
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
		x = arg0.getX();
		y = arg0.getY();
		x2 = 0;
		y2 = 0;
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		dragging = false;
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
			submit();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

}
