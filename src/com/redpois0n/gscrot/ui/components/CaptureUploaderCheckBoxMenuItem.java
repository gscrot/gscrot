package com.redpois0n.gscrot.ui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;

import com.redpois0n.gscrot.CaptureUploader;

@SuppressWarnings("serial")
public class CaptureUploaderCheckBoxMenuItem extends JCheckBoxMenuItem implements ActionListener {

	private CaptureUploader uploader;
	
	public CaptureUploaderCheckBoxMenuItem(CaptureUploader uploader) {
		this.uploader = uploader;
		
		setText(uploader.getName());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (isSelected()) {
			CaptureUploader.setSelected(uploader);
		}
	}

}
