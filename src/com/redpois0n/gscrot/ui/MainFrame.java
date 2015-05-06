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
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import com.redpois0n.gscrot.Capture;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	public static final MainFrame INSTANCE = new MainFrame();
	
	private MenuPanel menuPanel;
	private StatusTable table;
	private ImagePanel imagePanel;
	private JSplitPane splitPane;
	
	public MainFrame() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(0, 0, 750, 400);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout(0, 0));
		
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
			} else if (c instanceof JComponent) {
				menuPanel.addComponent((JComponent) c);
			}
		}
		
		JScrollPane scrollPaneTable = new JScrollPane();
		table = new StatusTable();
		table.addMouseListener(new CaptureClickListener());
		scrollPaneTable.setViewportView(table);
		
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
	
	private class CaptureClickListener extends MouseAdapter {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			int row = table.getSelectedRow();
			
			if (row != -1) {
				Capture capture = null;
				
				for (int i = 0; i < table.getColumnCount(); i++) {
					if (table.getValueAt(row, i) instanceof Capture) {
						capture = (Capture) table.getValueAt(row, i);
						break;
					}
				}
				
				imagePanel.setImage(capture.getImage());
			}
		}
	}

}
