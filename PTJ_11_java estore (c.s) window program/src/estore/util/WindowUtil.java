package estore.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

public class WindowUtil {
	/**
	 * @description make window at centre of screen
	 * @param c
	 * @author XLT
	 */
	public static void centreWindow(Component c) {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension windowDimension = c.getSize();
		int x = (int) (dimension.getWidth() / 2 - windowDimension.getWidth() / 2);
		int y = (int) (dimension.getHeight() / 2 - windowDimension.getHeight() / 2);
		c.setLocation(x, y);
	}
}
