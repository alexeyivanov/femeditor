package com.fem.api;

import java.util.List;

/**
 * GeometryShape
 *  - keep geometry data.
 *  - keep implicitly internal representation of shape, e.g. occ shape. 
 */
public interface GeometryShape {
	
	List<FaceMesh> getFaceMeshes();
	List<float[]> getEdgeArrays();

	<T> T getIntertalShape(Class<T> clazz);
}
