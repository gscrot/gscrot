package com.redpois0n.gscrot.ui;

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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import com.redpois0n.gscrot.Capture;
import com.redpois0n.gscrot.RenderUtils;
import com.redpois0n.gscrot.util.ImageUtils;

@SuppressWarnings("serial")
public class RegionCapture extends JFrame implements KeyListener, MouseMotionListener, MouseInputListener {
	
	public static final int PREVIEW_SIZE = 150;
	public static final float PREVIEW_SCALEF = 8F;
	public static final int PREVIEW_SCALE = (int) PREVIEW_SCALEF;
	public static final float OPACITY = 0.5F;
	
	protected Rectangle rect;
	protected Image image;
	
	protected int x;
	protected int y;
	
	protected int x2;
	protected int y2;
	
	protected boolean dragging;
	protected int seed;
	
	private RepaintThread thread;

	public RegionCapture(Rectangle rect) {
		this(rect, null);
	}
	
	public RegionCapture(Rectangle rect, Image image) {
		this.rect = rect;
		this.image = image;
		setUndecorated(true);
		setBounds(rect);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		
		setCursor(ImageUtils.createTransparentCursor());
		
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
			
			// Get values in "order" to not fuck up rectangles
			
			int x = Math.min(RegionCapture.this.x, RegionCapture.this.x2);
			int y = Math.min(RegionCapture.this.y, RegionCapture.this.y2);
			int x2 = Math.max(RegionCapture.this.x, RegionCapture.this.x2);
			int y2 = Math.max(RegionCapture.this.y, RegionCapture.this.y2);
			
			// If nothing is selected, default to x and y
			int tx = x2 == 0 ? x : x2;
			int ty = y2 == 0 ? y : y2;
			
			if (image != null) {
				// Draw image over frame
				g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
				
				// Set color to transparent black
				g.setColor(new Color(0, 0, 0, 100));							 
			} else {
				g.setColor(Color.black);
			}	

			// Draw black transparent color over all areas that isn't being selected
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

			RenderUtils.drawOutlinedString("X " + (x + rect.x) + " / Y " + (y + rect.y), x + 2, y - 2, Color.white, Color.black, g);	
					
			boolean selected = x2 - x != 0 && y2 - y != 0;
			
			if (selected) {
				g.setColor(Color.white);
				g.drawRect(x, y, tx - x, ty - y);
				RenderUtils.drawMovingRect(x, y, tx - x, ty - y, g, seed);
				RenderUtils.drawOutlinedString("Width " + (x2 - x) + " / Height " + (y2 - y), x + 2, y - 4 - g.getFontMetrics().getHeight(), Color.white, Color.black, g);	
			}
			
			// Reset all values to global
			
			x = RegionCapture.this.x;
			y = RegionCapture.this.y;
			x2 = RegionCapture.this.x2;
			y2 = RegionCapture.this.y2;
			
			tx = x2 == 0 ? x : x2;
			ty = y2 == 0 ? y : y2;
			
			// Cross over screen(s)
			g.setColor(Color.white);
			RenderUtils.drawMovingRect(tx, 0, 0, getHeight(), g, seed);
			RenderUtils.drawMovingRect(0, ty, getWidth(), 0, g, seed);

			// Cursor
			g.drawImage(cursor, tx - cursor.getWidth(null) / 2, ty - cursor.getHeight(null) / 2, null);

			BufferedImage preview = new BufferedImage(PREVIEW_SIZE, PREVIEW_SIZE, BufferedImage.TYPE_INT_RGB);
			int pos = PREVIEW_SIZE / PREVIEW_SCALE;
			pos /= 2;

			Graphics2D pg = preview.createGraphics();

			pg.drawImage(image, 0, 0, PREVIEW_SIZE, PREVIEW_SIZE, x2 - pos, y2 - pos, x2 + PREVIEW_SIZE - pos, y2 + PREVIEW_SIZE - pos, null);

			preview = RenderUtils.scale(preview, BufferedImage.TYPE_INT_RGB, PREVIEW_SIZE, PREVIEW_SIZE, PREVIEW_SCALE);

			pg = preview.createGraphics();

			int cheight = preview.getHeight() / 2 - PREVIEW_SCALE / 2 + 1;

			// Crosshair
			pg.setColor(new Color(0, 0, 255, 100));
			// north
			pg.fillRect(preview.getWidth() / 2 - PREVIEW_SCALE / 2 + 1, 0, PREVIEW_SCALE, preview.getHeight() / 2 - PREVIEW_SCALE / 2 + 1);

			// west
			pg.fillRect(0, cheight, preview.getWidth() / 2 - PREVIEW_SCALE / 2 + 1, PREVIEW_SCALE);

			// east
			pg.fillRect(preview.getWidth() / 2 + PREVIEW_SCALE - 2, cheight, preview.getWidth() / 2, PREVIEW_SCALE);

			// south
			pg.fillRect(preview.getWidth() / 2 - PREVIEW_SCALE / 2 + 1, preview.getHeight() / 2 + PREVIEW_SCALE - 2, PREVIEW_SCALE, preview.getHeight() / 2);

			RenderUtils.grid(preview, PREVIEW_SCALE);

			// dot in middle off crosshair
			pg.setColor(Color.black);
			pg.drawRect(preview.getWidth() / 2 - PREVIEW_SCALE / 2 + 1, preview.getHeight() / 2 - PREVIEW_SCALE / 2 + 1, PREVIEW_SCALE, PREVIEW_SCALE);

			preview = RenderUtils.createCircle(preview);

			// draw preview
			if (isInsideSelection(x2 + 1, y2 + 1)) {
				g.drawImage(preview, x2 - PREVIEW_SIZE, y2 - PREVIEW_SIZE, PREVIEW_SIZE, PREVIEW_SIZE, null);
			} else {
				g.drawImage(preview, x2, y2, PREVIEW_SIZE, PREVIEW_SIZE, null);
			}
		}
	}
	
	private boolean isInsideSelection(int x, int y) {
		int px = Math.min(RegionCapture.this.x, RegionCapture.this.x2);
		int py = Math.min(RegionCapture.this.y, RegionCapture.this.y2);
		int px2 = Math.max(RegionCapture.this.x, RegionCapture.this.x2);
		int py2 = Math.max(RegionCapture.this.y, RegionCapture.this.y2);
		
		return x >= px && y >= py && x <= px2 && y <= py2;
	}
	
	/**
	 * Called when enter is pressed or mouse released, if preferred
	 */
	public void submit() {
		setVisible(false);
		dispose();
		
		Capture capture = new Capture(Capture.Type.REGION, getSelection());
		capture.start();
	}
	
	public BufferedImage getSelection() {
		int x = Math.min(this.x, this.x2);
		int y = Math.min(this.y, this.y2);
		int x2 = Math.max(this.x, this.x2);
		int y2 = Math.max(this.y, this.y2);
		
		int width = x2 - x;
		int height = y2 - y;
		
		BufferedImage part = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = part.createGraphics();
		g.drawImage(image, 0, 0, width, height, x, y, x2 + 1, y2 + 1, null);
		
		return part;
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
			setVisible(false);
			dispose();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

}
