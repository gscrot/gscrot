package com.redpois0n.gscrot;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class ClipboardHelper {
	
	/**
	 * @return returns the system clipboard
	 */
	public static Clipboard getClipboard() {
		return Toolkit.getDefaultToolkit().getSystemClipboard();
	}
	
	/**
	 * Sets string in clipboard
	 * @param s
	 */
	public static void setString(String s) {
		StringSelection selection = new StringSelection(s);
	    getClipboard().setContents(selection, selection);
	}

}
