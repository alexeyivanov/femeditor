package com.fem.views.annotations;

import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.swt.widgets.Shell;

public class AnnotationConfiguration implements IInformationControlCreator {

	@Override
	public IInformationControl createInformationControl(Shell parent) {
		return new DefaultInformationControl(parent); 
	}
	

}
