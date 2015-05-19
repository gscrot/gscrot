package com.redpois0n.gscrot.utils;

import java.awt.Color;
import java.awt.TrayIcon.MessageType;

import com.redpois0n.gscrot.ClipboardHelper;
import com.redpois0n.gscrot.TrayIconHelper;

public class Utils {
	
	public static void setColorInClipboard(Color color) {
		String s = color.getRed() + ", " + color.getGreen() + ", " + color.getBlue();
		ClipboardHelper.setString(s);
				
		TrayIconHelper.showMessage(s + " copied to clipboard", MessageType.INFO);
	}

}
