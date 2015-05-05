package com.redpois0n.gscrot.ui;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel {

	public MenuPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	public void addButton(final JButton btn) {
		JToolBar bar = new JToolBar();

		bar.setFloatable(false);
		bar.add(btn);

		bar.setAlignmentX(Component.LEFT_ALIGNMENT);

		add(bar);
	}
}
