package com.fem.cmd;

import static com.fem.ui.utils.consts.Consts.DRAW_LINE_MODE;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.fem.api.IFemEditor;

public class DrawLineCmd extends AbstractHandler {

	private static final String DRAW_LINE_IMG = "draw.png";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IWorkbenchPage page = window.getActivePage();
		IEditorPart activeEditor = page.getActiveEditor();
		
		if(activeEditor != null && activeEditor instanceof IFemEditor) {
			IFemEditor castedEditor = (IFemEditor) activeEditor;
			castedEditor.setFemEditorDrawMode(DRAW_LINE_IMG, DRAW_LINE_MODE);
		}
		return null;
	}
}
