package com.redpois0n.gscrot.ui.components;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

@SuppressWarnings("serial")
public class JDropDownButton extends JButton {
	
	private JPopupMenu menu = new JPopupMenu();

	public JDropDownButton(String text) {
		this(text, null);
	}
	
	public JDropDownButton(String text, ImageIcon icon) {
		super(text, icon);
		
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				menu.show((Component) e.getSource(), getWidth(), 0);
				menu.requestFocus();
			}
		});
	}
	
	public JPopupMenu getMenu() {
		return this.menu;
	}
	
	public void add(JMenuItem item) {
		this.menu.add(item);
	}
}
