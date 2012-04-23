package com.fem.ui.editors;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.fem.api.IDrawModel;

public class DummyEditorInput implements IEditorInput{

	private IDrawModel model;

    public DummyEditorInput(String id) {
    }
    public DummyEditorInput(IDrawModel model) {
    	this.model =model; 
    }
    public String getId() {
        return model.getUID();
    }

    @Override
    public boolean exists() {
        return true;
    }

    @Override
    public ImageDescriptor getImageDescriptor() {
        return ImageDescriptor.getMissingImageDescriptor();
    }

    @Override
    public String getName() {
        return String.valueOf(model.getUID());
    }

    @Override
    public IPersistableElement getPersistable() {
        return null;
    }

    @Override
    public String getToolTipText() {
        return model.getUID().toString();
    }

    @Override
    public Object getAdapter(Class adapter) {
        return model.getAdapter(adapter);
    }

}
