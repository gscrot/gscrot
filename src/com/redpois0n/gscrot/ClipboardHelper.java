package com.redpois0n.gscrot;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

public class ClipboardHelper {

	/**
	 * @return returns the system clipboard
	 */
	public static Clipboard getClipboard() {
		return Toolkit.getDefaultToolkit().getSystemClipboard();
	}

	/**
	 * Sets string in clipboard
	 * 
	 * @param s
	 */
	public static void setString(String s) {
		StringSelection selection = new StringSelection(s);
		getClipboard().setContents(selection, selection);
	}

	/**
	 * Sets image in clipboard
	 * @param image
	 */
	public static void setImage(Image image) {
		ImageTransferable transferable = new ImageTransferable(image);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(transferable, null);
	}

	/**
	 * http://www.java2s.com/Code/Java/2D-Graphics-GUI/ThisprogramdemonstratesthetransferofimagesbetweenaJavaapplicationandthesystemclipboard.htm
	 *
	 */
	private static class ImageTransferable implements Transferable {

		private Image image;

		public ImageTransferable(Image image) {
			this.image = image;
		}

		public DataFlavor[] getTransferDataFlavors() {
			return new DataFlavor[] { DataFlavor.imageFlavor };
		}

		public boolean isDataFlavorSupported(DataFlavor flavor) {
			return flavor.equals(DataFlavor.imageFlavor);
		}

		public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
			if (flavor.equals(DataFlavor.imageFlavor)) {
				return image;
			} else {
				throw new UnsupportedFlavorException(flavor);
			}
		}
	}

}
