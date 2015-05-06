package com.redpois0n.gscrot.actions;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.redpois0n.gscrot.Config;

public abstract class Action {
	
	private static final List<Action> ACTIONS = new ArrayList<Action>();
	
	static {		
		try {
			ACTIONS.add(new SaveAction(Config.get("save-dir")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static final List<Action> getAllActions() {
		return ACTIONS;
	}
	
	public static final List<Action> getActions(Event event) {
		List<Action> list = new ArrayList<Action>();
		
		for (Action action : ACTIONS) {
			if (action.getEvent() == event) {
				list.add(action);
			}
		}
		
		return list;
	}
	
	/**
	 * Add global action to be called after each screenshot taken
	 * @return
	 */
	public static final void addAction(Action a) {
		ACTIONS.add(a);
	}
	
	/**
	 * Remove global action
	 * @param a
	 */
	public static final void removeAction(Action a) {
		ACTIONS.remove(a);
	}
	
	private final Event event;
	
	public Action(Event event) {
		this.event = event;
	}
	
	public Event getEvent() {
		return this.event;
	}
	
	/**
	 * Called each time a new screenshot is taken
	 * @param image
	 */
	public abstract void process(BufferedImage image);

}
