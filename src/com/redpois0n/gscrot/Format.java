package com.redpois0n.gscrot;

public enum Format {
	
	PNG("png"),
	GIF("gif"),
	JPG("jpg");
	
	private final String format;
	
	private Format(String format) {
		this.format = format;
	}
	
	@Override
	public String toString() {
		return this.format;
	}

}
