package com.fem.ui.views;

import java.util.Observable;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextListener;
import org.eclipse.jface.text.TextEvent;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.ViewPart;

import com.fem.api.IDrawModel;
import com.fem.api.IFemView;

public class FemView extends ViewPart implements IFemView{
	
	private static final String EMPTY_TEXT = "";

	public static final String ID = "com.fem.ui.views.femView";
	
	private PageBook pagebook;
	private TableViewer tableviewer;
	private TextViewer textviewer;
	private IDrawModel model;
	
	
	private void showText(String text) {
		Document document = new Document(text);
		textviewer.setDocument(document);
		pagebook.showPage(textviewer.getControl());
	}
	

	@Override
	public void createPartControl(Composite parent) {
		
        pagebook = new PageBook(parent, SWT.NONE);
		
		tableviewer = new TableViewer(pagebook, SWT.NONE);
		tableviewer.setLabelProvider(new WorkbenchLabelProvider());
		tableviewer.setContentProvider(new ArrayContentProvider());
		
		getSite().setSelectionProvider(tableviewer);
		
		textviewer = new TextViewer(pagebook, SWT.H_SCROLL | SWT.V_SCROLL);
		textviewer.setEditable(true);
		showText(EMPTY_TEXT);
		
		
		textviewer.addTextListener(new ITextListener() {
			
			@Override
			public void textChanged(TextEvent event) {
				
				if(model == null){
					return;
				}
				
				
				if("\r\n".equals(event.getDocumentEvent().getText())){
					IDocument document = event.getDocumentEvent().getDocument();
					String lastLineText = null;
					int prevLine = 0;
					try {
						int numberOfLines = document.getNumberOfLines();
						if(numberOfLines >= 2){
							prevLine = numberOfLines - 2;
						}
						
						lastLineText = document.get(document.getLineOffset(prevLine) , document.getLineLength(prevLine));
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
					
//					TODO validation
					
					model.drawElement();
				    model.notifyAllObservers();
				}
			}
		});
		
	}

	@Override
	public void setFocus() {
		pagebook.setFocus();
	}
	
	public void dispose() {
		super.dispose();
	}

	@Override
	public void update(Observable model, Object arg) {
		IDrawModel drawModel = (IDrawModel) model;
		setModel(drawModel);
	}


	public void setModel(IDrawModel model) {
		this.model = model;
	}

}
