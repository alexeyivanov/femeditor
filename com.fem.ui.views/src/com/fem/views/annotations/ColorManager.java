package com.fem.views.annotations;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jface.text.source.ISharedTextColors;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class ColorManager implements ISharedTextColors {

	// note that color settings will override these, these are the defaults
	public static final RGB MULTI_LINE_COMMENT = new RGB(128, 128, 128);
	public static final RGB SINGLE_LINE_COMMENT = new RGB(128, 128, 128);
	public static final RGB DEFAULT = new RGB(0, 0, 0);
	public static final RGB KEYWORD = new RGB(0, 0, 128);
	public static final RGB METHOD = new RGB(205, 142, 23);
	public static final RGB STRING = new RGB(0, 128, 0);
	public static final RGB NUMBER = new RGB(0, 0, 255);
	public static final RGB GLOBAL = new RGB(102, 14, 122);
	public static final RGB GLOBAL_STRING = new RGB(0, 102, 102);
	public static final RGB BRACKET = new RGB(255, 0, 0);
	public static final RGB LUA_METHOD = new RGB(42, 164, 164);
	public static final RGB TODO = new RGB(0, 0, 200);
	public static final RGB MATCHED_BRACE = new RGB(153, 204, 255);
	public static final RGB LINE_NUMBER = new RGB(140, 140, 140);
	public static final RGB SELECTED_LINE_BACKGROUND_COLOR = new RGB(225, 235,
			224);
	public static final RGB ERROR = new RGB(255, 0, 0);
	public static final RGB WARNING = new RGB(201, 135, 47);
	public static final RGB VARIABLE_HOVER = new RGB(0, 0, 255);
	
	public static final RGB FEM_COMMENT = new RGB(128, 0, 0);
	public static final RGB FEM_PROPOSAL_BG = new RGB(230,255,230);

	private static Map<RGB, Color> colorTable;
	private static ColorManager instance;

	public ColorManager() {
		if (colorTable == null) {
			colorTable = new HashMap<RGB, Color>();
		}
	}

	public static Color get(int r, int g, int b) {
		return getInstance().getColor(r, g, b);
	}

	public static Color get(RGB rgb) {
		return getInstance().getColor(rgb);
	}

	public static ColorManager getInstance() {
		if (instance == null)
			instance = new ColorManager();

		return instance;
	}

	public void dispose() {
		Iterator<Color> e = colorTable.values().iterator();
		while (e.hasNext())
			((Color) e.next()).dispose();
	}

	public Color getWhite() {
		return getColor(new RGB(255, 255, 255));
	}

	public Color getBlack() {
		return getColor(new RGB(0, 0, 0));
	}

	public Color getColor(RGB rgb) {
		Color color = (Color) colorTable.get(rgb);

		if (color == null) {
			color = new Color(Display.getCurrent(), rgb);
			colorTable.put(rgb, color);
		}

		return color;
	}

	public Color getColor(int r, int g, int b) {
		Color color = (Color) colorTable.get(new RGB(r, g, b));

		if (color == null) {
			RGB rgb = new RGB(r, g, b);
			color = new Color(Display.getCurrent(), rgb);
			colorTable.put(rgb, color);
		}

		return color;
	}

}
