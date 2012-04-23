package com.fem.api;

public class FaceMesh
{
	private float[] nodes;
	private int[] mesh;
			
	public FaceMesh(float[] nodes, int[] mesh)	{
		if(nodes.length%3!=0 || mesh.length%3!=0)
			throw new IllegalArgumentException();
		this.nodes = nodes;
		this.mesh = mesh;
	}

	public float[] getNodes() {
		return nodes;
	}
	
	public int[] getMesh() {
		return mesh;
	}
}
