package com.redpois0n.gscrot.ui.components;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;

import com.redpois0n.gscrot.keys.KeyBinding;
import com.redpois0n.gscrot.keys.KeyBinding.Type;
import com.redpois0n.gscrot.keys.KeyBindings;

@SuppressWarnings("serial")
public class JKeyBindingButton extends JButton implements KeyListener {

	private Type type;
	
	private int[] keys = new int[KeyBindings.MAX_KEYS];
	private int index;

	public JKeyBindingButton(Type type, KeyBinding binding) {
		this.type = type;
		
		setFocusable(true);

		addKeyListener(this);
		
		setPreferredSize(new Dimension(150, 15));
		
		if (binding != null) {
			for (int i = 0; i < keys.length; i++) {
				keys[i] = binding.getKeys().get(i);
			}
			
			setKeyText();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (index >= keys.length) {
			clear();
			index = 0;
		}

		keys[index++] = e.getKeyCode();

		setKeyText();
		
		KeyBindings.KEYBINDINGS.put(type, getKeyBinding());
	}
	
	private void clear() {
		for (int i = 0; i < keys.length; i++) {
			keys[i] = 0;
		}
	}

	private void setKeyText() {
		String text = "";

		for (int i = 0; i < index; i++) {
			text += KeyEvent.getKeyText(keys[i]);

			if (i + 1 < index) {
				text += " + ";
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
