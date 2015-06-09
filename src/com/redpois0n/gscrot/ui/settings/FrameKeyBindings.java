package com.redpois0n.gscrot.ui.settings;

import javax.swing.JFrame;

import com.redpois0n.gscrot.ui.JKeyBindingPanel;

@SuppressWarnings("serial")
public class FrameKeyBindings extends JFrame {

	public FrameKeyBindings() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JKeyBindingPanel panel = new JKeyBindingPanel("Region");
		panel.setBounds(10, 10, 200, 50);
		getContentPane().add(panel);
		
		JKeyBindingPanel panel_1 = new JKeyBindingPanel("Fullscreen");
		panel_1.setBounds(10, 66, 200, 50);
		getContentPane().add(panel_1);
	}
}
