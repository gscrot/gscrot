package guiscrot.api;

import java.io.File;

import pluginlib.Plugin;

public class PluginLoader {
	
	public static void loadPlugins() throws Exception {
		for (File file : new File("plugins/").listFiles()) {
			Plugin plugin = new Plugin(file);
			
			plugin.load();
		}
	}

}
