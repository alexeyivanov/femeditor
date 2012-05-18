package com.fem.api;

import java.util.Observer;

public interface IFemEditor extends Observer {
	
	void setFemEditorDrawMode(String fileName, String drawMode);

	void setFemEditorDrawModeDefault(String drawMode);

}
