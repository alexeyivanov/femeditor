package com.fem.ui.editors.listeners;

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.PickInfo;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import com.fem.ui.editor.model.Java3DViewer;
import com.fem.ui.editor.model.Visible;
import com.sun.j3d.utils.behaviors.mouse.MouseBehaviorCallback;
import com.sun.j3d.utils.pickfast.PickCanvas;
import com.sun.j3d.utils.pickfast.PickTool;

public class KeyMouseListener  /*extends ViewPlatformAWTBehavior /*/extends KeyMouseBehavior {
	
	private Java3DViewer viewer;
	private PickCanvas pickCanvas;
	double z_factor = .04;
    Vector3d translation = new Vector3d();
    
    protected TransformGroup transformGroup;
    protected Transform3D transformX;
    protected Transform3D transformY;
    protected Transform3D currXform;
    protected boolean reset = false;


    // true if this behavior is enable
    protected boolean enable = false;

    
  
    private MouseBehaviorCallback callback = null;
	private int x_last;
	private int y_last;
	
	public KeyMouseListener(Java3DViewer viewer, BranchGroup root, Bounds bounds) {
		super(viewer, KEY_LISTENER | MOUSE_LISTENER | MOUSE_WHEEL_LISTENER
				| MOUSE_MOTION_LISTENER);
		this.viewer = viewer;
		this.viewer.requestFocus();
		pickCanvas = new PickCanvas(viewer, root);
		pickCanvas.setMode(PickInfo.PICK_GEOMETRY);
		this.setSchedulingBounds(bounds);

		currXform = new Transform3D();
		transformX = new Transform3D();
		transformY = new Transform3D();
		reset = true;
	}


	@Override
	protected void integrateTransforms() {
	}
	
	
	public void setTolerance(float tolerance) {
		pickCanvas.setTolerance(tolerance);
	}
	

	public float getTolerance() {
		return pickCanvas.getTolerance();
	}
	

	@Override
	protected void processAWTEvents(AWTEvent[] events) {
//		motion = false;
		for (int i = 0; i < events.length; i++) {

			if (events[i] instanceof MouseEvent) {
				processMouseEvent((MouseEvent) events[i]);
//				motion = true;
			} else {
				processKeyEvent((KeyEvent) events[i]);
//				motion = true;
			}
		}
	}

	
	private void processKeyEvent(KeyEvent keyEvent) {
		if(keyEvent.isAltDown()){
			return;
		}
		if (keyEvent.getID() == KeyEvent.KEY_PRESSED || keyEvent.getID() == KeyEvent.KEY_RELEASED) {
			
			float angleStep = viewer.getAngleStep();
			switch(keyEvent.getKeyCode())
			{
				case KeyEvent.VK_RIGHT:viewer.rotateV(angleStep);break;
				case KeyEvent.VK_LEFT: viewer.rotateV(-angleStep); break;
				case KeyEvent.VK_UP: viewer.rotateH(-angleStep); break;
				case KeyEvent.VK_DOWN: viewer.rotateH(angleStep); break;
				case KeyEvent.VK_DELETE: viewer.delete();  break;
				default:  break;
			}
			
		}
	}
	

	private void processMouseEvent(MouseEvent mouseEvent) {
		
		
        if (mouseEvent.getID() == MouseEvent.MOUSE_PRESSED) {
        	doProcessMousePressed(mouseEvent);
        }
       
        if (mouseEvent.getID() == MouseEvent.MOUSE_WHEEL) {
        	doProcessZoom(mouseEvent);
        }
        if (mouseEvent.getID() == MouseEvent.MOUSE_DRAGGED) {
//        	System.err.println("MOUSE_DRAGGED");
//        	doProcessDrag(mouseEvent);
        }
	}
	
	
	private void doProcessDrag(MouseEvent event) {
		int xpos = event.getX();
		int ypos = event.getY();
//		System.err.println(x_last +  y_last);
//		int dx = x - x_last;
//		int dy = y - y_last;
//		
//		Point3d center = viewer.getCenter();
//		
//		center.x += dx * 0.02;
//		center.y -= dy * 0.02;
//		
//		viewer.setCenter(center);
//		
//		x_last = x;
//		y_last = y;
//		viewer.updateView();
	}


	void doProcessZoom(MouseEvent evt) {
		int units = 0;
			
		if ((evt.getID() == MouseEvent.MOUSE_WHEEL)) {
		    MouseWheelEvent wheelEvent = (MouseWheelEvent)evt;
		    if (wheelEvent.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
			    units = wheelEvent.getUnitsToScroll();
		    }

			if (!reset) {
				transformGroup.getTransform(currXform);

				translation.z = units * z_factor;

				transformX.set(translation);

				currXform.mul(transformX, currXform);

				transformGroup.setTransform(currXform);

				transformChanged(currXform);

				if (callback != null)
					callback.transformChanged(MouseBehaviorCallback.ZOOM,
							currXform);

			} else {
				reset = false;
			}
		 }
	    }
	
	
//	private void doProcessDragged(MouseEvent evt) {
//			int id;
//			int dx; 
//			int dy;
//
////			if (((buttonPress)&&((flags & MANUAL_WAKEUP) == 0)) ||
////			    ((wakeUp)&&((flags & MANUAL_WAKEUP) != 0))){
//			    id = evt.getID();
//			    if ((id == MouseEvent.MOUSE_WHEEL) &&
//				evt.isAltDown() && !evt.isMetaDown()){
//				
//				x = evt.getX();
//				y = evt.getY();
//				
//				dx = x - x_last;
//				dy = y - y_last;
//				
//				if (!reset){
//				    transformGroup.getTransform(currXform);
//				    
//				    translation.z  = dy*z_factor;
//				    
//				    transformX.set(translation);
//				    
////				    if (invert) {
////					currXform.mul(currXform, transformX);
////				    } else {
//					currXform.mul(transformX, currXform);
////				    }
//				    
//				    transformGroup.setTransform(currXform);
//				    
//				    transformChanged(currXform);
//				    
//				    if (callback!=null){
//				    	callback.transformChanged(MouseBehaviorCallback.ZOOM, currXform );
//				    }
//				    
//				}else {
//				    reset = false;
//				}
//				
//				x_last = x;
//				y_last = y;
//			 } else if (id == MouseEvent.MOUSE_PRESSED) {
//				x_last = evt.getX();
//				y_last = evt.getY();
//			  }
//		}
////		 }



	private void transformChanged(Transform3D currXform2) {
		
	}
	
	
	public Point3d getPosition(MouseEvent event) {
//		Point3d eyePos = new Point3d();
//		Point3d mousePos = new Point3d();
//		canvas.getCenterEyeInImagePlate(eyePos);
//		canvas.getPixelLocationInImagePlate(event.getX(),
//                       event.getY(), mousePos);
//		Transform3D transform = new Transform3D();
//		canvas.getImagePlateToVworld(transform);
//		transform.transform(eyePos);
//		transform.transform(mousePos);
//		Vector3d direction = new Vector3d(eyePos);
//		direction.sub(mousePos);
//		// three points on the plane
//		Point3d p1 = new Point3d(.5, -.5, .5);
//		Point3d p2 = new Point3d(.5, .5, .5);
//		Point3d p3 = new Point3d(-.5, .5, .5);
//		Transform3D currentTransform = new Transform3D();
//		box.getLocalToVworld(currentTransform);
//		currentTransform.transform(p1);
//		currentTransform.transform(p2);
//		currentTransform.transform(p3);		
//		Point3d intersection = getIntersection(eyePos, mousePos,
//                        p1, p2, p3);
//		currentTransform.invert();
//		currentTransform.transform(intersection);
		return null;		
	}

//	public Point3d getCanvasPtToVworldPt(MouseEvent event) {
//		Point3d eyePos = new Point3d();
//		Point3d mousePos = new Point3d();
//		canvas.getCenterEyeInImagePlate(eyePos);
//		canvas.getPixelLocationInImagePlate(event.getX(),
//                       event.getY(), mousePos);
//        // convert the canvas point to ImagePlate coords
//        getPixelLocationInImagePlate(x, y, VworldPt);
//        // transform the point from an imageplate coordinate to a Vworld
//        // coordinate
//        getImagePlateToVworld(imagePlateToVworld);
//        imagePlateToVworld.transform(mousePos);
//
//        getCenterEyeInImagePlate(centerEyePt);
//        imagePlateToVworld.transform(centerEyePt);
//        //Logging.trace(10, "Center eye pt VW " + centerEyePt);
//        //now compute the z=0 value  in the centereye to VworldPt pt
//        //centerEyePt_VworldPt = alpha *centerEyePt_planePt with
//planePt.z=0
//        double alpha = 0.0;
//        if ( VworldPt.z != centerEyePt.z ) {
//            alpha = centerEyePt.z /(VworldPt.z - centerEyePt.z);
//        }
//
//        Point3d planePt =
//            new Point3d(    centerEyePt.x - alpha *(VworldPt.x -
//centerEyePt.x),
//                            centerEyePt.y - alpha *(VworldPt.y -
//centerEyePt.y),
//                            0.0);
//    return planePt;
//    }


	private void doProcessMousePressed(MouseEvent mouseEvent) {
		int xpos = mouseEvent.getPoint().x;
        int ypos = mouseEvent.getPoint().y;
		
        if (!mouseEvent.isMetaDown() && !mouseEvent.isAltDown()){
			BranchGroup bg = null;
			pickCanvas.setFlags(PickInfo.NODE | PickInfo.SCENEGRAPHPATH | PickInfo.CLOSEST_INTERSECTION_POINT);
			pickCanvas.setShapeLocation(xpos, ypos);
			
		
			PickInfo pickInfo = pickCanvas.pickClosest();				
			if(pickInfo != null) {
				
				bg = (BranchGroup) pickCanvas.getNode(pickInfo, PickTool.TYPE_BRANCH_GROUP);
				
				if (bg != null) {
					if (bg.getUserData() != null) {
						Visible v = (Visible) bg.getUserData();
						v.select();							
					}						
				}
		    } else{
		    	System.err.println("doProcessMousePressed(MouseEvent mouseEvent)" + xpos + "----" + ypos);
		    }
		}
	}

	public void setTransformGroup(TransformGroup objTransform) {
		transformGroup = objTransform;
	}
	
	public void dispose() {

		this.setEnable(false);
		this.enableListeners(false);
	}
	
	public void setupCallback(MouseBehaviorCallback callback) {
	      this.callback = callback;
	 }


}
