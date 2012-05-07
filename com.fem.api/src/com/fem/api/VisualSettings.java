package com.fem.api;

import java.awt.Color;

public interface VisualSettings {

	<T> T getLineAppearance(final Class<T> clazz);
	<T> T getSelectedLineAppearance(final Class<T> clazz);
	<T> T getMeshAppearance(final Class<T> clazz);
	<T> T getSelectedMeshAppearance(final Class<T> clazz);
	<T> T getFaceAppearance(final Class<T> clazz);
	<T> T getSelectedFaceAppearance(final Class<T> clazz);
	
	<T> T getSelectedTransparencyAttributes(final Class<T> clazz);
	
	<T> T getScaleColor(final Class<T> clazz, float v);
	
	<T> T getJ3DColor(final Class<T> clazz, Color c);
	
	Color getFaceColor();
	
	void setFaceColor(Color faceColor);
	
	void setTransparency(double t);
	
	double getTransparncy();
	
	Color getLineColor();
	
	void setLineColor(Color lineColor);
	
	float getLineWidth();
	
	void setLineWidth(float lineWidth);
	
	<T> T  getTexture(final Class<T> clazz);
	
	<T> void setTexture(T texture, final Class<T> clazz);
	
	VisualSettings cloneObject();
}
