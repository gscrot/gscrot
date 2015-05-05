package com.redpois0n.guiscrot;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	public static final MainFrame INSTANCE = new MainFrame();
	
	private MenuPanel menuPanel;

	public MainFrame() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
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
			}
		}
	}

}
