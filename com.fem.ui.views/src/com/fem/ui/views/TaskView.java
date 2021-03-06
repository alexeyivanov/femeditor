package com.fem.ui.views;

import java.util.Observable;

import org.eclipse.jface.text.Document;
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

public class TaskView extends ViewPart implements IFemView {

	private PageBook pagebook;
	private TableViewer tableviewer;
	private TextViewer textviewer;
	

	
	private void showText(String text) {
		textviewer.setDocument(new Document(text));
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
		textviewer.setEditable(false);
		showText("Imagine a fantastic user interface here in Task View");
	}

	@Override
	public void setFocus() {
		pagebook.setFocus();
	}
	
	
	public void dispose() {
		super.dispose();
	}

	@Override
	public void update(Observable o, Object arg) {
		String updatedString = "update() called, model identificator "
		        + ((IDrawModel) o).getUID();
		showText(updatedString);
	}

}
