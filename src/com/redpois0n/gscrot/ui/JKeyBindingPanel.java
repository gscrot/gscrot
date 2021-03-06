package com.redpois0n.gscrot.ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.redpois0n.gscrot.keys.KeyBinding;
import com.redpois0n.gscrot.ui.components.JKeyBindingButton;
import com.redpois0n.gscrot.ui.settings.FrameKeyBindings;

@SuppressWarnings("serial")
public class JKeyBindingPanel extends JPanel {
	
	private JKeyBindingButton btn;
	private FrameKeyBindings parent;
	
	public JKeyBindingPanel(FrameKeyBindings parent, KeyBinding.Type type, KeyBinding binding) {
		this.parent = parent;
		
		JLabel label = new JLabel("  " + type.toString());
		label.setPreferredSize(new Dimension(5000, 50));
		label.setBorder(BorderFactory.createLineBorder(Color.gray));
		
		btn = new JKeyBindingButton(this, type, binding);
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
	
	public FrameKeyBindings getParentPanel() {
		return parent;
	}
	
	public JKeyBindingButton getButton() {
		return btn;
	}

}
