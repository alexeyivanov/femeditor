package com.fem.api;

import java.awt.Color;

public interface VisualSettingsFactory {

	VisualSettings create(Color faceColor, Color lineColor, float lineWidth, double t);
}
