package com.fem.ui.views;

import java.util.Map;
import java.util.Observable;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.rules.IPartitionTokenScanner;
import org.eclipse.jface.text.source.AnnotationBarHoverManager;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.jface.text.source.AnnotationPainter;
import org.eclipse.jface.text.source.AnnotationRulerColumn;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.ISharedTextColors;
import org.eclipse.jface.text.source.LineNumberRulerColumn;
import org.eclipse.jface.text.source.OverviewRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.VerifyKeyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.ViewPart;

import com.fem.api.IDrawModel;
import com.fem.api.IFemView;
import com.fem.ui.views.document.listener.DocumentPositionUpdater;
import com.fem.views.annotations.AnnotationConfiguration;
import com.fem.views.annotations.AnnotationHover;
import com.fem.views.annotations.AnnotationMarkerAccess;
import com.fem.views.annotations.ColorManager;
import com.fem.views.annotations.CommandLineViewerConfiguration;
import com.fem.views.annotations.CommandPartitionScanner;
import com.vem.views.utils.CommandBuilder;

/**
 * 
 * Command Line View
 */
public class FemView extends ViewPart implements IFemView{
	
	private static final String DUMMY_TEXT = "";

	public static final String ID = "com.fem.ui.views.femView";
	
	public static final String ERROR_TYPE = "error.type";
	
	public static final RGB ERROR_RGB = new RGB(255, 0, 0); 
	
	public static final int VERTICAL_RULER_WIDTH = 12;
	
	
	private SourceViewer textviewer;

	private Document document;
	
	private final Map<String, String> femCommands = CommandBuilder.buildCommands();


	private AnnotationModel annotationModel;

	private StyledText st;

	private DocumentPositionUpdater updater;


        
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
 		      
 		      CompositeRuler fCompositeRuler = new CompositeRuler();

 		      textviewer = new SourceViewer(parent, fCompositeRuler, fOverviewRuler, true, styles);
 		      st = textviewer.getTextWidget(); 
 		      st.setFont(new Font(Display.getDefault(), "Courier New", 10, SWT.NONE));
 		     
 		      createDocument();
 		    
 		      
 		      annotationModel = new AnnotationModel();
 		      annotationModel.connect(document);
 		      
 		      
 		    updater = new DocumentPositionUpdater(femCommands, annotationModel, st);
			document.addPositionUpdater(updater);


 		      SourceViewerConfiguration sourceViewerConfiguration = new CommandLineViewerConfiguration(colorManager, annotationModel, femCommands);

 			  AnnotationPainter ap = new AnnotationPainter(textviewer,  fAnnotationAccess); 
 		  	  ap.addAnnotationType(ERROR_TYPE); 
 		  	  ap.setAnnotationTypeColor(ERROR_TYPE, colorManager.getColor(ERROR_RGB)); 
 		  	
 		  	  textviewer.addPainter(ap); 

 		  	  textviewer.configure(sourceViewerConfiguration); 
 		      
 		      textviewer.setDocument(document, annotationModel);
 		      

 		     LineNumberRulerColumn lineNumberColumn = new LineNumberRulerColumn();
 		      
 		     lineNumberColumn.setForeground(ColorManager.getInstance().getColor(ColorManager.LINE_NUMBER));
 		      
 		     AnnotationRulerColumn rulerColumn = new  AnnotationRulerColumn(annotationModel, 14, fAnnotationAccess); 
 		      
 		      
 			 fCompositeRuler.addDecorator(0, rulerColumn);
 			 fCompositeRuler.addDecorator(1, lineNumberColumn);
 			  
 			 rulerColumn.addAnnotationType(ERROR_TYPE); 
 			  
 			  // hover manager that shows text when we hover 
 			  AnnotationBarHoverManager fAnnotationHoverManager = new 
 					  AnnotationBarHoverManager(fCompositeRuler, textviewer, new AnnotationHover(annotationModel), new 
 							  AnnotationConfiguration()); 
 			  fAnnotationHoverManager.install(rulerColumn.getControl());
 		      
 		      addAssistantAcivatorListener();
	  }

	private void addAssistantAcivatorListener() {
		textviewer.appendVerifyKeyListener(new VerifyKeyListener() {
			public void verifyKey(VerifyEvent event) {

				if (event.stateMask == SWT.CTRL && event.character == ' ') {

					if (textviewer.canDoOperation(SourceViewer.CONTENTASSIST_PROPOSALS)){
						textviewer.doOperation(SourceViewer.CONTENTASSIST_PROPOSALS);
					}
					event.doit = false;
				}
			}
		});
	}

	private OverviewRuler createOverviewRuler(ISharedTextColors colorManager, AnnotationMarkerAccess fAnnotationAccess) {
		OverviewRuler fOverviewRuler = new OverviewRuler(fAnnotationAccess,
				VERTICAL_RULER_WIDTH, colorManager);

		fOverviewRuler.addAnnotationType(ERROR_TYPE);
		fOverviewRuler.addHeaderAnnotationType(ERROR_TYPE);
		fOverviewRuler.setAnnotationTypeLayer(ERROR_TYPE, 3);
		fOverviewRuler.setAnnotationTypeColor(ERROR_TYPE,
				colorManager.getColor(ERROR_RGB));
		return fOverviewRuler;
	}


	private void createDocument() {
		document = new Document();
		document.set(DUMMY_TEXT);
		
		IPartitionTokenScanner scanner = new CommandPartitionScanner();
		String[] legalContentTypes = new String[] {
				CommandPartitionScanner.FEM_COMMAND,
				CommandPartitionScanner.FEM_COMMENT};
		IDocumentPartitioner partitioner = new FastPartitioner(scanner,
				legalContentTypes);

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
