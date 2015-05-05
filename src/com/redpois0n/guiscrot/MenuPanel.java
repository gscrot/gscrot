package com.redpois0n.guiscrot;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel {
	
	public MenuPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);
	}

	public void addComponent(Component c) {
		GridBagConstraints grid = new GridBagConstraints();
		grid.gridx = 0;
		grid.gridy = getComponentCount();
		grid.anchor = GridBagConstraints.WEST;
		
		add(c, grid);
	}
}
