package com.fem.ui.editors;

import java.awt.Color;
import java.awt.Frame;
import java.util.Observable;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import com.fem.api.IDrawModel;
import com.fem.api.IFemEditor;
import com.fem.ui.editor.model.Java3DViewer;

public class FemEditor extends EditorPart implements IFemEditor, ISelectionProvider, ISelectionListener {
	
	
	public static final String ID = "com.fem.main.femEditor";
	
//	private DummyEditorInput input;
	
	private Composite topComposite;
//	
//	private SimpleUniverse universe;
//
	private Frame frameAWT;
	
	private Java3DViewer viewer;
	
	private Text text;

	

	@Override
	public void doSave(IProgressMonitor monitor) {
		
	}

	@Override
	public void doSaveAs() {
		
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		if (!(input instanceof DummyEditorInput)) {
			throw new RuntimeException("Wrong input");
		}
		setSite(site);
		setInput(input);
		
		IDrawModel model = (IDrawModel) input.getAdapter(IDrawModel.class);
		
		if(model!=null) {
//			setPartName(model.getResource().getName());
			setPartName("Fem Editor");
		}else{
			setPartName("UNNAMED");
		}

		
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		
//		text = new Text(parent, SWT.BORDER);
		
//		get draw model
		IEditorInput input = getEditorInput();
		
		IDrawModel model = (IDrawModel) input.getAdapter(IDrawModel.class);
		
		
		
		topComposite = new Composite(parent, SWT.EMBEDDED | SWT.NO_BACKGROUND);

		topComposite.setParent(parent.getParent());
		parent.dispose();
		parent = null;
		frameAWT = SWT_AWT.new_Frame(topComposite);
		
		
//		frameAWT.setBounds(0, 0, 800, 600);

//		model.setFaceColor(Color.GREEN);
//		model.circle(0.9);
		
		
	    viewer = new Java3DViewer(frameAWT, model);
		frameAWT.add(viewer);
	    
		
		
		
		
		
//		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
//		Canvas3D canvas = new Canvas3D(config);
////		SimpleUniverse universe = new SimpleUniverse(canvas);
//		universe = new SimpleUniverse(canvas);
//		
//		ColorCube cube  = new ColorCube();
//		Transform3D trans = new Transform3D();
//		trans.rotX(Math.PI / 4.0d);
//		trans.rotY(Math.PI / 4.0d);
//		trans.setTranslation(new Vector3d(0, 0, -10));
//		
//		TransformGroup group = new TransformGroup(trans);
////		TransformGroup group = new TransformGroup(trans);
//		group.addChild(cube);
////		branchGroup = new BranchGroup();
//		BranchGroup branchGroup = new BranchGroup();
//		branchGroup.addChild(group);
//		universe.getViewingPlatform().setNominalViewingTransform();
//		universe.addBranchGraph(branchGroup);
//		frameAWT.add(canvas, BorderLayout.CENTER);
		
		
		
		
		
		
//		topComposite = new Composite(parent, SWT.EMBEDDED | SWT.NO_BACKGROUND);
		
//		topComposite.setParent(parent.getParent());
//		parent.dispose();
//		parent = null;
//		frameAWT = SWT_AWT.new_Frame(topComposite);
//		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
//		Canvas3D canvas = new Canvas3D(config);
////		SimpleUniverse universe = new SimpleUniverse(canvas);
//		universe = new SimpleUniverse(canvas);
//		
//		ColorCube cube  = new ColorCube();
//		Transform3D trans = new Transform3D();
//		trans.rotX(Math.PI / 4.0d);
//		trans.rotY(Math.PI / 4.0d);
//		trans.setTranslation(new Vector3d(0, 0, -10));
//		
//		TransformGroup group = new TransformGroup(trans);
////		TransformGroup group = new TransformGroup(trans);
//		group.addChild(cube);
////		branchGroup = new BranchGroup();
//		BranchGroup branchGroup = new BranchGroup();
//		branchGroup.addChild(group);
//		universe.getViewingPlatform().setNominalViewingTransform();
//		universe.addBranchGraph(branchGroup);
//		frameAWT.add(canvas, BorderLayout.CENTER);
		
		
		getSite().setSelectionProvider(this);
		getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(this);

	}

	@Override
	public void setFocus() {
	}

//	@Override
//	public void drawElement() {
//		    ColorCube cube  = new ColorCube(0.4f);
//		    BranchGroup branchGroup = new BranchGroup();
//		    branchGroup.addChild(cube);
//		    universe.addBranchGraph(branchGroup);
//			
//	}

	
	@Override
	public void dispose() {
		// important: We need do unregister our listener when the view is disposed
		getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener(this);
//		super.dispose();
	}


	@Override
	public void update(Observable o, Object arg) {
		IDrawModel model =(IDrawModel)o;
		String updatedString = "update() called"
		        + model.getModelInfo();
//		text.setText(updatedString);
	}

	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		
	}

	@Override
	public ISelection getSelection() {
		return new StructuredSelection(new Object());
	}

	@Override
	public void removeSelectionChangedListener(
			ISelectionChangedListener listener) {
	}

	@Override
	public void setSelection(ISelection selection) {
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (part == FemEditor.this) {
			// changed model
			IDrawModel model = (IDrawModel) getEditorInput().getAdapter(IDrawModel.class);
			if(model != null){
		       model.notifyAllObservers();
			}
	   }
	}

}