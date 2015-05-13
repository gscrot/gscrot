package com.redpois0n.gscrot;

import iconlib.IconUtils;

import java.awt.Frame;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import statusicon.StatusIcon;
import statusicon.StatusIcon.Mode;

import com.redpois0n.gscrot.ui.GlobalPopupMenu;
import com.redpois0n.gscrot.ui.MainFrame;

public class TrayIconHelper {
	
	private static StatusIcon statusIcon;
	private static TrayIcon icon;
	
	public static StatusIcon getStatusIcon() {
		return statusIcon;
	}
	
	public static void setIndeterminate(boolean b) {
		if (statusIcon != null) {
			statusIcon.setIndeterminate(b);
		}
	}

	public static void initialize() throws Exception {		
		if (SystemTray.isSupported()) {
		    SystemTray tray = SystemTray.getSystemTray();

		    Image image = IconUtils.getIcon("icon-16x16").getImage();
		
		    icon = new TrayIcon(image, "gscrot");
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
		    
		    statusIcon = new StatusIcon(Mode.IMAGES, icon);
		    
		    for (int i = 1; i <= 8; i++) {
		    	statusIcon.getIcons().add(IconUtils.getIcon("load" + i).getImage());
		    }
		    
		    tray.add(icon);
		}
	}

	public static void showMessage(String title, MessageType type) {
		if (icon != null) {
			icon.displayMessage("gscrot", title, type);
		}
	}
}
