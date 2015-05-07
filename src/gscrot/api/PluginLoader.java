package gscrot.api;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pluginlib.Plugin;

import com.redpois0n.gscrot.Logger;

public class PluginLoader {
	
	public static List<Plugin<gscrot.api.Plugin>> plugins = new ArrayList<Plugin<gscrot.api.Plugin>>();
	
	public static void loadPlugins() throws Exception {
		File dir = new File("plugins/");
		File files[] = dir.listFiles();
		
		if (files != null) {
			for (File file : files) {
				load(file);
			}
		}
	}
	
	public static void load(File file) throws Exception {
		Logger.log("Loading plugin " + file.getName());
		
		Plugin<gscrot.api.Plugin> plugin = new Plugin<gscrot.api.Plugin>(file);
		
		plugin.load();
		
		plugins.add(plugin);
	}

}
