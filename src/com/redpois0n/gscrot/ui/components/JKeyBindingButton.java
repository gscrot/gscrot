package com.redpois0n.gscrot.ui.components;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;

import com.redpois0n.gscrot.keys.KeyBinding;
import com.redpois0n.gscrot.keys.KeyBinding.Type;
import com.redpois0n.gscrot.keys.KeyBindings;
import com.redpois0n.gscrot.ui.JKeyBindingPanel;

@SuppressWarnings("serial")
public class JKeyBindingButton extends JButton implements KeyListener {

	private JKeyBindingPanel parent;
	private Type type;
	
	private int[] keys = new int[KeyBindings.MAX_KEYS];
	private int index;

	public JKeyBindingButton(JKeyBindingPanel parent, Type type, KeyBinding binding) {
		this.parent = parent;
		this.type = type;
		
		setFocusable(true);

		addKeyListener(this);
		
		setPreferredSize(new Dimension(150, 15));

		if (binding != null) {
			for (int i = 0; i < keys.length; i++) {
				keys[i] = binding.getKeys().get(i);
			}
			
			index = 3;
			
			update();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (index >= keys.length) {
			clear();
			index = 0;
		}

		keys[index++] = e.getKeyCode();

		update();
		
		KeyBindings.KEYBINDINGS.put(type, getKeyBinding());
	}
	
	public void update() {
		setKeyText();
	}
	
	private void clear() {
		for (int i = 0; i < keys.length; i++) {
			keys[i] = 0;
		}
	}

	private void setKeyText() {
		String text = "";

		for (int i = 0; i < index; i++) {
			if (keys[i] != 0) {
				text += KeyEvent.getKeyText(keys[i]);

				if (i + 1 < index && keys[i + 1] != 0) {
					text += " + ";
				}
			}
		}

		setText(text);
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
	
	public int[] getKeys() {
		return this.keys;
	}

	public KeyBinding getKeyBinding() {
		return new KeyBinding(keys);
	}
}
