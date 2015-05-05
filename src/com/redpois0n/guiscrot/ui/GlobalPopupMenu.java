package com.redpois0n.guiscrot.ui;

import iconlib.IconUtils;

import java.awt.GraphicsDevice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.redpois0n.guiscrot.Main;
import com.redpois0n.guiscrot.ScreenshotHelper;

public class GlobalPopupMenu {
	
	public static JPopupMenu getPopupMenu() {
	    JPopupMenu popup = new JPopupMenu();
	    
	    // Capture category
	    JMenu mnCapture = new JMenu("Capture");
	    mnCapture.setIcon(IconUtils.getIcon("camera"));
	    
	    JMenuItem mntmRegion = new JMenuItem("Region", IconUtils.getIcon("region-select"));
	    mntmRegion.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		try {
					Main.createBackground();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
	    	}
	    });
	    mnCapture.add(mntmRegion);
	    
	    JMenu mntmMonitor = new JMenu("Monitor");
	    mntmMonitor.setIcon(IconUtils.getIcon("monitor"));
	    mntmMonitor.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		
	    	}
	    });
	    
	    GraphicsDevice[] devices = ScreenshotHelper.getScreens();
	    
	    for (int i = 0; i < devices.length; i++) {
	    	GraphicsDevice device = devices[i];
	    	
	    	JMenuItem item = new JMenuItem("Monitor " + i, IconUtils.getIcon("monitor"));
	    	
	    	item.addActionListener(new ActionListener() {
	    		public void actionPerformed(ActionEvent e) {
	    			// TODO capture selected monitor
	    		}
	    	});
	    	
	    	mntmMonitor.add(item);
	    }
	    
	    mnCapture.add(mntmMonitor);
	    
	    popup.add(mnCapture);
	    
	    return popup;
	}

}
