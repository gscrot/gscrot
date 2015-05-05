package com.redpois0n.gscrot.actions;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.redpois0n.gscrot.Config;

public abstract class AfterCaptureAction {
	
	public static List<AfterCaptureAction> actions = new ArrayList<AfterCaptureAction>();
	
	static {
		// Debug only
		
		actions.add(new SaveAction(Config.get("save-dir")));
	}
	
	/**
	 * Called each time a new screenshot is taken
	 * @param image
	 */
	public abstract void process(BufferedImage image);

}
