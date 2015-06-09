package com.redpois0n.gscrot.ui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.redpois0n.gscrot.ui.settings.JKeyBindingButton;

@SuppressWarnings("serial")
public class JKeyBindingPanel extends JPanel {
	
	private JKeyBindingButton btn;
	
	public JKeyBindingPanel(String text) {
		setLayout(new BorderLayout(0, 0));
		
		JLabel label = new JLabel(text);
		add(label, BorderLayout.WEST);
		
		btn = new JKeyBindingButton();
		add(btn, BorderLayout.EAST);
	}

}
