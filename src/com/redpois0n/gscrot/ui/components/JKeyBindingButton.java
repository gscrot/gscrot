package com.redpois0n.gscrot.ui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import com.redpois0n.gscrot.keys.KeyBinding;
import com.redpois0n.gscrot.keys.KeyBinding.Type;
import com.redpois0n.gscrot.keys.KeyBindings;
import com.redpois0n.gscrot.ui.JKeyBindingPanel;

@SuppressWarnings("serial")
public class JKeyBindingButton extends JButton implements KeyListener, MouseListener {

	private JKeyBindingPanel parent;
	private Type type;
	
	private int[] keys = new int[KeyBindings.MAX_KEYS];
	private int index;

	public JKeyBindingButton(JKeyBindingPanel parent, Type type, KeyBinding binding) {
		this.parent = parent;
		this.type = type;
		
		clear();
		
		setFocusable(true);

		addKeyListener(this);
		addMouseListener(this);
		
		setPreferredSize(new Dimension(150, 15));

		if (binding != null) {
			boolean zero = true;
			
			for (int i = 0; i < keys.length; i++) {
				keys[i] = binding.getKeys().get(i);
				
				if (keys[i] != 0) {
					zero = false;
				}
			}
			
			index = zero ? 0 : KeyBindings.MAX_KEYS;
		}
		
		update();

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (index >= keys.length) {
			clear();
			index = 0;
		}

		keys[index++] = e.getKeyCode();

		update();
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		clear();
		update();
		setText("Press to enter keys...");
	}
	
	@Override
	public void setBackground(Color c) {
		super.setBackground(c);
		
		if (parent != null) {
			parent.setBackground(c);
		}
	}
	
	public void update() {
		if (index > 0 && parent.getParentPanel().isConflicting(parent, keys)) {
			setBackground(Color.red);
		} else if (index == 0) {
			setBackground(new Color(250, 250, 210));
		} else {
			setBackground(new Color(152, 251, 152));
		}

		setKeyText();
		KeyBindings.KEYBINDINGS.put(type, getKeyBinding());
	}
	
	private void clear() {
		index = 0;
		for (int i = 0; i < keys.length; i++) {
			keys[i] = 0;
		}
	}

	private void setKeyText() {
		String text = "";
		
		if (index == 0) {
			text = "Press to enter keys...";
		}

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

	public int[] getKeys() {
		return this.keys;
	}

	public KeyBinding getKeyBinding() {
		return new KeyBinding(keys);
	}

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }

	@Override
	public void mousePressed(MouseEvent e) { }

	@Override
	public void mouseReleased(MouseEvent e) { }

	@Override
	public void keyReleased(KeyEvent e) { }

	@Override
	public void keyTyped(KeyEvent e) { }
	
}
