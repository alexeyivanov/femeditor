package com.fem.cmd;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.fem.api.IDrawModel;
import com.fem.api.IFemEditor;
import com.fem.api.describer.IFEMModelDescriber;
import com.fem.api.utils.ExtensionPointDescriberHelper;
import com.fem.ui.editor.model.DrawModelImpl;
import com.fem.ui.editor.model.GeometryShapeManagerOCC;
import com.fem.ui.editor.model.VisualShapeManagerImpl;
import com.fem.ui.editors.DummyEditorInput;

public class OpenFileCmd extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();

		FileDialog dialog = new FileDialog(shell, SWT.OPEN);
//		dialog.setFilterExtensions(new String[] { "*.brep", "*.*" });
//		dialog.setFilterNames(new String[] { "Text File", "All Files" });
		dialog.setFilterExtensions(new String[] { "*.brep"});
		dialog.setFilterNames(new String[] { "*.brep" });
		String fileSelected = dialog.open();

		if (fileSelected == null) {
			return null;
		}
		
		System.out.println("Selected file: " + fileSelected);
		
		IDrawModel model = new DrawModelImpl();
		model.setGeometryShapeManager(new GeometryShapeManagerOCC());
		model.setDrawShapeModelManager(new VisualShapeManagerImpl());
		
		model.load(fileSelected);
		
//		IDrawModel model = new DrawModelImpl();
		
	
		
		String fileExtention = "brep";
		
		List<IFEMModelDescriber> describers = null;
		
		try {
			describers = ExtensionPointDescriberHelper.getAvailableDescribersFromExtentionPoint();
		
		if(describers == null || describers.isEmpty()){
//			TODO added log
			 return null;
		}
		 for (IFEMModelDescriber describer : describers) {
				String editorId = describer.getPreferredEditorID(fileExtention);
	            //For now, just grab the first that comes.
	            //TODO: implement some sort of hierarchy here if multiple matches
	            if (editorId != null) {
	                IEditorDescriptor editor
	                    = PlatformUI.getWorkbench().getEditorRegistry()
	                .findEditor(editorId);
	                if (editor != null) {
	                    open(fileExtention, editor.getId(), model);
	                    return null;
	                }
	            }
	        }
		 
		} catch (Exception e) {
//			e.printStackTrace();
//			TODO added log
		}
		return null;
	}

	private void open(String fileExtention, String editorID, IDrawModel model) {
		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		DummyEditorInput input = new DummyEditorInput(model);

		// open Editor with given editor ID

		try {
			IFemEditor openEditor = (IFemEditor) page.openEditor(input, editorID);
			model.addObserver(openEditor);
			model.notifyAllObservers();
			
		} catch (PartInitException e) {
			e.printStackTrace();
		}

	}

}
