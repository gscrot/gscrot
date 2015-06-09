package com.redpois0n.gscrot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.redpois0n.gscrot.keys.KeyBinding;

public class Config {
	
	public static final Map<String, String> CONFIG = new HashMap<String, String>();
	public static final File CONFIG_FILE = new File(".gscrot");
	
	public static List<KeyBinding> KEYBINDINGS = new ArrayList<KeyBinding>();
	public static final File KEYBINDINGS_FILE = new File(".keys");
	
	public static final String KEY_COPY_URL_TO_CLIPBOARD = "copy-url-to-clipboard";
	public static final String KEY_COPY_IMAGE_TO_CLIPBOARD = "copy-image-to-clipboard";
		
	/**
	 * Gets value from configuration
	 * @param k
	 * @param def default value if nothing is found in config
	 * @return
	 */
	public static String get(String k, String def) {
		if (CONFIG.containsKey(k)) {
			return CONFIG.get(k);
		} else {
			return def;
		}
	}
	
	/**
	 * Sets value in configuration
	 * @param k
	 * @param v
	 */
	public static void put(String k, String v) {
		CONFIG.put(k, v);
	}
	
	public static void load() throws Exception {
		CONFIG.clear();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(CONFIG_FILE)));
		
		String line;
		
		while ((line = reader.readLine()) != null) {
			if (!line.startsWith("#") && line.contains("=")) {
				String k = line.substring(0, line.indexOf("="));
				String v = line.substring(line.indexOf("=") + 1, line.length());
								
				CONFIG.put(k, v);
			}
		}
		
		reader.close();
		
		KEYBINDINGS.clear();
		KEYBINDINGS = (List<KeyBinding>) new ObjectInputStream(new FileInputStream(KEYBINDINGS_FILE)).readObject(); 
	}
	
	public static void save() throws Exception {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(CONFIG_FILE)));
		
		writer.write("# gscrot generated config");
		writer.newLine();
		
		for (String k : CONFIG.keySet()) {
			writer.write(k + "=" + CONFIG.get(k));
			writer.newLine();
		}
		
		writer.close();
		
		new ObjectOutputStream(new FileOutputStream(KEYBINDINGS_FILE)).writeObject(KEYBINDINGS);
	}

}
