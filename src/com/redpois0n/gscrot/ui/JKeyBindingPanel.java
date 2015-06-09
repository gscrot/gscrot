package com.redpois0n.gscrot.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.redpois0n.gscrot.ui.settings.JKeyBindingButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

@SuppressWarnings("serial")
public class JKeyBindingPanel extends JPanel {
	
	private JKeyBindingButton btn;
	
	public JKeyBindingPanel(String text) {
		
		JLabel label = new JLabel("  " + text);
		label.setPreferredSize(new Dimension(5000, 50));
		label.setBorder(BorderFactory.createLineBorder(Color.gray));
		
		btn = new JKeyBindingButton();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btn, GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(label, GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
				.addComponent(btn, GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
		);
		setLayout(groupLayout);
	}

}
