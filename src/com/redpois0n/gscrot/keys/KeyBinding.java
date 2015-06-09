package com.redpois0n.gscrot.keys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class KeyBinding implements Serializable {
	
	public static enum Type {
		REGION,
		FULLSCREEN,
		REGION_COLOR,
		PICK_COLOR
	}
	
	private static final long serialVersionUID = 1607444383964747069L;
	
	private List<Integer> keys = new ArrayList<Integer>();
		
	public KeyBinding(Integer... keys) {
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
