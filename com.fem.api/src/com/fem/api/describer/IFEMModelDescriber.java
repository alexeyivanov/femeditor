/* *****************************************************************************
 * Copyright (c) 2009 Ola Spjuth.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ola Spjuth - initial API and implementation
 ******************************************************************************/
package com.fem.api.describer;


/**
 * An interface for describing Editors.
 * @author ola
 *
 */
public interface IFEMModelDescriber {

    /**
     * Returns an EditorID for the object or NULL is none found
     * @param fileExtention The fileExtention to determine editor for
     * @return
     * @throws Exception 
     */
    String getPreferredEditorID(String fileExtention) throws Exception;

}
