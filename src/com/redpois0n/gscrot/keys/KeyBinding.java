package com.redpois0n.gscrot.keys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.redpois0n.gscrot.Main;
import com.redpois0n.gscrot.ScreenshotHelper;
import com.redpois0n.gscrot.Tools;

public class KeyBinding implements Serializable {
	
	public static enum Type {
		REGION("Capture Region", new Runnable() {
			@Override
			public void run() {
				Main.createBackground();
			}
		}),
		
		FULLSCREEN("Capture all screens", new Runnable() {
			@Override
			public void run() {
    			ScreenshotHelper.captureAll();
			}
		}),
		
		REGION_COLOR("Grab color from screen", new Runnable() {
			@Override
			public void run() {
				Tools.callScreenColorPicker();
			}
		}),
		
		PICK_COLOR("Open color picker", new Runnable() {
			@Override
			public void run() {
				Tools.callColorPicker();
			}
		});
		
		private String text;
		private Runnable run;
		
		private Type(String text, Runnable run) {
			this.text = text;
			this.run = run;
		}
		
		/**
		 * Returns {@link #text}
		 */
		@Override
		public String toString() {
			return text;
		}
		
		public void trigger() {
			run.run();
		}
	}
	
	private static final long serialVersionUID = 1607444383964747069L;
	
	private List<Integer> keys = new ArrayList<Integer>();
		
	public KeyBinding(int... keys) {
		for (Integer i : keys) {
			this.keys.add(i);
		}
	}

	@Override
	public String toString() {
		return super.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		return this == o;
	}
	
	public List<Integer> getKeys() {
		return this.keys;
	}

}
