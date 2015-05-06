package com.redpois0n.gscrot.actions;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.redpois0n.gscrot.Config;

public abstract class AfterCaptureAction {
	
	private static final List<AfterCaptureAction> ACTIONS = new ArrayList<AfterCaptureAction>();
	
	static {		
		ACTIONS.add(new SaveAction(Config.get("save-dir")));
	}
	
	public static final List<AfterCaptureAction> getActions() {
		return ACTIONS;
	}
	
	/**
	 * Add global action to be called after each screenshot taken
	 * @return
	 */
	public static final void addAction(AfterCaptureAction a) {
		ACTIONS.add(a);
	}
	
	/**
	 * Remove global action
	 * @param a
	 */
	public static final void removeAction(AfterCaptureAction a) {
		ACTIONS.remove(a);
	}
	
	/**
	 * Called each time a new screenshot is taken
	 * @param image
	 */
	public abstract void process(BufferedImage image);

}
