package com.redpois0n.gscrot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class Config {

	public static final Properties CONFIG = new Properties();
	public static final File CONFIG_FILE = new File(".gscrot");

	public static final String KEY_COPY_URL_TO_CLIPBOARD = "copy-url-to-clipboard";
	public static final String KEY_COPY_IMAGE_TO_CLIPBOARD = "copy-image-to-clipboard";
	public static final String KEY_PREFERRED_FORMAT = "preferred-format";
	public static final String KEY_DEFAULT_FILENAME = "image";

	/**
	 * Gets value from configuration
	 * 
	 * @param k
	 * @param def
	 *            default value if nothing is found in config
	 * @return
	 */
	public static String get(String k, String def) {
		return CONFIG.getProperty(k, def);
	}

	/**
	 * Gets value as boolean from configuration
	 * 
	 * @param k
	 * @param def
	 *            default value
	 * @return
	 */
	public static boolean get(String k, boolean def) {
		return get(k, def + "").equalsIgnoreCase("true");
	}

	/**
	 * Sets value in configuration
	 * 
	 * @param k
	 * @param v
	 */
	public static void put(String k, String v) {
		CONFIG.put(k, v);
	}

	public static void load() throws Exception {
		FileInputStream fis = new FileInputStream(CONFIG_FILE);
		CONFIG.load(fis);
		fis.close();
	}

	public static void save() throws Exception {
		FileOutputStream fos = new FileOutputStream(CONFIG_FILE);
		CONFIG.store(fos, "gscrot generated config");
		fos.close();
	}

}
