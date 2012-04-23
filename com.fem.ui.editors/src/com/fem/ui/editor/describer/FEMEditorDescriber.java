package com.fem.ui.editor.describer;

import com.fem.api.describer.IFEMModelDescriber;

public class FEMEditorDescriber implements IFEMModelDescriber {

	@Override
	public String getPreferredEditorID(String fileExtention) throws Exception {
		
		if("brep".equals(fileExtention)){
			return "com.fem.main.femEditor";
		}
		
		return null;
	}

}
