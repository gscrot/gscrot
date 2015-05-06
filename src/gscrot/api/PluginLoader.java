package gscrot.api;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pluginlib.Plugin;

public class PluginLoader {
	
	public static List<Plugin> plugins = new ArrayList<Plugin>();
	
	public static void loadPlugins() throws Exception {
		for (File file : new File("plugins/").listFiles()) {
			Plugin plugin = new Plugin(file);
			
			plugin.load();
			
			plugins.add(plugin);
		}
	}

}
