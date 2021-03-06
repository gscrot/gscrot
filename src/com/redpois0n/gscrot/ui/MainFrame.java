package com.redpois0n.gscrot.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import com.redpois0n.gscrot.Capture;
import com.redpois0n.gscrot.UploadResponse;
import com.redpois0n.gscrot.Version;
import com.redpois0n.gscrot.ui.components.URLMenuItem;
import com.redpois0n.gscrot.utils.Icons;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements PopupMenuListener {

	/**
	 * Global frame instance
	 */
	public static final MainFrame INSTANCE = new MainFrame();
	
	private MenuPanel menuPanel;
	private CaptureTable table;
	private ImagePanel imagePanel;
	private JSplitPane splitPane;
	private JPopupMenu popupMenu;
	
	public MainFrame() {
		setIconImages(Icons.getIcons());
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(0, 0, 750, 400);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(0, 0));
		setTitle(null);
		
		JScrollPane sp = new JScrollPane();
		menuPanel = new MenuPanel();
		sp.setViewportView(menuPanel);
		
		add(sp, BorderLayout.WEST);		
		
		JScrollPane scrollPaneTable = new JScrollPane();
		table = new CaptureTable();
		table.addMouseListener(new CaptureClickListener());
		scrollPaneTable.setViewportView(table);
		
		popupMenu = new JPopupMenu();
		popupMenu.addPopupMenuListener(this);
		addPopup(table, popupMenu);
		
		JScrollPane scrollPaneImage = new JScrollPane();
		imagePanel = new ImagePanel();
		scrollPaneImage.setViewportView(imagePanel);
		
		splitPane = new JSplitPane();
		splitPane.setLeftComponent(scrollPaneTable);
		splitPane.setRightComponent(scrollPaneImage);
		add(splitPane, BorderLayout.CENTER);
		
		for (Component c : GlobalPopupMenu.getMenu()) {
			if (c instanceof JSeparator) {
				menuPanel.addSeparator();
			} else {
				menuPanel.add(c);
			}
		}
	}
	
	/**
	 * Add capture to global table
	 * @param capture
	 */
	public void add(Capture capture) {
		table.add(capture);
	}
	
	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				splitPane.setDividerLocation(splitPane.getWidth() / 2);
			}
		});
	}
	
	/**
	 * Sets the frame title beginning with gscrot version - message
	 * @param s the title to append
	 */
	@Override
	public void setTitle(String s) {
		String title = "gscrot " + Version.getVersion();
		
		if (s != null && s.length() > 0) {
			title += " - " + s;
		}
		
		super.setTitle(title);
	}
	
	public Capture getSelectedCapture() {
		int row = table.getSelectedRow();

		Capture capture = null;
		
		if (row != -1) {
			for (int i = 0; i < table.getColumnCount(); i++) {
				if (table.getValueAt(row, i) instanceof Capture) {
					capture = (Capture) table.getValueAt(row, i);
					break;
				}
			}
		}
		
		return capture;
	}
	
	private class CaptureClickListener extends MouseAdapter {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			Capture capture = getSelectedCapture();
			
			if (capture != null) {				
				imagePanel.setImage(capture.getImage());
			}
		}
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	@Override
	public void popupMenuCanceled(PopupMenuEvent e) {
		
	}

	@Override
	public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
		
	}

	/**
	 * Adds menu items to popup menu depending on the selected capture
	 */
	@Override
	public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
		Capture capture = getSelectedCapture();
		
		if (capture != null) {			
			final UploadResponse r = capture.getResponse();
			
			popupMenu.removeAll();
			
			if (r != null) {
				if (r.getUrl() != null) {
					popupMenu.add(new URLMenuItem("Open link", r.getUrl()));
				}
				
				if (r.getUrl() != null) {
					popupMenu.add(new URLMenuItem("Open removal link", r.getRemoveUrl()));
				}
			}
			
			JMenuItem mntmResponse = new JMenuItem("View Response");
			mntmResponse.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					System.out.println(r.getRaw());
				}
			});
			popupMenu.add(mntmResponse);
		}
	}
}
