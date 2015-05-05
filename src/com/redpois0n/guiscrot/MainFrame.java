package com.redpois0n.guiscrot;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	
	public static final MainFrame INSTANCE = new MainFrame();

	public MainFrame() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.WEST);
		
		JPopupMenu global = GlobalPopupMenu.getPopupMenu();
		
		for (Component c : global.getComponents()) {
			if (c instanceof JMenuItem) {
				JMenuItem mi = (JMenuItem) c;
				JButton btn = new JButton(mi.getText(), mi.getIcon());
				panel.add(btn);
				
				for (ActionListener l : mi.getActionListeners()) {
					btn.addActionListener(l);
				}
			} else if (c instanceof JMenu) {
				JMenu m = (JMenu) c;
				
				final JButton btn = new JButton(m.getText(), m.getIcon());
				panel.add(btn);
				
				final JPopupMenu menu2 = new JPopupMenu();
				
				btn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						menu2.show(null, btn.getX() + btn.getWidth(), 0);
					}
				});
				
				for (Component c1 : m.getComponents()) {
					menu2.add(c1);
				}
			}
		}
		
	}

}
