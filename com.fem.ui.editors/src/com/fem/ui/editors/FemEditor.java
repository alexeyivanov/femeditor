package com.fem.ui.editors;

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
	
	
	private Composite topComposite;

	private Frame frameAWT;
	
	private Java3DViewer viewer;
	

	

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
		
		
//		get draw model
		IEditorInput input = getEditorInput();
		
		IDrawModel model = (IDrawModel) input.getAdapter(IDrawModel.class);
		
		
		
		topComposite = new Composite(parent, SWT.EMBEDDED | SWT.NO_BACKGROUND);

		topComposite.setParent(parent.getParent());
		parent.dispose();
		parent = null;
		frameAWT = SWT_AWT.new_Frame(topComposite);
		
//		frameAWT.setBounds(0, 0, 800, 600);
//
//		model.setFaceColor(Color.GREEN);
//		model.circle(0.9);
		
		
	    viewer = new Java3DViewer(frameAWT, model);
		frameAWT.add(viewer);
		
		getSite().setSelectionProvider(this);
		getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(this);

	}

	@Override
	public void setFocus() {
	}

	
	@Override
	public void dispose() {
		// important: We need do unregister our listener when the view is disposed
		getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener(this);
		super.dispose();
	}


	@Override
	public void update(Observable o, Object arg) {
		IDrawModel model =(IDrawModel)o;
		if(model != null){
			viewer.draw(model);
		}
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
