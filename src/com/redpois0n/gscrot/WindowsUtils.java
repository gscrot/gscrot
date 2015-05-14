package com.redpois0n.gscrot;

import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;

public class WindowsUtils {

	/**
	 * https://stackoverflow.com/questions/3188484/windows-how-to-get-a-list-of-all-visible-windows
	 * 
	 * https://stackoverflow.com/questions/7521693/converting-c-sharp-to-java-jna-getmodulefilename-from-hwnd
	 */
	public static List<NativeWindow> getWindows() {
		final List<NativeWindow> inflList = new ArrayList<NativeWindow>();
		final List<Integer> order = new ArrayList<Integer>();

		int top = User32.INSTANCE.GetTopWindow(0);

		while (top != 0) {
			order.add(top);
			top = User32.INSTANCE.GetWindow(top, User32.GW_HWNDNEXT);
		}

		User32.INSTANCE.EnumWindows(new WndEnumProc() {
			public boolean callback(int hWnd, int lParam) {
				if (User32.INSTANCE.IsWindowVisible(hWnd)) {
					NativeRectagle r = new NativeRectagle();
					User32.INSTANCE.GetWindowRect(hWnd, r);
					byte[] buffer = new byte[1024];
					User32.INSTANCE.GetWindowTextA(hWnd, buffer, buffer.length);

					byte[] buffer2 = new byte[1024];

					Pointer zero = new Pointer(0);
					IntByReference pid = new IntByReference();
					User32.INSTANCE.GetWindowThreadProcessId(hWnd, pid);

					Pointer ptr = Kernel32.INSTANCE.OpenProcess(1040, false, pid.getValue());
					PsAPI.INSTANCE.GetModuleFileNameExA(ptr, zero, buffer2, buffer2.length);
					
					String title = Native.toString(buffer);
					String process = Native.toString(buffer2);			
					inflList.add(new NativeWindow(hWnd, r, title, process));

				}
				return true;
			}
		}, 0);

		Collections.sort(inflList, new Comparator<NativeWindow>() {
			public int compare(NativeWindow o1, NativeWindow o2) {
				return order.indexOf(o1.hwnd) - order.indexOf(o2.hwnd);
			}
		});

		User32.INSTANCE.SetForegroundWindow(inflList.get(5).hwnd);

		return inflList;
	}

	public static interface WndEnumProc extends StdCallLibrary.StdCallCallback {

		public abstract boolean callback(int hWnd, int lParam);
	}

	public static interface User32 extends StdCallLibrary {

		public static final User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);

		public static final int GW_HWNDNEXT = 2;

		public abstract boolean EnumWindows(WndEnumProc wndenumproc, int lParam);

		public abstract boolean IsWindowVisible(int hWnd);

		public abstract int GetWindowRect(int hWnd, NativeRectagle r);

		public abstract void GetWindowTextA(int hWnd, byte[] buffer, int buflen);

		public abstract int GetWindowThreadProcessId(int hWnd);

		public abstract int GetTopWindow(int hWnd);

		public abstract int GetWindow(int hWnd, int flag);

		public abstract int SetForegroundWindow(int hWnd);

		public abstract int GetWindowThreadProcessId(int hWnd, IntByReference pid);

	}

	public static final int PROCESS_QUERY_INFORMATION = 0x0400;

	public interface Kernel32 extends StdCallLibrary {
		Kernel32 INSTANCE = (Kernel32) Native.loadLibrary("kernel32", Kernel32.class);

		public Pointer OpenProcess(int dwDesiredAccess, boolean bInheritHandle, int dwProcessId);

		public int GetTickCount();
	};

	public interface PsAPI extends StdCallLibrary {
		PsAPI INSTANCE = (PsAPI) Native.loadLibrary("psapi", PsAPI.class);

		int GetModuleFileNameExA(Pointer process, Pointer hModule, byte[] lpString, int nMaxCount);

	};

	public static class NativeRectagle extends Structure {

		public int left;
		public int right;
		public int top;
		public int bottom;

		@Override
		protected List<String> getFieldOrder() {
			List<String> list = new ArrayList<String>();
			list.add("left");
			list.add("right");
			list.add("top");
			list.add("bottom");

			return list;
		}
	}

	public static class NativeWindow {

		private int hwnd;
		private NativeRectagle rect;
		private String title;
		private String process;

		public NativeWindow(int hwnd, NativeRectagle rect, String title, String process) {
			this.hwnd = hwnd;
			this.rect = rect;
			this.title = title;
			this.process = process;
		}

		public int getHwnd() {
			return hwnd;
		}

		public Rectangle getRectangle() {
			return new Rectangle(rect.left, rect.top, rect.right, rect.bottom);
		}

		public String getTitle() {
			return title;
		}

		public boolean isMinimized() {
			return rect.left <= -32000;
		}

		public String toString() {
			return String.format("(%d,%d)-(%d,%d) : \"%s\" %s", rect.left, rect.top, rect.right, rect.bottom, title, process);
		}

		public Icon getIcon() {
			return FileSystemView.getFileSystemView().getSystemIcon(new File(process));
		}
	}
}