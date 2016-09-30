package com.redpois0n.gscrot;

import java.util.Calendar;

public class Logger {

	public static void log(Object obj) {
		String time = getTimeString();

		String s = "[" + time + "] [LOG]: " + obj.toString();

		System.out.println(s);
	}

	public static String getTimeString() {
		int HOUR_OF_DAY = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		String hours = (HOUR_OF_DAY + "").length() == 1 ? "0" + HOUR_OF_DAY : Integer.toString(HOUR_OF_DAY);

		int MINUTE = Calendar.getInstance().get(Calendar.MINUTE);
		String minutes = (MINUTE + "").length() == 1 ? "0" + MINUTE : Integer.toString(MINUTE);

		int SECOND = Calendar.getInstance().get(Calendar.SECOND);
		String seconds = (SECOND + "").length() == 1 ? "0" + SECOND : Integer.toString(SECOND);

		String time = hours + ":" + minutes + ":" + seconds;

		return time;
	}

	/**
	 * getTimeString() but replaces : with - which is invalid in path names (at
	 * least on Windows)
	 * 
	 * @return
	 */
	public static String getTimeFileString() {
		return getTimeString().replace(":", "-");
	}
}
