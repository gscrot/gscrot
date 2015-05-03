package com.redpois0n.guiscrot;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class RegionFrame extends JFrame {

	public RegionFrame() {
		setAlwaysOnTop(true);
		setBounds(0, 0, 450, 450);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				dispose();
			}
		});
		setUndecorated(true);
		setLocationRelativeTo(null);
		setOpacity(0.5F);
	}

}