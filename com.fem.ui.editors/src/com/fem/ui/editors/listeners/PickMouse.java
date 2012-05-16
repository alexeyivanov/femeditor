package com.fem.ui.editors.listeners;

import java.awt.event.MouseEvent;

import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.PickInfo;
import javax.media.j3d.Shape3D;

import com.fem.api.VisualShape;
import com.fem.ui.editor.model.Visible;
import com.sun.j3d.utils.pickfast.PickTool;
import com.sun.j3d.utils.pickfast.behaviors.PickMouseBehavior;

public class PickMouse extends PickMouseBehavior {

	protected int x_last, y_last;
	
	public PickMouse(Canvas3D canvas, BranchGroup root, Bounds bounds) {
		super(canvas, root, bounds);
		pickCanvas.setMode(PickInfo.PICK_GEOMETRY);			
		this.setSchedulingBounds(bounds);
	}

	@Override
	public void updateScene(int xpos, int ypos) {
		
		if (mevent.getID() == MouseEvent.MOUSE_MOVED) {
			System.err.println(mevent.getX() + " --- " +  mevent.getY());
		}
//		
		if ((mevent.getID() == MouseEvent.MOUSE_DRAGGED) &&
				!mevent.isAltDown() && mevent.isMetaDown()) {
			
			
				
////		if (mevent.getID() == MouseEvent.MOUSE_DRAGGED) {
//				int x = mevent.getX();
//				int y = mevent.getY();
//				System.err.println(x_last +  y_last);
//				int dx = x - x_last;
//				int dy = y - y_last;
//				
//				center.x += dx * 0.02;
//				center.y -= dy * 0.02;
//				
//				x_last = x;
//				y_last = y;
//				updateView();
		}else if (mevent.getID() == MouseEvent.MOUSE_PRESSED) {
			x_last = mevent	.getX();
			y_last = mevent.getY();
			System.err.println(x_last + " " +   y_last);
		}
//		
//		
	    
		if (!mevent.isMetaDown() && !mevent.isAltDown()){
			BranchGroup bg = null;
			Shape3D s = null;
			pickCanvas.setFlags(PickInfo.NODE | PickInfo.SCENEGRAPHPATH | PickInfo.CLOSEST_INTERSECTION_POINT);
			pickCanvas.setShapeLocation(xpos, ypos);
			
		
			PickInfo pickInfo = pickCanvas.pickClosest();				
			if(pickInfo != null) {
//				if (drawing instanceof Mesh) {
//					Point3d p = pickInfo.getClosestIntersectionPoint();
//					
//					Mesh m = (Mesh)drawing;
//					MeshPoint mp = m.find(p.x, p.y, p.z);
//					if (mp != null) mp.select();
//					
//					m.setLineColor(Color.GREEN);
//					m.setLineWidth(5);
////					createSceneGraph(drawing);
//					
////					view.detach();						
//					draw(drawing);
////					root.addChild(view);
//					return;
//				}
				
				System.out.println(pickInfo.getClosestIntersectionPoint());					
				
				bg = (BranchGroup) pickCanvas.getNode(pickInfo, PickTool.TYPE_BRANCH_GROUP);
				s = (Shape3D) pickCanvas.getNode(pickInfo, PickTool.TYPE_SHAPE3D);					
				
				if (bg != null) {
					System.out.println("Shape "+bg.getName()+" selected");
					if (bg.getUserData() != null) {
						VisualShape sh = (VisualShape) bg.getUserData();
						
//						TODO get Mesh
						
//						Mesh m = sh.getMesh();
//						if (m != null) {
//							Point3d p3d = pickInfo.getClosestIntersectionPoint();
//							m.selectNearestPoint(p3d.x, p3d.y, p3d.z);
//							m.createTextLabels();
//							System.out.println("Result = " + m.getResult(p3d.x, p3d.y, p3d.z));
//							draw(drawModel);
//						}
//						else {
//							sh.select();
////							drawing.meshShape(sh);
////							draw(drawing);
//						}
						
//						Visible v = (Visible) bg.getUserData();
//						v.select();							
					}						
				}
				
//				if (s != null) {
//					System.out.println("Shape "+s.getName()+" selected");
//					if (s.getUserData() != null) {
//						Visible v = (Visible) s.getUserData();
//						v.select();	
//					}
//					
//				}
				
		    } 
		}
	}
	
}
