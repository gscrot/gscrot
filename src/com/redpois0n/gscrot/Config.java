package com.redpois0n.gscrot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class Config {
	
	public static final Map<String, String> CONFIG = new HashMap<String, String>();
	public static final File CONFIG_FILE = new File(".gscrot");
		
	public static String get(String s) {
		return CONFIG.get(s);
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
		
		if (CONFIG.size() == 0) {
			loadDefaults();
		}
		
		reader.close();
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
	}
	
	public static void loadDefaults() {
		CONFIG.put("save-dir", System.getProperty("java.io.tmpdir"));
	}

}
