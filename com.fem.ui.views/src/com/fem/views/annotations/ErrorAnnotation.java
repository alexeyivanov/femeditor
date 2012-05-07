package com.fem.views.annotations;

import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.core.resources.IMarker;

public class ErrorAnnotation extends Annotation {
	private IMarker marker;
	private String text;
	private int line;
	public static final String ANNO_TYPE = "com.fem.element";
	private String type = ANNO_TYPE;
	private Position position;
	private Image image = new Image(Display.getDefault(), "c://error.gif");

	public ErrorAnnotation(IMarker marker) {
		this.marker = marker;
	}

	public ErrorAnnotation(int line, String text, String type) {
		super(type, true, null);
		this.marker = null;
		this.line = line;
		this.text = text;
		this.type = type;
	}

	public IMarker getMarker() {
		return marker;
	}

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
