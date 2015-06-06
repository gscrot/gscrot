package com.redpois0n.gscrot.ui;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JToolBar;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel {

	private JToolBar bar;
	
	public MenuPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		bar = new JToolBar(null, JToolBar.VERTICAL);
		bar.setFloatable(false);
		bar.setAlignmentX(Component.LEFT_ALIGNMENT);
		super.add(bar);
	}

	/**
	 * Adds separator to {@link #bar}
	 */
	public void addSeparator() {
		bar.addSeparator();
	}
	
	@Override
	public Component add(Component c) {
		bar.add(c);
		
		return c;
	}
	
	/**
	 * Removes all components in {@link #bar}
	 */
	@Override
	public void removeAll() {
		bar.removeAll();
	}
}
