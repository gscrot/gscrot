package com.redpois0n.gscrot.ui.settings;

import iconlib.IconUtils;

import javax.swing.JFrame;

import com.redpois0n.gscrot.keys.KeyBinding;
import com.redpois0n.gscrot.keys.KeyBindings;
import com.redpois0n.gscrot.ui.JKeyBindingPanel;

@SuppressWarnings("serial")
public class FrameKeyBindings extends JFrame {

	public FrameKeyBindings() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Key bindings");
		setIconImage(IconUtils.getIcon("keys").getImage());
		setBounds(100, 100, 450, 300);
		setLayout(null);
		
		for (int i = 0; i < KeyBinding.Type.values().length; i++) {
			KeyBinding.Type type = KeyBinding.Type.values()[i];
			
			JKeyBindingPanel panel = new JKeyBindingPanel(type.name(), type, KeyBindings.KEYBINDINGS.get(type));
			panel.setBounds(10, 10 + i * 30, getWidth() - 30, 25);
			
			add(panel);
		}
	}
}
