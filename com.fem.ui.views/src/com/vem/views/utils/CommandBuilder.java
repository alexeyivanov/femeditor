package com.vem.views.utils;

import java.util.HashMap;
import java.util.Map;
/*
 * CommandBuilder
 */
public class CommandBuilder {
	
	

	public static Map<String, String> buildCommands() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("circle(double r)", "draw circle with specified radious");
		result.put("cylinder(double r, double h, double angle)", "draw cylinder");
		result.put("box(double dx, double dy, double dz)", "draw box");
		result.put("delete(VisualShape s)", "delete shape");
		result.put("drawElement()", "draw some element");
		result.put("deleteSelected()", "delete selected");
		result.put("cone(double baseRadius, double topRadius, double h, double angle)", "draw cone");
		result.put("move(double x, double y, double z)", "move element");
		result.put("cut(VisualShape s1, VisualShape s2)", "cut some element");
		return result;
	}

}
