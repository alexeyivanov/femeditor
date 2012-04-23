package com.fem.cmd;

import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import com.fem.api.IDrawModel;
import com.fem.api.IFemView;

public class ShowViewCmd extends AbstractHandler {

	private static final String VIEWS_SHOW_VIEW_PARM_ID = "com.fem.cmd.viewId";
	private static final String VIEWS_SHOW_VIEW_SECONDARY_ID = "com.fem.cmd.secondaryId";
	private static final String VIEWS_SHOW_VIEW_PARM_FASTVIEW = "com.fem.cmd.fastView";
	
	
	private boolean makeFast = false;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil
				.getActiveWorkbenchWindowChecked(event);
		// Get the view identifier, if any.
		final Map parameters = event.getParameters();
		final Object viewId = parameters.get(VIEWS_SHOW_VIEW_PARM_ID);
		final Object secondary = parameters.get(VIEWS_SHOW_VIEW_SECONDARY_ID);
		makeFast  = "true".equals(parameters.get(VIEWS_SHOW_VIEW_PARM_FASTVIEW)); //$NON-NLS-1$
		
		if (viewId == null) {
			openOther(window);
		} else {
				try {
					openView((String) viewId, (String) secondary, window);
				} catch (PartInitException e) {
					 throw new ExecutionException("Fem View could not be initialized", e); 
				}
		}

		return null;
	}

	private void openView(String viewId, String secondaryId, IWorkbenchWindow activeWorkbenchWindow) throws PartInitException {
		final IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
		if (activePage == null) {
			return;
		}

        if (makeFast) {

        } else {
        	IFemView view = (IFemView) activePage.showView(viewId, secondaryId, IWorkbenchPage.VIEW_ACTIVATE);
        	
        	IEditorReference[] editorReferences = activePage.getEditorReferences();
    		
    		for (IEditorReference editorReference : editorReferences) {
    			IEditorPart editor = editorReference.getEditor(false);
    			IDrawModel model = (IDrawModel) editor.getEditorInput().getAdapter(IDrawModel.class);
    			if(model!=null){
    				model.addObserver(view);
    				model.notifyAllObservers();
    			}
    		}
        	
        }
	}

	
	/*
	 * Open Show View Dialog
	 */
	private void openOther(IWorkbenchWindow window) {
//		final IWorkbenchPage page = window.getActivePage();
//		if (page == null) {
//			return;
//		}
//		
//		final ShowViewDialog dialog = new ShowViewDialog(window,
//				WorkbenchPlugin.getDefault().getViewRegistry());
//		dialog.open();
//		
//		if (dialog.getReturnCode() == Window.CANCEL) {
//			return;
//		}
//		
//		final IViewDescriptor[] descriptors = dialog.getSelection();
//		for (int i = 0; i < descriptors.length; ++i) {
//			try {
//				openView(descriptors[i].getId(), null, window);
//			} catch (Exception e) {
////				StatusUtil.handleStatus(e.getStatus(),
////						WorkbenchMessages.ShowView_errorTitle
////								+ ": " + e.getMessage(), //$NON-NLS-1$
////						StatusManager.SHOW);
//			}
//		}
	}

}
