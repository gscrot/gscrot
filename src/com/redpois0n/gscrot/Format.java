package com.redpois0n.gscrot;

public enum Format {
	
	PNG("png", "image/png"),
	GIF("gif", "image/gif"),
	JPG("jpg", "image/jpeg");
	
	private final String format;
	private final String mime;
	
	private Format(String format, String mime) {
		this.format = format;
		this.mime = mime;
	}
	
	@Override
	public String toString() {
		return this.format;
	}

	public String getMime() {
		return this.mime;
	}

	public static Format getFromString(String s) {
		for (Format format : Format.values()) {
			if (format.toString().equalsIgnoreCase(s)) {
				return format;
			}
		}
		
		return null;
	}
}
