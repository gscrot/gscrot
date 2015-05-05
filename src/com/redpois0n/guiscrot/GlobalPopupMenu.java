package com.redpois0n.guiscrot;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class GlobalPopupMenu {
	
	public static JPopupMenu getPopupMenu() {
	    JPopupMenu popup = new JPopupMenu();

	    popup.add(new JMenuItem("test"));
	    
	    JMenu menu = new JMenu("JMenu");
	    menu.add(new JMenuItem("Test menu"));
	    popup.add(menu);
	    
	    return popup;
	}

}
