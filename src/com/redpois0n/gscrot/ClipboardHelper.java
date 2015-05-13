package com.redpois0n.gscrot;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class ClipboardHelper {
	
	public static Clipboard getClipboard() {
		return Toolkit.getDefaultToolkit().getSystemClipboard();
	}
	
	public static void set(String s) {
		StringSelection selection = new StringSelection(s);
	    getClipboard().setContents(selection, selection);
	}

}
