package com.redpois0n.gscrot.ui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.redpois0n.gscrot.Capture;

@SuppressWarnings("serial")
public class CaptureTable extends JTable {
	
	public static final String COLUMN_FILENAME = "Filename";
	public static final String COLUMN_STATUS = "Status";
	public static final String COLUMN_TYPE = "Type";
	public static final String COLUMN_URL = "URL";
	
	private DefaultTableModel model;
	
	public CaptureTable() {
		model = new DefaultTableModel() {
			public boolean isCellEditable(int paramInt1, int paramInt2) {
				return false;
			}
		};
		model.addColumn(COLUMN_FILENAME);
		model.addColumn(COLUMN_STATUS);
		model.addColumn(COLUMN_TYPE);
		model.addColumn(COLUMN_URL);
		
		setFillsViewportHeight(true);
		setModel(model);
		setDefaultRenderer(Object.class, new Renderer());
		setGridColor(Color.white);
		setRowHeight(25);
	}

	/**
	 * Adds capture to top
	 * @param p
	 */
	public void add(Capture p) {
		model.insertRow(0, new Object[] { p });
	}
	
	private class Renderer extends DefaultTableCellRenderer {
		
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			Capture capture = null;
			
			for (int i = 0; i < table.getColumnCount(); i++) {
				if (table.getValueAt(row, i) instanceof Capture) {
					capture = (Capture) table.getValueAt(row, i);
					break;
				}
			}
			
			String colname = table.getColumnName(column);
			
			label.setIcon(null);
			
			if (colname.equals(COLUMN_FILENAME)) {
				label.setText(capture.getFilename());
				label.setIcon(capture.getStatus().getIcon());
			} else if (colname.equals(COLUMN_STATUS)) {
				label.setText(capture.getStatus().toString());
			} else if (colname.equals(COLUMN_TYPE)) {
				label.setText(capture.getType().toString());
			} else if (colname.equals(COLUMN_URL) && capture.getResponse() != null) {
				label.setText(capture.getResponse().getUrl());
			}
			
			return label;
		}
	}

}
