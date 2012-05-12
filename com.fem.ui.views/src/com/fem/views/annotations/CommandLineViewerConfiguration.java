package com.fem.views.annotations;

import java.util.Map;

import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISharedTextColors;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class CommandLineViewerConfiguration extends SourceViewerConfiguration {
	private static final int ASSIST_ACTIVATION_DELAY = 500;
	private ISharedTextColors manager;
	private IAnnotationModel annotationModel;
	private CommandTagScanner tagScanner;
	private Map<String, String> femCommands;
	
	
	private static final DefaultInformationControl.IInformationPresenter
	   presenter = new DefaultInformationControl.IInformationPresenter() {
	      public String updatePresentation(Display display, String infoText,
	         TextPresentation presentation, int maxWidth, int maxHeight) {
	         int start = -1;

	         // Loop over all characters of information text
	         for (int i = 0; i < infoText.length(); i++) {
//	        	 TODO: change for commands
	        	 
	            switch (infoText.charAt(i)) {
	               case '<' :

	                  // Remember start of tag
	                  start = i;
	                  break;
	               case '>' :
	                  if (start >= 0) {

	                    // We have found a tag and create a new style range
	                    StyleRange range = 
	                       new StyleRange(start, i - start + 1, null, null, SWT.BOLD);

	                    // Add this style range to the presentation
	                    presentation.addStyleRange(range);

	                    // Reset tag start indicator
	                    start = -1;
	                  }
	                  break;
	         }
	      }

	      // Return the information text

	      return infoText;
	   }
	};
	
	
	public CommandLineViewerConfiguration(ISharedTextColors manager,
			IAnnotationModel annotationModel, Map<String, String> femCommands) {
		this.manager = manager;
		this.annotationModel = annotationModel;
		this.femCommands = femCommands;
	}

	public IPresentationReconciler getPresentationReconciler(
			ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(
				getCommandTagScanner());
		reconciler.setDamager(dr, CommandPartitionScanner.FEM_COMMAND);
		reconciler.setRepairer(dr, CommandPartitionScanner.FEM_COMMAND);


		NonRuleBasedDamagerRepairer ndr = new NonRuleBasedDamagerRepairer(
				new TextAttribute(manager.getColor(ColorManager.FEM_COMMENT)));
		reconciler.setDamager(ndr, CommandPartitionScanner.FEM_COMMENT);
		reconciler.setRepairer(ndr, CommandPartitionScanner.FEM_COMMENT);

		return reconciler;
	}

	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] { IDocument.DEFAULT_CONTENT_TYPE,
				CommandPartitionScanner.FEM_COMMENT, CommandPartitionScanner.FEM_COMMAND};
	}
	
	
	
	
	
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer)
	{

		ContentAssistant assistant= new ContentAssistant();
	    IContentAssistProcessor processor = new CommandContentAssistProcessor(femCommands);
	    assistant.setContentAssistProcessor(processor, CommandPartitionScanner.FEM_COMMENT);
	    assistant.setContentAssistProcessor(processor, CommandPartitionScanner.FEM_COMMAND);
	    assistant.setContentAssistProcessor(processor, IDocument.DEFAULT_CONTENT_TYPE);
        
	    assistant.enableAutoActivation(true);
	    assistant.setInformationControlCreator(getInformationControlCreator(sourceViewer));
	    assistant.setAutoActivationDelay(ASSIST_ACTIVATION_DELAY);
	    assistant.setProposalPopupOrientation(IContentAssistant.CONTEXT_INFO_BELOW);
	    assistant.setContextInformationPopupOrientation(IContentAssistant.CONTEXT_INFO_BELOW);
	    assistant.setProposalSelectorBackground(manager.getColor(ColorManager.FEM_PROPOSAL_BG));
	    return assistant;

	}
	
	
	@Override
    public IInformationControlCreator getInformationControlCreator(ISourceViewer sourceViewer) {
        return new IInformationControlCreator() {
            public IInformationControl createInformationControl(Shell parent) {
                return new DefaultInformationControl(parent, presenter);
            }
        };
    }
	
	
	
//	public IContentFormatter getContentFormatter(ISourceViewer sourceViewer)
//	{
//	    ContentFormatter formatter = new ContentFormatter();
////	    TextFormattingStrategy textStrategy = new TextFormattingStrategy();
////	    formatter.setFormattingStrategy(textStrategy, CommandPartitionScanner.XML_TEXT);
//
//	    return formatter;
//	}



	protected CommandTagScanner getCommandTagScanner() {
		if (tagScanner == null) {
			tagScanner = new CommandTagScanner(manager);
		}
		return tagScanner;
	}

	public IAnnotationHover getAnnotationHover(ISourceViewer sourceViewer) {
		return new AnnotationHover(annotationModel);
	}

	public Map<String, String> getFemCommands() {
		return femCommands;
	}

	public void setFemCommands(Map<String, String> femCommands) {
		this.femCommands = femCommands;
	}
}
