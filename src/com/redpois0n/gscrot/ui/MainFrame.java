package com.redpois0n.gscrot.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
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
import com.redpois0n.gscrot.ui.components.URLMenuItem;
import com.redpois0n.gscrot.util.Icons;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements PopupMenuListener {

	public static final MainFrame INSTANCE = new MainFrame();
	
	private MenuPanel menuPanel;
	private StatusTable table;
	private ImagePanel imagePanel;
	private JSplitPane splitPane;
	private JPopupMenu popupMenu;
	
	public MainFrame() {
		setIconImages(Icons.getIcons());
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(0, 0, 750, 400);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JScrollPane sp = new JScrollPane();
		menuPanel = new MenuPanel();
		sp.setViewportView(menuPanel);
		
		add(sp, BorderLayout.WEST);
		
		JPopupMenu global = GlobalPopupMenu.getPopupMenu();

		for (Component c : global.getComponents()) {
			if (c instanceof JMenu) {
				JMenu m = (JMenu) c;

				final JButton btn = new JButton(m.getText(), m.getIcon());
				menuPanel.addButton(btn);

				final JPopupMenu menu2 = new JPopupMenu();

				btn.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						menu2.show((Component) e.getSource(), btn.getWidth(), 0);
						menu2.requestFocus();
					}
				});

				for (Component c1 : m.getMenuComponents()) {
					menu2.add(c1);
				}
			} else if (c instanceof JMenuItem) {
				JMenuItem mi = (JMenuItem) c;
				JButton btn = new JButton(mi.getText(), mi.getIcon());
				menuPanel.addButton(btn);

				for (ActionListener l : mi.getActionListeners()) {
					btn.addActionListener(l);
				}
			} else if (c instanceof JSeparator) {
				menuPanel.addSeparator();
			} else if (c instanceof JComponent) {
				menuPanel.addComponent((JComponent) c);
			}
		}
		
		JScrollPane scrollPaneTable = new JScrollPane();
		table = new StatusTable();
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
		
		
	}

	public void add(Capture p) {
		table.add(p);
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
	
	public Capture getSelectedCapture() {
		int row = table.getSelectedRow();

		Capture capture = null;
		
		for (int i = 0; i < table.getColumnCount(); i++) {
			if (table.getValueAt(row, i) instanceof Capture) {
				capture = (Capture) table.getValueAt(row, i);
				break;
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

	@Override
	public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
		Capture capture = getSelectedCapture();
		
		if (capture != null && capture.getResponse() != null) {			
			UploadResponse r = capture.getResponse();
			
			popupMenu.removeAll();
			
			if (r.getUrl() != null) {
				popupMenu.add(new URLMenuItem("Open link", r.getUrl()));
			}
			
			if (r.getUrl() != null) {
				popupMenu.add(new URLMenuItem("Open removal link", r.getRemoveUrl()));
			}
		}
	}
}
