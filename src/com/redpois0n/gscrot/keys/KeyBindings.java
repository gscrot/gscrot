package com.redpois0n.gscrot.keys;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import com.redpois0n.gscrot.ui.settings.JKeyBindingButton;

public class KeyBindings {
	
	public static Map<KeyBinding.Type, Runnable> ACTIONS = new HashMap<KeyBinding.Type, Runnable>();
	
	public static Map<KeyBinding.Type, KeyBinding> KEYBINDINGS = new HashMap<KeyBinding.Type, KeyBinding>();
	public static final File KEYBINDINGS_FILE = new File(".keys");
	
	public static void load() throws Exception {
		DataInputStream dis = new DataInputStream(new FileInputStream(KEYBINDINGS_FILE));
		
		int len = dis.readInt();
		
		for (int i = 0; i < len; i++) {
			KeyBinding.Type type = KeyBinding.Type.values()[i];
			
			Integer[] keys = new Integer[JKeyBindingButton.MAX_KEYS];
			
			for (int k = 0; i < JKeyBindingButton.MAX_KEYS; i++) {
				keys[k] = dis.readInt();
			}
			
			KeyBinding binding = new KeyBinding(keys);
			KEYBINDINGS.put(type, binding);
		}
		
		dis.close();
	}
	
	public static void save() throws Exception {
		DataOutputStream dos = new DataOutputStream(new FileOutputStream(KEYBINDINGS_FILE));
		
		dos.writeInt(KEYBINDINGS.size());
		
		for (KeyBinding.Type mapkey : KEYBINDINGS.keySet()) {
			dos.writeInt(mapkey.ordinal());
			
			KeyBinding binding = KEYBINDINGS.get(mapkey);
			
			for (int key : binding.getKeys()) {
				dos.writeInt(key);
			}
		}
		
		dos.close();
	}

}
