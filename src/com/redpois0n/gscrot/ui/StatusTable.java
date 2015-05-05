package com.redpois0n.gscrot.ui;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.redpois0n.gscrot.Capture;

@SuppressWarnings("serial")
public class StatusTable extends JTable {
	
	public static final String COLUMN_TYPE = "Type";
	
	private DefaultTableModel model;
	
	public StatusTable() {
		model = new DefaultTableModel();
		model.addColumn(COLUMN_TYPE);
		
		setFillsViewportHeight(true);
		setModel(model);
		setDefaultRenderer(Object.class, new Renderer());
	}

	public void add(Capture p) {
		model.addRow(new Object[] { p });
	}
	
	private class Renderer extends DefaultTableCellRenderer {
		
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			
			Capture capture = null;
			
			for (int i = 0; i < column; i++) {
				if (table.getValueAt(row, i) instanceof Capture) {
					capture = (Capture) table.getValueAt(row, i);
					break;
				}
			}
			
			String colname = table.getColumnName(column);
			
			if (colname.equals(COLUMN_TYPE)) {
				label.setText(capture.getType().toString());
			}
			
			return label;
		}
	}

}
