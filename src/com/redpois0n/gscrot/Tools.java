package com.redpois0n.gscrot;

import java.awt.Color;

import javax.swing.JColorChooser;

import com.redpois0n.gscrot.utils.Utils;

public class Tools {

	/**
	 * Opens the system native color chooser
	 */
	public static void callColorPicker() {
		Color color = JColorChooser.showDialog(null, "Color Picker", Color.black);
		Utils.setColorInClipboard(color);		
	}

	/**
	 * Creates a RegionCapture with the purpose of getting the clicked pixel color
	 */
	public static void callScreenColorPicker() {
		try {
			Main.createColorPicker();
		} catch (Exception ex) {
			ex.printStackTrace();
		}		
	}

}
