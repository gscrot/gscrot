package com.redpois0n.guiscrot;

import iconlib.IconUtils;

import java.awt.Frame;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

public class TrayIconHelper {

	public static void initialize() throws Exception {		
		if (SystemTray.isSupported()) {
		    SystemTray tray = SystemTray.getSystemTray();

		    Image image = IconUtils.getIcon("icon").getImage();
		
		    TrayIcon icon = new TrayIcon(image, "guiscrot");
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
					} else if (SwingUtilities.isRightMouseButton(e)) {
						 GlobalPopupMenu.getPopupMenu().show(null, e.getXOnScreen(), e.getYOnScreen());
					}
		    	}
		    });
		    
		    tray.add(icon);
		}
	}
	
}
