package com.fem.cmd;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.fem.api.IDrawModel;

public class MeshCmd extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IWorkbenchPage page = window.getActivePage();
		IEditorPart activeEditor = page.getActiveEditor();
		IDrawModel model = (IDrawModel) activeEditor.getEditorInput().getAdapter(IDrawModel.class);
		
		model.setMeshSize(0.1);
		model.meshAll();
		
		model.notifyAllObservers();
		return null;
	}
}
