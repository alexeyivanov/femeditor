package com.fem.ui.views;

import java.util.Observable;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.rules.IPartitionTokenScanner;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.AnnotationBarHoverManager;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.jface.text.source.AnnotationPainter;
import org.eclipse.jface.text.source.AnnotationRulerColumn;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.ISharedTextColors;
import org.eclipse.jface.text.source.OverviewRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.fem.api.IDrawModel;
import com.fem.api.IFemView;
//import com.fem.ui.views.document.listener.CommandDocumentListener;
import com.fem.views.annotations.AnnotationConfiguration;
import com.fem.views.annotations.AnnotationHover;
import com.fem.views.annotations.AnnotationMarkerAccess;
import com.fem.views.annotations.ColorManager;
import com.fem.views.annotations.CommandLineViewerConfiguration;
import com.fem.views.annotations.CommandPartitionScanner;
import com.fem.views.annotations.ErrorAnnotation;

/**
 * 
 * @author echekanina
 * Command Line View
 */
public class FemView extends ViewPart implements IFemView{
	
//	private static final String DUMMY_TEXT = "";
	private static final String DUMMY_TEXT = "this is a test document";

	public static final String ID = "com.fem.ui.views.femView";
	
	public static final String ANNO_TYPE = "error.type";
	
	public static final RGB ERROR_RGB = new RGB(255, 0, 0); 
	
	public static final int VERTICAL_RULER_WIDTH = 12;
	
	
	private SourceViewer textviewer;
//	private CommandDocumentListener listener;

	private Document document;


	private AnnotationModel annotationModel;


        
 		@Override
		public void createPartControl(Composite parent) {
			
 		      int styles = SWT.V_SCROLL
 		          | SWT.H_SCROLL
 		          | SWT.MULTI
 		          | SWT.BORDER
 		          | SWT.FULL_SELECTION;
 		      ISharedTextColors colorManager = ColorManager.getInstance();
 		      
 		      
 		      AnnotationMarkerAccess fAnnotationAccess = new AnnotationMarkerAccess();

 		      OverviewRuler fOverviewRuler = createOverviewRuler(colorManager, fAnnotationAccess); 
 		      
 		      CompositeRuler fCompositeRuler = new CompositeRuler(VERTICAL_RULER_WIDTH);

 		      createDocument();
 		      
 		      textviewer = new SourceViewer(parent, fCompositeRuler, fOverviewRuler, true, styles);

 		      annotationModel = new AnnotationModel();
 		      annotationModel.connect(document);

 		      
 		      SourceViewerConfiguration sourceViewerConfiguration = new CommandLineViewerConfiguration(colorManager, annotationModel);

 			  AnnotationPainter ap = new AnnotationPainter(textviewer,  fAnnotationAccess); 
 		  	  ap.addAnnotationType(ANNO_TYPE); 
 		  	  ap.setAnnotationTypeColor(ANNO_TYPE, colorManager.getColor(ERROR_RGB)); 
 		  	
 		  	 // this will draw the squigglies under the text 
 		  	  textviewer.addPainter(ap); 

 		  	  textviewer.configure(sourceViewerConfiguration); 
 		      
 		      textviewer.setDocument(document, annotationModel);

// 		      LineNumberRulerColumn rulerColumn = new LineNumberRulerColumn();
 		      
 		     AnnotationRulerColumn rulerColumn = new  AnnotationRulerColumn(annotationModel, 14, fAnnotationAccess); 
 		      
 		      
 			 fCompositeRuler.addDecorator(0, rulerColumn);
 			  
 			 rulerColumn.addAnnotationType(ANNO_TYPE); 
 			  
 			  // hover manager that shows text when we hover 
 			  AnnotationBarHoverManager fAnnotationHoverManager = new 
 					  AnnotationBarHoverManager(fCompositeRuler, textviewer, new AnnotationHover(annotationModel), new 
 							  AnnotationConfiguration()); 
 			  fAnnotationHoverManager.install(rulerColumn.getControl());
 			  

 		      Annotation annotation = new ErrorAnnotation(1, "Learn how to spell \"this!\"", ANNO_TYPE);
 		      Position position = new Position(0, 4);
 		      annotationModel.addAnnotation(annotation, position);
 		      
// 		      listener = new CommandDocumentListener();
//		 	  textviewer.addTextListener(listener);
	  }

	private OverviewRuler createOverviewRuler(ISharedTextColors colorManager, AnnotationMarkerAccess fAnnotationAccess) {
		OverviewRuler fOverviewRuler = new OverviewRuler(fAnnotationAccess,
				VERTICAL_RULER_WIDTH, colorManager);

		fOverviewRuler.addAnnotationType(ANNO_TYPE);
		fOverviewRuler.addHeaderAnnotationType(ANNO_TYPE);
		// set what layer this type is on
		fOverviewRuler.setAnnotationTypeLayer(ANNO_TYPE, 3);
		// set what color is used on the overview ruler for the type
		fOverviewRuler.setAnnotationTypeColor(ANNO_TYPE,
				colorManager.getColor(ERROR_RGB));
		return fOverviewRuler;
	}


	private void createDocument() {
		document = new Document();
		document.set(DUMMY_TEXT);

		IPartitionTokenScanner scanner = new CommandPartitionScanner();
		String[] legalContentTypes = new String[] {};
		IDocumentPartitioner partitioner  = new FastPartitioner(scanner, legalContentTypes);
//		
//		
//		 IDocumentPartitioner partitioner =
//		 new FastPartitioner(
//		 new XMLPartitionScanner(),
//		 new String[] {
//		 XMLPartitionScanner.XML_TAG,
//		 XMLPartitionScanner.XML_COMMENT });

		 
		 
		 partitioner.connect(document);
		 document.setDocumentPartitioner(partitioner);
	}

	@Override
	public void setFocus() {
	}
	
	public void dispose() {
		super.dispose();
	}

	@Override
	public void update(Observable model, Object arg) {
		IDrawModel drawModel = (IDrawModel) model;
//		listener.setModel(drawModel);
	}

}
