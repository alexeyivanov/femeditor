package com.fem.ui.editor.model;

import java.awt.Color;

import com.fem.api.VisualSettings;
import com.fem.api.VisualSettingsFactory;

public class VisualSettingsFactoryJava3DImpl implements VisualSettingsFactory {

	@Override
	public VisualSettings create(Color faceColor, Color lineColor,
			float lineWidth, double t) {
		
		return new VisualSettingsJava3DImpl(faceColor, lineColor, lineWidth, null, t);
	}

}
