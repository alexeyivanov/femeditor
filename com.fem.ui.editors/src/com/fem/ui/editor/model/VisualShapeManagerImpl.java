package com.fem.ui.editor.model;

import com.fem.api.GeometryShape;
import com.fem.api.VisualSettings;
import com.fem.api.VisualShape;
import com.fem.api.VisualShapeManager;

public class VisualShapeManagerImpl implements VisualShapeManager {

	@Override
	public VisualShape create(int type, GeometryShape shape, VisualSettings vs) {
		
		return new VisualShapeImpl(type, shape, vs);
	}

}
