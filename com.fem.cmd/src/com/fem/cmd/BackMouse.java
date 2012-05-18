package com.fem.cmd;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import static com.fem.ui.utils.consts.Consts.DRAW_DEFAULT_MODE;

import com.fem.api.IFemEditor;

public class BackMouse extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IWorkbenchPage page = window.getActivePage();
		IEditorPart activeEditor = page.getActiveEditor();
		
		if(activeEditor != null && activeEditor instanceof IFemEditor) {
			IFemEditor castedEditor = (IFemEditor) activeEditor;
			castedEditor.setFemEditorDrawModeDefault(DRAW_DEFAULT_MODE);
		}
		
		return null;
	}
}
