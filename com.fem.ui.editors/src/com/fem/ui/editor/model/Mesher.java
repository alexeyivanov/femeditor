package com.fem.ui.editor.model;

import org.jcae.opencascade.jni.BRepPrimAPI_MakeBox;
import org.jcae.opencascade.jni.TopAbs_ShapeEnum;
import org.jcae.opencascade.jni.TopExp_Explorer;
import org.jcae.opencascade.jni.TopoDS_Edge;
import org.jcae.opencascade.jni.TopoDS_Shape;

import com.fem.api.IDrawModel;
import com.fem.api.VisualShape;


public class Mesher {
	
	static {
		System.load("C:/AllLib/AllLib/Gmsh/Gmsh.dll");
	}
	//TopoDS_Shape s
	public static native void CreateGmodel(long s);
	
	public static native void SetMeshSize(double size);
	//public static native void SetMeshSizeInPoint(double x, double y, double z, double size);
	public static native void SetMeshSizeInPoint(long v, double size);
	public static native void SetPointsTransfinite(int nb);
	public static native void SetPointsTransfiniteInEdge(long e,int nb);

	public static native double[] MeshShape();	 

    public static native int[] Indtetr();
	public static native int[] Indtrian();
	public static native int[] Indline();

	
	public static Mesh createMesh(Shape s) {
		Mesh m = new Mesh();
		
		CreateGmodel(TopoDS_Shape.getCPtr(s.getShape()));
		SetMeshSize(0.1);
		
		TopExp_Explorer explorer = new TopExp_Explorer();
//		for (explorer.init(s.shape, TopAbs_ShapeEnum.VERTEX); explorer.more(); explorer.next())
//		{						
//			TopoDS_Shape sh = explorer.current();
//			if (!(sh instanceof TopoDS_Vertex)) continue; // should not happen!
//			TopoDS_Vertex vert = (TopoDS_Vertex)sh;
//			//SetMeshSize(0.1);
//			//SetMeshSizeInPoint(vert.getCPtr(vert),0.01);
//			break;
//		}
//		
		for (explorer.init(s.getShape(), TopAbs_ShapeEnum.EDGE); explorer.more(); explorer.next())
		{						
			TopoDS_Shape sh = explorer.current();
			if (!(sh instanceof TopoDS_Edge)) continue; // should not happen!
			TopoDS_Edge edge = (TopoDS_Edge)sh;
			SetPointsTransfinite(10);
			SetPointsTransfiniteInEdge(edge.getCPtr(edge),50);
			break;
		}
//		
		
//		SetPointsTransfinite(10);
//		SetMeshSize(0.1);
//		SetMeshSizeInPoint(2,2,2,0.5);
//		SetMeshSizeInPoint(0,2,2,0.02);
//		SetMeshSizeInPoint(1,1,1,0.02);
		double[] points = MeshShape();
		int[] indtetr = Indtetr();
		int[] indtrian = Indtrian();
		int[] indline = Indline();
		
//		 for (int i=0; i<points.length; i++)
//	        	System.out.println(points[i]);
//	        for (int i=0; i<indtetr.length; i++)
//	        	System.out.println(indtetr[i]);
		
		m.addPoints(points);
		
//		TODO get VisualSettings
		
		m.setFaceColor(s.getVisualSettings().getFaceColor());
//		m.addTetrahedrons(indtetr);
		m.setFaceColor(s.getVisualSettings().getFaceColor());
		m.addTriangles(indtrian);
		m.setLineColor(s.getVisualSettings().getLineColor());
		m.addLines(indline);
		
//		for (int i = 0; i < indtetr.length; i+=4) {
//			m.tetrahedron(indtetr[i]-1, indtetr[i+1]-1, indtetr[i+2]-1, indtetr[i+3]-1);
//		}
		return m;
	}
	
	public static Mesh createMesh(VisualShape s) {
		Mesh m = new Mesh();
		
		CreateGmodel(TopoDS_Shape.getCPtr(s.getShape(TopoDS_Shape.class)));
		SetMeshSize(0.1);
		
		TopExp_Explorer explorer = new TopExp_Explorer();
//		for (explorer.init(s.shape, TopAbs_ShapeEnum.VERTEX); explorer.more(); explorer.next())
//		{						
//			TopoDS_Shape sh = explorer.current();
//			if (!(sh instanceof TopoDS_Vertex)) continue; // should not happen!
//			TopoDS_Vertex vert = (TopoDS_Vertex)sh;
//			//SetMeshSize(0.1);
//			//SetMeshSizeInPoint(vert.getCPtr(vert),0.01);
//			break;
//		}
//		
		for (explorer.init(s.getShape(TopoDS_Shape.class), TopAbs_ShapeEnum.EDGE); explorer.more(); explorer.next())
		{						
			TopoDS_Shape sh = explorer.current();
			if (!(sh instanceof TopoDS_Edge)) continue; // should not happen!
			TopoDS_Edge edge = (TopoDS_Edge)sh;
			SetPointsTransfinite(10);
			SetPointsTransfiniteInEdge(edge.getCPtr(edge),50);
			break;
		}
//		
		
//		SetPointsTransfinite(10);
//		SetMeshSize(0.1);
//		SetMeshSizeInPoint(2,2,2,0.5);
//		SetMeshSizeInPoint(0,2,2,0.02);
//		SetMeshSizeInPoint(1,1,1,0.02);
		double[] points = MeshShape();
		int[] indtetr = Indtetr();
		int[] indtrian = Indtrian();
		int[] indline = Indline();
		
//		 for (int i=0; i<points.length; i++)
//	        	System.out.println(points[i]);
//	        for (int i=0; i<indtetr.length; i++)
//	        	System.out.println(indtetr[i]);
		
		m.addPoints(points);
//		m.setFaceColor(s.getVisualSettings().getFaceColor());
////		m.addTetrahedrons(indtetr);
//		m.setFaceColor(s.getVisualSettings().getFaceColor());
//		m.addTriangles(indtrian);
//		m.setLineColor(s.getVisualSettings().getLineColor());
		m.addLines(indline);
		
//		for (int i = 0; i < indtetr.length; i+=4) {
//			m.tetrahedron(indtetr[i]-1, indtetr[i+1]-1, indtetr[i+2]-1, indtetr[i+3]-1);
//		}
		return m;
	}
	
	public static void main(String args[]) {		
    	
		System.out.println(System.getProperty("java.library.path"));
		Mesher m= new Mesher();
    	double d[];
    	int it[];
    	
    	IDrawModel dr = new DrawModelImpl(new VisualSettingsFactoryJava3DImpl());
    	dr.setGeometryShapeManager(new GeometryShapeManagerOCC());
    	dr.setDrawShapeModelManager(new VisualShapeManagerImpl());
    	
		VisualShape cube = dr.box(0.1, 0.1, 0.1);
		
		
		TopoDS_Shape box=new BRepPrimAPI_MakeBox(
				new double[]{0,0,0},
				new double[]{1,1,1}
				).shape();
		
    	m.CreateGmodel(TopoDS_Shape.getCPtr(box));
        d=m.MeshShape();
        it=m.Indtetr();
        for (int i=0; i<d.length; i++)
        	System.out.println(d[i]);
        for (int i=0; i<it.length; i++)
        	System.out.println(it[i]);
    }
	
}
