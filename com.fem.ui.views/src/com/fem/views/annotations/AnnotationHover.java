package com.fem.views.annotations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;

public class AnnotationHover implements IAnnotationHover, ITextHover {
	
	
	
	private IAnnotationModel annotationModel;

	public AnnotationHover(IAnnotationModel annotationModel) {
		this.annotationModel = annotationModel;
	}
	

	@Override
	public String getHoverInfo(ISourceViewer sourceViewer, int lineNumber) {
		Iterator ite = annotationModel.getAnnotationIterator();

        List<String> all = new ArrayList<>();

        while (ite.hasNext()) {
            Annotation annotation = (Annotation) ite.next();
            if (annotation instanceof ErrorAnnotation) {
            	ErrorAnnotation anno = (ErrorAnnotation) annotation;
                //System.err.println(ca.getLine() + " " + i);
                if ((anno.getLine()-1) == lineNumber) {
                    all.add(anno.getText());
                }
            }
        }

        if (all.size() == 0)
            return null;

        StringBuffer total = new StringBuffer();
        for (int x = 0; x < all.size(); x++) {
            String str = (String) all.get(x);
            total.append(" " + str + (x == (all.size()-1) ? "" : "\n"));
        }

        return total.toString();
	}

	@Override
	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		return null;
	}

	@Override
	public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
		return null;
	}



	public IAnnotationModel getAnnotationModel() {
		return annotationModel;
	}

	public void setAnnotationModel(IAnnotationModel annotationModel) {
		this.annotationModel = annotationModel;
	}

}
