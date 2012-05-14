package com.fem.ui.views.document.listener;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.management.ReflectionException;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IPositionUpdater;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import com.fem.api.IDrawModel;
import com.fem.ui.editors.FemEditor;
import com.fem.ui.views.FemView;
import com.fem.views.annotations.ErrorAnnotation;
import com.vem.views.utils.ReflectionUtils;
import com.vem.views.utils.TextUtils;

public class DocumentPositionUpdater implements IPositionUpdater {

	private final Map<String, String> femCommands;
	
	private static final String ENTER_STRING = "\r\n";

	private  AnnotationModel annotationModel;
	
	private Logger logger =  Logger.getLogger(DocumentPositionUpdater.class.getName());
	
	
	private  Map<Integer, Annotation> annotations = new HashMap<Integer, Annotation>();

	private StyledText st;
	
	public static final String ERROR_MESSAGE = "there is no such command as {0}";
	
	
	public DocumentPositionUpdater(Map<String, String> femCommands, AnnotationModel model, StyledText st) {
		this.femCommands = femCommands;
		this.annotationModel = model;
		this.st = st;
	}
	
	

	@Override
	public void update(DocumentEvent event) {
		
		String text = event.getText();
	    IDocument document = event.getDocument();
		
		int lineNumByOffset = TextUtils.getLineNumber(st, event.getOffset());
		try {
			int lineOffset = event.getDocument().getLineOffset(lineNumByOffset);
			
			if(event.getOffset() == lineOffset && text.trim().length() == 0){
    			removeErrorAnnotation(lineNumByOffset);
			}
			
			if (existSuchCommand(text)) {
				removeErrorAnnotation(lineNumByOffset);
			}
			String lastLineText = document.get(document.getLineOffset(lineNumByOffset), document.getLineLength(lineNumByOffset));
			
			if (ENTER_STRING.equals(text)) {
				String realLineText = lastLineText.replace(ENTER_STRING, "");

				if (!existSuchCommand(realLineText) && realLineText.trim().length() > 0) {
					String message = MessageFormat.format(ERROR_MESSAGE,
							realLineText);
					addErrorAnnotation(document, message, lineNumByOffset, realLineText);
					return;
				}
				
				invokeDrawModelCommand(lastLineText);
				
				
			}
		
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
		
	}
	
    private void invokeDrawModelCommand(String lastLineText) {
    	if(lastLineText.indexOf('(')!= -1){
			String methodName = lastLineText.substring(0, lastLineText.indexOf('('));
			
			String paramString = lastLineText.substring(lastLineText.indexOf('(') + 1, lastLineText.indexOf(')'));
			
			String[] params = null;
			
			if(paramString.indexOf(',')!= -1){
				params = paramString.split(",");
			}else{
				params = new String[]{paramString};
			}
			
			
			logger.info(params.length + "");
			
			
//			IWorkbenchPage page = HandlerUtil.getActiveWorkbenchWindow(new DocumentEvent()).getActivePage();
			
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			IEditorPart activeEditor = page.getActiveEditor();
			if(activeEditor != null && activeEditor instanceof FemEditor) {
				IDrawModel model = (IDrawModel) activeEditor.getEditorInput().getAdapter(IDrawModel.class);
				
				Class[] classesParam = new Class[0];
				if(params != null && params.length > 0 && params[0].trim().length() > 0) {
					classesParam = new Class[params == null ? 0 : params.length];
					
					for (int i = 0; i < params.length; i++) {
						classesParam[i] = double.class;
			    	}
				}
				
				Method method = ReflectionUtils.findMethod(model.getClass(), methodName, classesParam);
				
				ReflectionUtils.invokeMethod(method, model, toDoubleArray(params));
				
				model.notifyAllObservers();
				
			}
			
    	}
			
//			Class<?>[] types = new Class[] {value.getClass()};
//			try {
//				Method method = obj.getClass().getMethod(mname, types);
//				method.invoke(obj, new Object[] { value });
//			} catch (Exception e) {
//				logger.error("Unable to invoke method " + methodName + " in class " + obj.getClass().getName(), e);
//			}
	}


    private Object[] toDoubleArray(String[] args) {
    	
    	if(args == null || args[0].trim().length() == 0) {
    		return new Object[0];
    	}
    	
    	final Object[] result = new Object[args.length];
    	for (int i = 0; i < args.length; i++) {
    		result[i] = Double.valueOf(args[i]);
    	}
    	
    	return result;
    }

	private boolean existSuchCommand(String command) {
    	for (String cmd : femCommands.keySet()) {
			if(command.equals(cmd) || (command.indexOf('(') > -1 && cmd.startsWith(command.substring(0, command.indexOf('('))))){
				return true;
			}
		}
		return false;
	}

	public void addErrorAnnotation(IDocument document, String message ,int lineNumber, String lineText) throws BadLocationException {
		  ErrorAnnotation errorAnnotation = new ErrorAnnotation((lineNumber + 1), message, FemView.ERROR_TYPE);
          int lineLength = lineText.length();
		  annotationModel.addAnnotation(errorAnnotation, new Position(document.getLineOffset(lineNumber), lineLength));
		  annotations.put(lineNumber, errorAnnotation);
    }
	
	public void removeErrorAnnotation(int lineNumber) throws BadLocationException {
		Annotation annotation = annotations.get(lineNumber);
		if(annotation != null){
			annotationModel.removeAnnotation(annotation);
		}
		
	}


	public Map<String, String> getFemCommands() {
		return femCommands;
	}

}
