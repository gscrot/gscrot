package com.redpois0n.gscrot.util;

import java.util.Calendar;

public enum NameFormatter {
	
	YEAR("y", "Current year"),
	MONTH("m", "Current month"),
	DAY("d", "Current day"),
	HOUR("h", "Current hour"),
	MINUTE("min", "Current minute"),
	SECOND("s", "Current second");
	
	private final String code;
	private final String description;
	
	private NameFormatter(String code, String description) {
		this.code = code;
		this.description = description;
	}
	
	public String getCode() {
		return "%" + this.code;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public static String format(String input) {
		Calendar cal = Calendar.getInstance();
		
		input = input.replace(YEAR.getCode(), cal.get(Calendar.YEAR) + "");
		input = input.replace(MINUTE.getCode(), cal.get(Calendar.MINUTE) + "");
		input = input.replace(MONTH.getCode(), Integer.toString(cal.get(Calendar.MONTH) + 1));
		input = input.replace(DAY.getCode(), cal.get(Calendar.DATE) + "");
		input = input.replace(HOUR.getCode(), cal.get(Calendar.HOUR) + "");
		input = input.replace(SECOND.getCode(), cal.get(Calendar.SECOND) + "");

		return input;
	}
	
	
}
