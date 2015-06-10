package com.redpois0n.gscrot.ui.settings;

import iconlib.IconUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

import com.redpois0n.gscrot.keys.KeyBinding;
import com.redpois0n.gscrot.keys.KeyBindings;
import com.redpois0n.gscrot.ui.JKeyBindingPanel;

@SuppressWarnings("serial")
public class FrameKeyBindings extends JFrame {
	
	private List<JKeyBindingPanel> panels = new ArrayList<JKeyBindingPanel>();

	public FrameKeyBindings() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Key bindings");
		setIconImage(IconUtils.getIcon("keys").getImage());
		setBounds(100, 100, 450, 300);
		setLayout(null);
		
		for (int i = 0; i < KeyBinding.Type.values().length; i++) {
			KeyBinding.Type type = KeyBinding.Type.values()[i];
						
			JKeyBindingPanel panel = new JKeyBindingPanel(this, type.name(), type, KeyBindings.KEYBINDINGS.get(type));
			panels.add(panel);
			
			panel.setBounds(10, 10 + i * 30, getWidth() - 30, 25);
			
			add(panel);
		}
	}
	
	/**
	 * Returns if any of the key bindings is the same (conflicting)
	 * @param keys
	 * @return true if any conflict exists, false if not
	 */
	public boolean isConflicting(JKeyBindingPanel p, int[] keys) {
		for (JKeyBindingPanel panel : panels) {
			if (panel != p) {
				int match = 0;

				for (int i = 0; i < keys.length; i++) {				
					if (panel.getButton().getKeys()[i] == keys[i]) {
						match++;
					}
				}
							
				if (match == keys.length) {
					return true;
				}
			}
		}
		
		return false;
	}
}
