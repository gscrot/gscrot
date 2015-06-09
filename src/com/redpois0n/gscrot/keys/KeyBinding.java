package com.redpois0n.gscrot.keys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class KeyBinding implements Serializable {
	
	private static final long serialVersionUID = 1607444383964747069L;
	
	private List<Integer> keys = new ArrayList<Integer>();
	
	private KeyBinding() {
		
	}
	
	public abstract void trigger();
	
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
