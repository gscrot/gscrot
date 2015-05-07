package com.redpois0n.gscrot.ui;

import iconlib.IconUtils;

import java.awt.GraphicsDevice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import com.redpois0n.gscrot.CaptureUploader;
import com.redpois0n.gscrot.Main;
import com.redpois0n.gscrot.ScreenshotHelper;
import com.redpois0n.gscrot.ui.components.CaptureUploaderCheckBoxMenuItem;

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
	    
	    // Monitor item
	    JMenu mntmMonitor = new JMenu("Monitor");
	    mntmMonitor.setIcon(IconUtils.getIcon("monitor"));
	    
	    GraphicsDevice[] devices = ScreenshotHelper.getScreens();
	    
	    for (int i = 0; i < devices.length; i++) {
	    	final GraphicsDevice device = devices[i];
	    	
	    	JMenuItem item = new JMenuItem("Monitor " + i, IconUtils.getIcon("monitor"));
	    	
	    	item.addActionListener(new ActionListener() {
	    		public void actionPerformed(ActionEvent e) {
	    			ScreenshotHelper.captureScreen(device);
	    		}
	    	});
	    	
	    	mntmMonitor.add(item);
	    }
	    
	    mnCapture.add(mntmMonitor);
	    
	    popup.add(mnCapture);
	    
	    popup.add(new JSeparator(JSeparator.HORIZONTAL));

	    // Image uploaders
	    JMenu mnImageUploaders = new JMenu("Image Uploaders");
	    mnImageUploaders.setIcon(IconUtils.getIcon(""));
	    	    
	    CaptureUploader current = CaptureUploader.getSelected();
	    for (CaptureUploader uploader : CaptureUploader.getAllUploaders()) {
	    	CaptureUploaderCheckBoxMenuItem mntmUploader = new CaptureUploaderCheckBoxMenuItem(uploader);
	    	
	    	mntmUploader.setSelected(uploader == current);

		    mnImageUploaders.add(mntmUploader);
	    }
	    
	    popup.add(mnImageUploaders);
	    
	    popup.add(new JSeparator(JSeparator.HORIZONTAL));

	    // Exit item
	    JMenuItem mntmExit = new JMenuItem("Exit", IconUtils.getIcon("cross"));
	    mntmExit.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		System.exit(0);
	    	}
	    });
	    
	    popup.add(mntmExit);
	    
	    return popup;
	}

}
