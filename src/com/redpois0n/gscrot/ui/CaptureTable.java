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
	
	public static final String COLUMN_TYPE = "Type";
	
	private DefaultTableModel model;
	
	public CaptureTable() {
		model = new DefaultTableModel();
		model.addColumn(COLUMN_TYPE);
		
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
			
			if (colname.equals(COLUMN_TYPE)) {
				label.setText(capture.getType().toString());
				label.setIcon(capture.getStatus().getIcon());
			}
			
			return label;
		}
	}

}
