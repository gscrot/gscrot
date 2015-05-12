package com.redpois0n.gscrot.ui;

import java.awt.BorderLayout;
import java.awt.GraphicsDevice;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;

import com.redpois0n.graphs.monitors.MonitorListener;
import com.redpois0n.graphs.monitors.PanelMonitors;
import com.redpois0n.graphs.monitors.PanelMonitors.PanelMonitor;
import com.redpois0n.graphs.monitors.RemoteMonitor;
import com.redpois0n.gscrot.ScreenshotHelper;

@SuppressWarnings("serial")
public class MonitorPicker extends JDialog {
	
	private PanelMonitors panelMonitors;
	private JLabel lblXY;
	private JLabel lblWidthHeight;
	private JButton btnThumbnails;
	
	public MonitorPicker() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		
		btnThumbnails = new JButton("Thumbnails");
		btnThumbnails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				btnThumbnails.setText("Reload");
			}
		});
		toolBar.add(btnThumbnails);
		
		lblXY = new JLabel("X, Y: ");
		toolBar.add(lblXY);
		
		lblWidthHeight = new JLabel("Width, Height:");
		toolBar.add(lblWidthHeight);
		
		panelMonitors = new PanelMonitors(getMonitors(), false);
		
		panelMonitors.addListener(new MonitorListener() {
			@Override
			public void onMonitorChange(RemoteMonitor monitor) {						
				PanelMonitor panel = null;
				
				for (PanelMonitor panel1 : panelMonitors.getPanels()) {
					if (panel1.getMonitor().equals(monitor)) {
						panel = panel1;
						break;
					}
				}
				
				if (panel != null) {
					lblXY.setText("X, Y: " + panel.getMonitor().getX() + ", " + panel.getMonitor().getY() + "     ");
					lblWidthHeight.setText("Width, Height: " + panel.getMonitor().getWidth() + ", " + panel.getMonitor().getHeight());
				}
			}
		});
		
		
		scrollPane.setViewportView(panelMonitors);
		setLayout(new BorderLayout(0, 0));
		
		add(toolBar, BorderLayout.SOUTH);
		add(scrollPane);
		
		panelMonitors.reload();
	}
	
	public RemoteMonitor[] getMonitors() {
		RemoteMonitor[] monitors = new RemoteMonitor[ScreenshotHelper.getScreens().length];
		
		for (int i = 0; i < ScreenshotHelper.getScreens().length; i++) {
			GraphicsDevice device = ScreenshotHelper.getScreens()[i];
			
			Rectangle rect = device.getDefaultConfiguration().getBounds();
			
			monitors[i] = new RemoteMonitor(device.getIDstring(), rect.x, rect.y, rect.width, rect.height);
		}
		
		return monitors;
	}
	
	public PanelMonitors getPanelMonitors() {
		return panelMonitors;
	}
}
