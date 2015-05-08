package com.redpois0n.gscrot.util;

import iconlib.IconUtils;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class Icons {
	
	public static final boolean TRANSPARENT = false;
	
	public static List<Image> getIcons() {
		List<Image> list = new ArrayList<Image>();
		
		String icon = TRANSPARENT ? "icon" : "icon2";
		
		list.add(IconUtils.getIcon(icon + "-256x256").getImage());
		list.add(IconUtils.getIcon(icon + "-128x128").getImage());
		list.add(IconUtils.getIcon(icon + "-64x64").getImage());
		list.add(IconUtils.getIcon(icon + "-32x32").getImage());
		list.add(IconUtils.getIcon(icon + "-16x16").getImage());

		return list;
	}

}
