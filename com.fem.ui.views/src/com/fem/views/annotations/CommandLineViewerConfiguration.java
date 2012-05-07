package com.fem.views.annotations;

import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISharedTextColors;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

public class CommandLineViewerConfiguration extends SourceViewerConfiguration {
	private ISharedTextColors manager;
	private IAnnotationModel annotationModel;

	public CommandLineViewerConfiguration(ISharedTextColors manager, IAnnotationModel annotationModel) {
		this.manager = manager;
		this.annotationModel = annotationModel;
	}

	public IPresentationReconciler getPresentationReconciler(
			ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();
		return reconciler;
	}

	public IAnnotationHover getAnnotationHover(ISourceViewer sourceViewer) {
		return new AnnotationHover(annotationModel);
	}
}
