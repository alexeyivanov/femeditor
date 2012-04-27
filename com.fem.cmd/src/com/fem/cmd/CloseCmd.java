package com.fem.cmd;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

public class CloseCmd extends AbstractHandler {


	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		//
		IWorkbenchWindow window = HandlerUtil
				.getActiveWorkbenchWindowChecked(event);
		IEditorPart part = HandlerUtil.getActiveEditorChecked(event);
		window.getActivePage().closeEditor(part, true);
		return null;
	}
	

}
