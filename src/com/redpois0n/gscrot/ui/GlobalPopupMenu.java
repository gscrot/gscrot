package com.redpois0n.gscrot.ui;

import iconlib.IconUtils;

import java.awt.Color;
import java.awt.Component;
import java.awt.GraphicsDevice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import nativewindowlib.NativeWindow;
import nativewindowlib.WindowUtils;

import com.redpois0n.gscrot.BinaryImageProcessor;
import com.redpois0n.gscrot.Capture.Type;
import com.redpois0n.gscrot.CaptureUploader;
import com.redpois0n.gscrot.Config;
import com.redpois0n.gscrot.GraphicsImageProcessor;
import com.redpois0n.gscrot.ImageProcessor;
import com.redpois0n.gscrot.Main;
import com.redpois0n.gscrot.ScreenshotHelper;
import com.redpois0n.gscrot.ui.components.CaptureUploaderCheckBoxMenuItem;
import com.redpois0n.gscrot.ui.components.ImageProcessorCheckBoxMenuItem;
import com.redpois0n.gscrot.ui.components.JDropDownButton;
import com.redpois0n.gscrot.ui.components.MonitorPicker;
import com.redpois0n.gscrot.ui.settings.FrameSettings;
import com.redpois0n.gscrot.utils.Utils;
import com.redpois0n.oslib.OperatingSystem;

public class GlobalPopupMenu {
	
	public static final List<JMenuItem> SETTINGS_ITEMS = new ArrayList<JMenuItem>();
	
	public static List<Component> getMenu() {
		List<Component> list = new ArrayList<Component>();
		
		// Capture
		JDropDownButton btnCapture = new JDropDownButton("Capture", IconUtils.getIcon("camera"));
	    list.add(btnCapture);

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
	    btnCapture.getMenu().add(mntmRegion);
	    
	    JMenuItem mntmAllMonitors = new JMenuItem("All Monitors");
	    mntmAllMonitors.setIcon(IconUtils.getIcon("monitor-all"));
	    mntmAllMonitors.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			try {
					ScreenshotHelper.process(Type.FULL, ScreenshotHelper.capture(ScreenshotHelper.getWholeDesktop()));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
    		}
    	});
	    btnCapture.getMenu().add(mntmAllMonitors);
	    
	    JMenu mntmMonitor = new JMenu("Monitor");
	    mntmMonitor.setIcon(IconUtils.getIcon("monitor"));
	    GraphicsDevice[] devices = ScreenshotHelper.getScreens();
	    
	    JMenuItem mntmPickMonitor = new JMenuItem("Pick Monitor...", IconUtils.getIcon("monitor-select"));
	    mntmPickMonitor.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			new MonitorPicker().setVisible(true);
    		}
    	});
	    mntmMonitor.add(mntmPickMonitor);
	    mntmMonitor.addSeparator();
	    
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
	    
	    if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
	    	// Windows item
		    final JMenu mntmWindows = new JMenu("Window");
		    mntmWindows.setIcon(IconUtils.getIcon("window"));
		    
		    List<NativeWindow> windows = WindowUtils.getVisibleWindows();

			for (final NativeWindow window : windows) {
				JMenuItem item = new JMenuItem(window.getTitle(), window.getIcon());

				item.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							ScreenshotHelper.process(Type.WINDOW, ScreenshotHelper.capture(window));
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				});

				mntmWindows.add(item);
			}
		    btnCapture.add(mntmWindows);
	    }
	    
	    // Tools
	    JDropDownButton btnTools = new JDropDownButton("Tools", IconUtils.getIcon("toolbox"));
	    list.add(btnTools);
		JMenuItem mntmScreenColorPicker = new JMenuItem("Screen Color Picker", IconUtils.getIcon("pipette-color"));
		mntmScreenColorPicker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Main.createColorPicker();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnTools.add(mntmScreenColorPicker);
		
		JMenuItem mntmColorPicker = new JMenuItem("Color Picker", IconUtils.getIcon("color"));
		mntmColorPicker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(null, "Color Picker", Color.black);
				Utils.setColorInClipboard(color);
			}
		});
		btnTools.add(mntmColorPicker);
			    
	    list.add(new JSeparator());

	    
	    // Image Uploaders
	    ButtonGroup group = new ButtonGroup();

	    JDropDownButton btnImageUploaders = new JDropDownButton("Image Uploaders", IconUtils.getIcon("drive-upload"));
	    list.add(btnImageUploaders);
	    CaptureUploader selectedUploader = CaptureUploader.getSelected();
	    for (CaptureUploader uploader : CaptureUploader.getAllUploaders()) {
	    	CaptureUploaderCheckBoxMenuItem mntmUploader = new CaptureUploaderCheckBoxMenuItem(uploader);
	    	mntmUploader.setIcon(uploader.getIcon());
	    	
	    	mntmUploader.setSelected(uploader == selectedUploader);

		    btnImageUploaders.add(mntmUploader);
		    
		    group.add(mntmUploader);
	    }
	    	    
	    // Image processors
	    JDropDownButton btnImageProcessors = new JDropDownButton("Image Processors", IconUtils.getIcon("image-processor"));
	    list.add(btnImageProcessors);
	    for (BinaryImageProcessor processor: ImageProcessor.getBinaryProcessors()) {
	    	ImageProcessorCheckBoxMenuItem mntmProcessor = new ImageProcessorCheckBoxMenuItem(processor);
	    	mntmProcessor.setIcon(processor.getIcon());
	    	btnImageProcessors.add(mntmProcessor);
	    }
	    
	    for (GraphicsImageProcessor processor: ImageProcessor.getGraphicsProcessors()) {
	    	ImageProcessorCheckBoxMenuItem mntmProcessor = new ImageProcessorCheckBoxMenuItem(processor);
	    	mntmProcessor.setIcon(processor.getIcon());
	    	btnImageProcessors.add(mntmProcessor);
	    }
	    	    
	    list.add(new JSeparator());
	    
	    // After upload    
	    JDropDownButton btnAfterCapture = new JDropDownButton("After Capture", IconUtils.getIcon("after-capture"));
	    list.add(btnAfterCapture);
	    JCheckBoxMenuItem mntmImageClipboard = new JCheckBoxMenuItem("Copy Image to clipboard");
	    mntmImageClipboard.setSelected(Config.get(Config.KEY_COPY_IMAGE_TO_CLIPBOARD, "true").equalsIgnoreCase("true"));
	    mntmImageClipboard.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		boolean b = ((JCheckBoxMenuItem) e.getSource()).isSelected();
	    		
	    		Config.put(Config.KEY_COPY_IMAGE_TO_CLIPBOARD, b + "");
	    	}
	    });
	    btnAfterCapture.add(mntmImageClipboard);
	    	    
	    // After upload    
	    JDropDownButton btnAfterUpload = new JDropDownButton("After Upload", IconUtils.getIcon("after-upload"));
	    list.add(btnAfterUpload);
	    
	    JCheckBoxMenuItem mntmURLClipboard = new JCheckBoxMenuItem("Copy URL to clipboard");
	    mntmURLClipboard.setSelected(Config.get(Config.KEY_COPY_URL_TO_CLIPBOARD, "true").equalsIgnoreCase("true"));
	    mntmURLClipboard.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		boolean b = ((JCheckBoxMenuItem) e.getSource()).isSelected();
	    		
	    		Config.put(Config.KEY_COPY_URL_TO_CLIPBOARD, b + "");
	    	}
	    });
	    btnAfterUpload.add(mntmURLClipboard);
	    
	   	list.add(new JSeparator());
	 
	    // Settings
	    JDropDownButton mnSettings = new JDropDownButton("Settings", IconUtils.getIcon("settings"));
	    list.add(mnSettings);

	    JMenuItem mntmApplicationSettings = new JMenuItem("Application Settings", IconUtils.getIcon("settings"));
	    mntmApplicationSettings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new FrameSettings().setVisible(true);
			} 	
	    });
	    mnSettings.add(mntmApplicationSettings);
	    
	    JMenuItem mntmKeyBindings = new JMenuItem("Keybindings", IconUtils.getIcon("keys"));
	    mntmApplicationSettings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

			} 	
	    });
	    mnSettings.add(mntmKeyBindings);
	    
	    if (SETTINGS_ITEMS.size() > 0) {
	    	mnSettings.getMenu().addSeparator();
	    	for (JMenuItem item : SETTINGS_ITEMS) {
	    		mnSettings.add(item);
	    	}
	    }
	    	    	    
	    list.add(new JSeparator());
	    
	    list.add(Box.createVerticalGlue());
	    
	    // Exit
	    JDropDownButton mntmExit = new JDropDownButton("Exit", IconUtils.getIcon("cross"));
	    list.add(mntmExit);
	    mntmExit.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		System.exit(0);
	    	}
	    });	    
	    
		return list;
	}
	
	public static JPopupMenu getPopupMenu() {
	    JPopupMenu popup = new JPopupMenu();
	    
	    return popup;
	}

}
