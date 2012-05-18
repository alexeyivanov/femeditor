package com.fem.ui.editor.model;

import java.awt.GraphicsConfigTemplate;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.Window;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.GraphicsConfigTemplate3D;
import javax.media.j3d.Node;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.fem.api.IDrawModel;
import com.fem.api.VisualShape;
import com.fem.ui.editors.listeners.KeyMouseListener;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Java3DViewer extends Canvas3D {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SimpleUniverse universe = null;
	private BranchGroup root = null;
	private BranchGroup	view = null;
	private TransformGroup rotation;
	private BoundingSphere bounds;
	private float angleV = 0; 
	private float angleH = 0; 
	private float angleStep = (float)Math.PI / 20;
	private float lastAngleV = 0, lastAngleH = 0;
	private Point3d center = new Point3d(0,0,0);	
	private float scale = 1;
	private IDrawModel drawModel;
	private List<TransformGroup> textLabels = new ArrayList<TransformGroup>();
	
	private String drawMode;
	
	private Logger logger =  Logger.getLogger(Java3DViewer.class.getName());
	
	
	
	    


//	private class KeyListener extends KeyAdapter
//	{
//		
//		@Override
//		public void keyPressed(KeyEvent e)
//		{
//			boolean found=true;
//			switch(e.getKeyCode())
//			{
//				case KeyEvent.VK_RIGHT:
//					rotateV(getAngleStep());
//					break;
//				case KeyEvent.VK_LEFT: rotateV(-getAngleStep()); break;
//				case KeyEvent.VK_UP: rotateH(-getAngleStep()); break;
//				case KeyEvent.VK_DOWN: rotateH(getAngleStep()); break;
//				case KeyEvent.VK_DELETE: drawModel.deleteSelected(); draw(drawModel); break;
//				default: found=false;
//			}
//			if(found)
//			{
//			
//			}
//		}
//				
//	}
	
	protected void setDirty(boolean isDirty) {
		
	}
	
	public void updateView() {
		Transform3D rotateXY = new Transform3D();
	
	
		Transform3D rotateY = new Transform3D();
		rotateXY.rotX(angleH);
		rotateY.rotY(angleV);
		rotateXY.mul(rotateY);
		rotateXY.setScale(scale);
		
		Vector3d trans = new Vector3d(-center.x,-center.y,-center.z);
		Transform3D translation = new Transform3D();
		translation.set(trans);
	    rotateXY.mul(translation);	    
		
		rotation.setTransform(rotateXY);
		for (TransformGroup tg: textLabels) {
			Transform3D t = new Transform3D();
			tg.getTransform(t);			
			Transform3D rotX = new Transform3D();
			Transform3D rotY = new Transform3D();
						
			rotY.rotY(lastAngleV-angleV);
			rotX.rotX(lastAngleH-angleH);
			t.mul(rotX);			
			t.mul(rotY);			
			    					
			tg.setTransform(t);
		}
		lastAngleH = angleH;
		lastAngleV = angleV;
	}
	
	public void rotateV(float angle) {
		angleV += angle;
		updateView();		
	}
	
	public void rotateH(float angle) {
		angleH += angle;
		updateView();		
	}
	
	public Java3DViewer(Window window, IDrawModel drawModel) {
		
		super(getPreferredConfiguration(window));
		universe = new SimpleUniverse(this);		
		universe.getViewingPlatform().setNominalViewingTransform();
		root = new BranchGroup();
		root.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		root.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);	
		// Let Java 3D perform optimizations on this scene graph.
		root.compile();
		createSceneGraph(drawModel);
		universe.addBranchGraph(root);
		
	}
	
	public void createSceneGraph(IDrawModel drawing) {
		// Branch group which can be deattached for changing scene;
		view = new BranchGroup();			
		view.setCapability(BranchGroup.ALLOW_DETACH);
		//BranchGroup for rotation	
		rotation = new TransformGroup();
    	rotation.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    	rotation.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);    		
    	view.addChild(rotation);    			
		bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), Double.POSITIVE_INFINITY);		
//		bounds = new BoundingSphere();		
		DirectionalLight lightD = new DirectionalLight();
        lightD.setDirection(new Vector3f(0.0f,0.0f,-0.7f));
        lightD.setInfluencingBounds(bounds);
        view.addChild(lightD);


        AmbientLight lightA = new AmbientLight();
        lightA.setInfluencingBounds(bounds);
        view.addChild(lightA);
		
		Background background = new Background();
//		background.setColor(1.0f, 0f, 0f);
		background.setColor(1.0f, 1.0f, 1.0f);
		background.setApplicationBounds(bounds);
		view.addChild(background);
        		//		
//		PickMouse pick = new PickMouse(this, view, bounds);
//		view.addChild(pick);        
//		
//		addKeyListener(new KeyListener());
		
//		    KeyNavigatorBehavior keyNavBeh = new KeyNavigatorBehavior(universe.getViewingPlatform().getViewPlatformTransform());
//	        keyNavBeh.setSchedulingBounds(bounds);
////	        keyNavBeh.s
//	        view.addChild(keyNavBeh);
		
		
	        KeyMouseListener keyNavBeh = new KeyMouseListener(this, view, bounds);
	        keyNavBeh.setTransformGroup(rotation);
//	        keyNavBeh.setViewingPlatform(universe.getViewingPlatform());
	        view.addChild(keyNavBeh);
		
		angleV = (float)Math.PI/4;
		angleH = (float)Math.PI/5;
		
		draw(drawing);
//		zoomAll();
	}
	
	public void draw(IDrawModel model) {
    	view.detach();
    	rotation.removeAllChildren();    	
    	drawModel = model;
    	if (drawModel.getPicture() == null){
    		logger.info("Warning!Picture is null");
    		return;
    	}
    	Integer facesEdgesCounter = 0;
    	for (VisualShape visualShape : drawModel.getPicture()) {
    		TransformGroup shapeGroup = new TransformGroup();
    		rotation.addChild(shapeGroup);
    		BranchGroup faces = visualShape.getFaces(BranchGroup.class);
    		BranchGroup edges = visualShape.getEdges(BranchGroup.class);    		    		
    		BranchGroup text = visualShape.getText(BranchGroup.class);
    		if (faces == null && edges == null){
    			continue;
    		}else{
    			facesEdgesCounter++;
    		}
    		if (faces != null) {    			
    			faces.detach();
    			rotation.addChild(faces);
    			faces.setPickable(true);
        		faces.setUserData(visualShape);
        		faces.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
        		faces.setName(facesEdgesCounter.toString());
   
    		}
    		if (edges != null) {
    			edges.detach();
    			rotation.addChild(edges);
    			edges.setPickable(true);
        		edges.setUserData(visualShape);
        		edges.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
         		edges.setName(facesEdgesCounter.toString());
    		}
    		
    		if (text != null) {
    			text.detach();
    			rotation.addChild(text);
    			text.setPickable(false);
    			if (text instanceof BranchGroup) {
    				BranchGroup bg = (BranchGroup)text;
    				for (int j = 0; j < bg.numChildren(); j++) {
    					Node child = bg.getChild(j);
    					if (child instanceof TransformGroup) {
    						TransformGroup tg = (TransformGroup)child;
    						tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    						tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
    						textLabels.add(tg);    						
    					}
    				}
    			}
    			 
    			
//        		edges.setUserData(v);
//        		edges.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
//         		edges.setName(i.toString());
    		}    		
    	} 
    	root.addChild(view);
    }
	
	
	 /**
     * From https://java3d.dev.java.net/issues/show_bug.cgi?id=89
     * Finds the preferred <code>GraphicsConfiguration</code> object
     * for the system.  This object can then be used to create the
     * Canvas3D object for this system.
     * @param window the window in which the Canvas3D will reside 
     *
     * @return The best <code>GraphicsConfiguration</code> object for
     *  the system.
     */
    private static GraphicsConfiguration getPreferredConfiguration(Window window)
    {
    	if(window==null)
    		return SimpleUniverse.getPreferredConfiguration();
    	GraphicsDevice device = window.getGraphicsConfiguration().getDevice();
        GraphicsConfigTemplate3D template = new GraphicsConfigTemplate3D();
        String stereo;

        // Check if the user has set the Java 3D stereo option.
        // Getting the system properties causes appletviewer to fail with a
        //  security exception without a try/catch.

        stereo = (String) java.security.AccessController.doPrivileged(
           new java.security.PrivilegedAction() {
           public Object run() {
               return System.getProperty("j3d.stereo");
           }
        });

        // update template based on properties.
        if (stereo != null) {
            if (stereo.equals("REQUIRED"))
                template.setStereo(GraphicsConfigTemplate.REQUIRED);
            else if (stereo.equals("PREFERRED"))
                template.setStereo(GraphicsConfigTemplate.PREFERRED);
        }
        // Return the GraphicsConfiguration that best fits our needs.
        return device.getBestConfiguration(template);
    }
    
    

    
    private Bounds getBounds(VisualShape v) {
    	Bounds b = null;
    	if (v == null) return null;
		if (v.getFaces(BranchGroup.class) != null) b = v.getFaces(BranchGroup.class).getBounds();
		if (v.getEdges(BranchGroup.class) != null) b.combine(v.getEdges(BranchGroup.class).getBounds());
		return b;
    }
    
    public void zoomAll() {
    	bounds = new BoundingSphere();
    	boolean first = true;
    	for (VisualShape v : drawModel.getPicture()) {
    		Bounds b = getBounds(v);
    		if (first) {
    			bounds.set(b);
    		}else{
    			bounds.combine(getBounds(v));
    		}
    		first = false;
    	} 
    	bounds.getCenter(center);
    	scale = 1.0f/1.3f/(float)bounds.getRadius();
    	updateView();
    }
//    
    public static void viewAWT(IDrawModel d) {
//		JFrame frame=new JFrame();
//		frame.setBounds(0, 0, 500, 400);
////		Viewer viewer=new Viewer(frame, d);		
//		frame.getContentPane().add(viewer);
//		frame.setVisible(true);
//		frame.addWindowListener(new WindowAdapter(){
//			public void windowClosing(WindowEvent we){
//				System.exit(0);
//			}
//		});
	}
    
//    public static void view(ArrayList<Element> elements) {
//    	Mesh m = new Mesh(elements);
//    	Drawing d = new Drawing();		
//		d.addMesh(m);
//		Viewer.view(d);
//    }
//    
//    public static void view(ArrayList<Element> elements, Identificator resultId) {
//    	Mesh m = new Mesh(elements, resultId);
//    	m.assemble();
//    	Drawing d = new Drawing();		
//		d.addMesh(m);
//		view(d);
//    }
    
    public static void view(Mesh mesh) {    	
//    	Drawing d = new Drawing();		
//		d.addMesh(mesh);
//		view(d);
    }
//    
//    public static void view(Drawing d)
//	{
//		final Display display = new Display();
//		final Shell shell = new Shell(display);
//		shell.setLayout(new FillLayout());
//		
//		Composite composite = new Composite(shell, SWT.EMBEDDED | SWT.NO_BACKGROUND);
//	    Frame baseFrame = SWT_AWT.new_Frame(composite);
//	    
//		baseFrame.setBounds(0, 0, 800, 600);
//
//		Viewer viewer=new Viewer(baseFrame, d);
//		baseFrame.add(viewer);
//	    
//		shell.open();
//		while (!shell.isDisposed()) {
//			if (!display.readAndDispatch()) display.sleep();
//		}
//		
//		baseFrame.dispose();
//		shell.dispose();
//		composite.dispose();
//		display.dispose();
//		System.exit(0);
//		
//	}
		
//    public static void view(IDrawModel d)
//	{
//		final Display display = new Display();
//		final Shell shell = new Shell(display);
//		shell.setLayout(new FillLayout());
//		
//		Composite composite = new Composite(shell, SWT.EMBEDDED | SWT.NO_BACKGROUND);
//	    Frame baseFrame = SWT_AWT.new_Frame(composite);
//	    
//		baseFrame.setBounds(0, 0, 800, 600);
//
//		Java3DViewer viewer = new Java3DViewer(baseFrame, d);
//		baseFrame.add(viewer);
//	    
//		shell.open();
//		
////		d.setFaceColor(Color.GREEN);
////		d.circle(0.9);
////		viewer.draw(d);
//		
//		while (!shell.isDisposed()) {
//			if (!display.readAndDispatch()) display.sleep();
//		}
//		
//		baseFrame.dispose();
//		shell.dispose();
//		composite.dispose();
//		display.dispose();
//		System.exit(0);
//		
//	}


	public float getAngleStep() {
		return angleStep;
	}

	public void setAngleStep(float angleStep) {
		this.angleStep = angleStep;
	}

	public void delete() {
		drawModel.deleteSelected(); 
		draw(drawModel);
	}

	public Point3d getCenter() {
		return center;
	}

	public void setCenter(Point3d center) {
		this.center = center;
	}

	public void drawLine(Point3d position) {
		drawModel.setLineWidth(0.6f);
		drawModel.lineTo(position.x, position.y, position.z);
		drawModel.notifyAllObservers();
	}

	public String getDrawMode() {
		return drawMode;
	}

	public void setDrawMode(String drawMode) {
		this.drawMode = drawMode;
	}

}
