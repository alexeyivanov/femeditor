package com.fem.views.annotations;

//import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.swt.graphics.Image;

import com.fem.ui.utils.images.ImageCache;

public class ErrorAnnotation extends Annotation {
//	private IMarker marker;
	private String text;
	private int line;
	private String type;
	private Position position;
	private Image image = ImageCache.getImage("error.gif");

//	public ErrorAnnotation(IMarker marker) {
//		this.marker = marker;
//	}

	public ErrorAnnotation(int line, String text, String type) {
		super(type, true, null);
//		this.marker = null;
		this.line = line;
		this.text = text;
		this.type = type;
	}

//	public IMarker getMarker() {
//		return marker;
//	}

	public int getLine() {
		return line;
	}

	public String getText() {
		return text;
	}

	public Image getImage() {
		return image;
	}

	public int getLayer() {
		return 3;
	}

	public String getType() {
		return type;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
}
