package com.fem.ui.editors.cmd;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import com.fem.api.IDrawModel;
import com.fem.api.IFemEditor;
import com.fem.api.IFemView;
import com.fem.ui.editor.model.Java3DViewerTester;
import com.fem.ui.editors.DummyEditorInput;
import com.fem.ui.editors.FemEditor;

public class NewEditorCmd extends AbstractHandler  {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// Get the view
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IWorkbenchPage page = window.getActivePage();
		
//		        IDrawModel model = new DrawModelImpl();
		        
		        IDrawModel model = Java3DViewerTester.drawWall2();
		        
				DummyEditorInput input = new DummyEditorInput(model);
				try {
					
					
					IFemEditor editor = (IFemEditor)page.openEditor(input, FemEditor.ID);
					
					model.addObserver(editor);
//					model.notifyAllObservers();
					
					
					
					IViewReference[] viewReferences = page.getViewReferences();
					
					for (IViewReference viewReference : viewReferences) {
						
						IViewPart viewPart = viewReference.getView(true);
						if(!(viewPart instanceof IFemView)){
							continue;
						}
						
						IFemView view = (IFemView) viewPart;
						model.addObserver(view);
						model.notifyAllObservers();
					}
					
				} catch (PartInitException e) {
					throw new RuntimeException(e);
				}
		return null;
	}
	
	
	

	

}
