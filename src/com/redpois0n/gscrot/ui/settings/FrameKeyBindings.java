package com.redpois0n.gscrot.ui.settings;

import iconlib.IconUtils;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import com.redpois0n.gscrot.keys.KeyBinding;
import com.redpois0n.gscrot.keys.KeyBindings;
import com.redpois0n.gscrot.ui.JKeyBindingPanel;

@SuppressWarnings("serial")
public class FrameKeyBindings extends JFrame {
	
	private List<JKeyBindingPanel> panels = new ArrayList<JKeyBindingPanel>();
	private JPanel bindingsPanel;

	public FrameKeyBindings() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Key bindings");
		setIconImage(IconUtils.getIcon("keys").getImage());
		setBounds(100, 100, 450, 300);
		
		bindingsPanel = new JPanel();
		bindingsPanel.setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setViewportView(bindingsPanel);
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
					.addGap(39))
		);
		getContentPane().setLayout(groupLayout);
				
		for (int i = 0; i < KeyBinding.Type.values().length; i++) {
			KeyBinding.Type type = KeyBinding.Type.values()[i];
						
			JKeyBindingPanel panel = new JKeyBindingPanel(this, type.name(), type, KeyBindings.KEYBINDINGS.get(type));
			panels.add(panel);
			
			panel.setBounds(10, 10 + i * 30, getWidth() - 50, 25);
			bindingsPanel.add(panel);
		}
		
		bindingsPanel.setPreferredSize(new Dimension(0, 35 + KeyBinding.Type.values().length * 30));
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
