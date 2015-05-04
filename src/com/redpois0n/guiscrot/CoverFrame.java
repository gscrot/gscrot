package com.redpois0n.guiscrot;

import iconlib.IconUtils;

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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

@SuppressWarnings("serial")
public class CoverFrame extends JFrame implements KeyListener, MouseMotionListener, MouseInputListener {
	
	public static final int PREVIEW_SIZE = 150;
	public static final float PREVIEW_SCALE = 8F;
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
				if (seed++ > 20) {
					seed = 2;
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
			
			int x = Math.min(CoverFrame.this.x, CoverFrame.this.x2);
			int y = Math.min(CoverFrame.this.y, CoverFrame.this.y2);
			int x2 = Math.max(CoverFrame.this.x, CoverFrame.this.x2);
			int y2 = Math.max(CoverFrame.this.y, CoverFrame.this.y2);
			
			/** If nothing is selected, default to x and y**/
			int tx = x2 == 0 ? x : x2;
			int ty = y2 == 0 ? y : y2;
			
			if (image != null) {
				/** Draw image over frame **/
				g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
				
				/** Set color to transparent black **/
				g.setColor(new Color(0, 0, 0, 100));							 
			} else {
				g.setColor(Color.black);
			}	

			/** Draw black transparent color over all areas that isn't being selected **/
			if (x2 == 0 && y2 == 0) {
				g.fillRect(0, 0, getWidth(), getHeight());
			} else {
				g.fillRect(0, 0, x, getHeight());
				g.fillRect(x, 0, getWidth(), y);			

				g.fillRect(tx, y, getWidth(), getHeight());
				g.fillRect(x, ty, tx - x, getHeight());		
			}
			
			
			Image cursor = IconUtils.getIcon("cursor").getImage();
			
			g.setFont(new Font("Arial", Font.BOLD, 16));

			RendererUtils.drawOutlinedString("X " + (x + rect.x) + " / Y " + (y + rect.y), x + 2, y - 2, Color.white, Color.black, g);	
			
			/** Draw cross **/
			g.setColor(Color.white);
			RendererUtils.drawMovingRect(tx, 0, 0, getHeight(), g, seed);
			RendererUtils.drawMovingRect(0, ty, getWidth(), 0, g, seed);
			
			if (x2 - x != 0 && y2 - y != 0) {
				RendererUtils.drawOutlinedString("Width " + (x2 - x) + " / Height " + (y2 - y), x + 2, y - 4 - g.getFontMetrics().getHeight(), Color.white, Color.black, g);	
				
				g.setColor(Color.white);
				g.drawRect(x, y, tx - x, ty - y);
				RendererUtils.drawMovingRect(x, y, tx - x, ty - y, g, seed);
			
			
				/** Draw cursor **/
				g.drawImage(cursor, tx - cursor.getWidth(null) / 2, ty - cursor.getHeight(null) / 2, null);
				
				BufferedImage preview = new BufferedImage(PREVIEW_SIZE, PREVIEW_SIZE, BufferedImage.TYPE_INT_RGB);
				int pos = PREVIEW_SIZE / (int) PREVIEW_SCALE;
				pos /= 2;
				preview.createGraphics().drawImage(image, 0, 0, PREVIEW_SIZE, PREVIEW_SIZE, x2 - pos, y2 - pos, x2 + PREVIEW_SIZE - pos, y2 + PREVIEW_SIZE - pos, null);

				preview = RendererUtils.scale(preview, BufferedImage.TYPE_INT_RGB, PREVIEW_SIZE, PREVIEW_SIZE, PREVIEW_SCALE);
	
				preview = RendererUtils.createCircle(preview);
				
				g.drawImage(preview, x2, y2, PREVIEW_SIZE, PREVIEW_SIZE, null);
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
		if (!dragging && x == x2 && y == y2) {
			x = arg0.getX();
			y = arg0.getY();
			x2 = arg0.getX();
			y2 = arg0.getY();
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
		x2 = arg0.getX();
		y2 = arg0.getY();
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
		} else if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
			x = 0;
			y = 0;
			x2 = 0;
			y2 = 0;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

}
