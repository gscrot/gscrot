package com.redpois0n.gscrot.keys;

import java.util.ArrayList;
import java.util.List;

public class KeyBinding {
	
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

}
