package com.fem.ui.editor.models;

import com.fem.api.Coordinate;

public interface ElementCoordinates {
	
	public Coordinate[] getIntegrationPoints(int order);
	
	public double[] getWeightFactors(int order);
	
	public double getV();

}
