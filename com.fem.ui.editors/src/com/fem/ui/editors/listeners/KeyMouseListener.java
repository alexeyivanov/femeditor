package com.fem.ui.editors.listeners;

import static com.fem.ui.utils.consts.Consts.DRAW_DEFAULT_MODE;
import static com.fem.ui.utils.consts.Consts.DRAW_LINE_MODE;

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

public class KeyMouseListener extends KeyMouseBehavior{
	
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
		super(viewer);
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
		for (int i = 0; i < events.length; i++) {

			if (events[i] instanceof MouseEvent) {
				processMouseEvent((MouseEvent) events[i]);
			} else {
				processKeyEvent((KeyEvent) events[i]);
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
        Point3d eyePos = new Point3d();
		Point3d mousePos = new Point3d();
		canvas.getCenterEyeInImagePlate(eyePos);
		canvas.getPixelLocationInImagePlate(event.getX(),
                       event.getY(), mousePos);
		Transform3D transform = new Transform3D();
		canvas.getImagePlateToVworld(transform);
		transform.transform(eyePos);
		transform.transform(mousePos);
		
		
		
//		 Point3d cursor = new Point3d();
//	        canvas.getPixelLocationInImagePlate( event.getX(), event.getY(), cursor );
//	        Transform3D imagePlateToVworld = new Transform3D();
//	        canvas.getImagePlateToVworld( imagePlateToVworld );
//	        Transform3D vWorldToImagePlate = new Transform3D();
//	        vWorldToImagePlate = new Transform3D( imagePlateToVworld );
//	        vWorldToImagePlate.invert();
//	        
////	        cursor.sub( previousCursorIP);
//	        
////	        Point3d loc = new Point3d();
////	        pickPoint.getPosition( loc );
//	        
//	        //System.out.println("Point "+loc);
////	        vWorldToImagePlate.transform( loc );
//	        System.out.println("Point in IP "+cursor );
//	        
//	        Point3d eye = new Point3d();
//	        canvas.getCenterEyeInImagePlate( eye );
	        //System.out.println("Eye in IP "+eye );
		
		
		
//		Vector3d direction = new Vector3d(eyePos);
//		direction.sub(mousePos);
//		Transform3D transform = new Transform3D();
//		canvas.getImagePlateToVworld(transform);
//		transform.transform(eyePos);
//		transform.transform(mousePos);
//		Vector3d direction = new Vector3d(eyePos);
//		direction.sub(mousePos);
        
        
        
		return mousePos;		
	}



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
		    	System.err.println(pickCanvas.getStartPosition());
		    	if(DRAW_DEFAULT_MODE.equals(viewer.getDrawMode())){
		    		return;
		    	}
		    	
		    	Point3d position = getPosition(mouseEvent);
		    	
		    	if(DRAW_LINE_MODE.equals(viewer.getDrawMode())){
		    		
//		    		Point3d eyePosn = new Point3d();
//		    		Point3d mousePosn = new Point3d();
//		    		
//		    		canvas.getCenterEyeInImagePlate(eyePosn);
//		    		canvas.getPixelLocationInImagePlate(xpos,ypos,mousePosn);
//		    		
//		    		System.err.println(mousePosn);
		    		
		    		viewer.drawLine(position);
		    	}
		    }
		}
	}

	public void setTransformGroup(TransformGroup objTransform) {
		transformGroup = objTransform;
	}
	
	public void dispose() {

		this.setEnable(false);
	}
	
	public void setupCallback(MouseBehaviorCallback callback) {
	      this.callback = callback;
	 }


}
