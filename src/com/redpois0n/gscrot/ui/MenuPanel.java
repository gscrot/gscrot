package com.redpois0n.gscrot.ui;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
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
		add(bar);
	}

	public void addButton(JButton btn) {
		bar.add(btn);
	}

	public void addSeparator() {
		bar.addSeparator();
	}
	
	public void addComponent(JComponent j) {
		bar.add(j);
	}
}
