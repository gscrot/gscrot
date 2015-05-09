package com.redpois0n.gscrot.ui.settings;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

@SuppressWarnings("serial")
public class FrameSettings extends JFrame {

	private Map<String, JPanel> panels = new HashMap<String, JPanel>();
	private JPanel panel;
	
	public FrameSettings() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.gray.brighter()));
		add(panel, BorderLayout.CENTER);
		
		JTree tree = new JTree();
		tree.setRootVisible(false);
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent arg0) {
				try {
					String path = arg0.getPath().getPath()[1].toString();
					if (isPanel(path)) {
						panel.removeAll();
						panel.add(panels.get(path.toLowerCase()));
						panel.revalidate();
						panel.repaint();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		tree.setBorder(BorderFactory.createLineBorder(Color.gray.brighter()));
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Settings") {
			{
				addNodes(this);
			}
		};
		tree.setModel(new DefaultTreeModel(root));
		
		add(tree, BorderLayout.WEST);
	}
	
	public boolean isPanel(String str) {
		return panels.containsKey(str.toLowerCase());
	}
	
	public void addNodes(DefaultMutableTreeNode root) {
		panels.put("images", new ImageSettingsPanel());
		root.add(new DefaultMutableTreeNode("Images"));
	}

}
