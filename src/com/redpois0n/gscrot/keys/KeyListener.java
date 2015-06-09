package com.redpois0n.gscrot.keys;

import java.util.ArrayList;
import java.util.List;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyListener implements NativeKeyListener {
	
	private static final List<Integer> pressed = new ArrayList<Integer>();
	
	public static boolean isPressed(int keycode) {
		return pressed.contains(keycode);
	}
	
	private static void removePressed(int keycode) {
		for (int i = 0; i < pressed.size(); i++) {
			if (pressed.get(i).equals(keycode)) {
				pressed.remove(i);
			}
		}
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		pressed.add(e.getRawCode());
		
		for (KeyBinding.Type k : KeyBindings.KEYBINDINGS.keySet()) {
			KeyBinding kb = KeyBindings.KEYBINDINGS.get(k);
			
			boolean trigger = false;
			for (int i : kb.getKeys()) {
				if (i != 0) {
					if (pressed.contains(i)) {
						trigger = true;
					} else {
						trigger = false;
						break;
					}
				}
			}
			
			if (trigger) {
				k.trigger();
			}
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		removePressed(e.getRawCode());
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {

	}

}
