package com.redpois0n.guiscrot;

import iconlib.IconUtils;

import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;

public class TrayIconHelper {

	public static void initialize() {
		TrayIcon trayIcon = null;
		
		if (SystemTray.isSupported()) {
		    SystemTray tray = SystemTray.getSystemTray();

		    Image image = IconUtils.getIcon("icon").getImage();

		    PopupMenu popup = new PopupMenu();
		
		    TrayIcon icon = new TrayIcon(image, "guiscrot", popup);
		}
	}
	
}
