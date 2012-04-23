package com.fem.cmd;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import com.fem.api.IDrawModel;

public class SaveAsCmd extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		    Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();	
		    FileDialog dialog = new FileDialog(shell, SWT.SAVE);
//	        dialog.setFilterExtensions(new String[] {"*.txt", "*.*"});
//	        dialog.setFilterNames(new String[] {"Text File", "All Files"});
	        dialog.setFilterExtensions(new String[] { "*.brep"});
			dialog.setFilterNames(new String[] { "*.brep" });
	        String fileSelected = dialog.open();

	        if (fileSelected != null) {
	           // Perform Action, like save the file.
	             System.out.println("Selected file: " + fileSelected);
	        }
	        
	        IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
			IWorkbenchPage page = window.getActivePage();
		
	        IEditorPart activeEditor = page.getActiveEditor();
	        IDrawModel model = (IDrawModel) activeEditor.getEditorInput().getAdapter(IDrawModel.class);
	        model.save(fileSelected);	
	        
		
		return null;
	}

}
