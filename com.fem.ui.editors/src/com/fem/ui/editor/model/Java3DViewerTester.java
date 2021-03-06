package com.fem.ui.editor.model;

import java.awt.Color;

import com.fem.api.IDrawModel;
import com.fem.api.VisualShape;

public class Java3DViewerTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		IDrawModel d = drawWall2();
		IDrawModel d = drawFN2();
		
		d.setMeshSize(0.1);
		d.meshAll();
		
//		Java3DViewer.view(d);
	}
	
	public static IDrawModel drawFN2() {
		
		IDrawModel d = new DrawModelImpl(new VisualSettingsFactoryJava3DImpl());
		d.setGeometryShapeManager(new GeometryShapeManagerOCC());
		d.setDrawShapeModelManager(new VisualShapeManagerImpl());
		
		d.setLineWidth(0.1f);
		
		d.circle(0.6);
//		
//		d.circle(0.4);
////		
//		d.moveTo(0, -0.2, 0);
//		d.setFaceColor(Color.CYAN);
//		VisualShape b1 = d.box(0.8, 0.3, 0.3);
//		
//		d.setFaceColor(Color.GREEN);
//		VisualShape b2 = d.box(0.8, 0.15, 0.6);
//				
//		d.setFaceColor(Color.MAGENTA);
//		VisualShape box = d.box(-0.25, 0.25, 0.25);
//		
//		d.setFaceColor(Color.ORANGE);
//		d.box(-0.1, 0.1, 0.35);
//		
////		d.setTexture("brick1.gif");
//		
//		d.setFaceColor(Color.YELLOW);
//		VisualShape cyl = d.cylinder(0.5, 0.7, Math.PI*3/2);
//				
////		d.noTexture();
//		d.moveTo(0, 0, 0);
//		d.setFaceColor(Color.BLUE);
//		d.cylinder(0.2, 0.3, Math.PI*2);
//		d.save("FN.brep");
//		d.load("C:/Users/echekanina/Documents/EclipseFEMProject/workspaceFEMFinal/femeditor/columns.brep");
		return d;
	}
	
	public static IDrawModel drawWall2() {
		IDrawModel d = new DrawModelImpl(new VisualSettingsFactoryJava3DImpl());
		d.setGeometryShapeManager(new GeometryShapeManagerOCC());
		d.setDrawShapeModelManager(new VisualShapeManagerImpl());
		
		d.setCheckIntersection(false);
		d.setFaceColor(Color.WHITE);
		d.setTransparency(0);
		d.moveTo(-0.4,0,-0.4);		
		VisualShape base = d.box(0.8, 0.2, 0.8);
		d.moveTo(0, 0.2, 0);
		VisualShape col = d.cone(0.3, 0.2, 3.8, Math.PI*2);
		d.moveTo(0, 4, 0);
		VisualShape capitel1 = d.cone(0.2, 0.3, 0.2, Math.PI*2);
		d.move(0, 0.2, 0);
		VisualShape capitel2 = d.cylinder(0.35, 0.15, Math.PI*2);
		VisualShape column = d.fuse(base, col);
		column = d.fuse(column, capitel1);
		column = d.fuse(column, capitel2);
		d.moveTo(-1, 0, 0);
		VisualShape cutBox = d.box(5, 6, 1);
		column = d.cut(column, cutBox);
		d.delete(cutBox);
		VisualShape columns = d.array(column, 6, 2, 0, 0);				
		d.moveTo(-0.4, 4.35, -0.4);		
		VisualShape beam = d.box(5*2+0.8, 0.3, 0.4);
		
		d.moveTo(-0.4, 0, 0);
		d.setFaceColor(Color.GREEN);		
		VisualShape wall = d.box(5*2+0.8, 4.65, 0.5);
		
		wall = d.fuse(wall, beam);
		d.fuse(columns, wall);
	
		d.setDirection(0, 0, 1);
		d.moveTo(1, 3, 0);
		VisualShape arch = d.cylinder(0.5, 0.5, Math.PI*2);			
		d.move(-0.5,0,0);
		VisualShape door = d.box(1, -3, 0.5);
		door = d.fuse(door, arch);
		wall = d.cut(wall,door);		
		door = d.copy(door, 2, 0, 0);
		wall = d.cut(wall,door);
		door = d.copy(door, 2, 0, 0);
		wall = d.cut(wall,door);
		door = d.copy(door, 2, 0, 0);
		wall = d.cut(wall,door);
		door = d.copy(door, 2, 0, 0);
		wall = d.cut(wall,door);
//		
//		d.lineTo(20,0,0);
//		d.moveTo(0, 0, 0);
//		d.lineTo(0,20,0);
////		d.setPosition(0, 0, 0);
//		d.moveTo(0, 0, 0);
//		d.lineTo(0,0,20);
//		d.setPosition(0, 0, 0);
		
		
//		d.save("columns.brep");	
		return d;
	}

}

