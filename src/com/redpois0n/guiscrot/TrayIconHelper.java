package com.redpois0n.guiscrot;

import iconlib.IconUtils;

import java.awt.Frame;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TrayIconHelper {

	public static void initialize() {		
		if (SystemTray.isSupported()) {
		    SystemTray tray = SystemTray.getSystemTray();

		    Image image = IconUtils.getIcon("icon").getImage();

		    PopupMenu popup = new PopupMenu();
		
		    TrayIcon icon = new TrayIcon(image, "guiscrot", popup);
		    icon.addMouseListener(new MouseAdapter() {
		    	public void mouseClicked(MouseEvent e)  {
		    		if (e.getClickCount() == 2 && !e.isConsumed()) {
						e.consume();
						MainFrame frame = MainFrame.INSTANCE;
						if (frame.getState() == Frame.ICONIFIED) {
							frame.setState(Frame.NORMAL);
						} else {
							frame.setState(Frame.ICONIFIED);
						}
					}
		    	}
		    });
		}
	}
	
}
