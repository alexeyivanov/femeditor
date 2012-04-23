package com.fem.api.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import com.fem.api.describer.IFEMModelDescriber;


public class ExtensionPointDescriberHelper {
	

    public static List<IFEMModelDescriber> getAvailableDescribersFromExtentionPoint() throws Exception{

        IExtensionRegistry registry = Platform.getExtensionRegistry();

        if ( registry == null ) throw new Exception(
        "Eclipse registry=null");
        // it likely means that the Eclipse workbench has not
        // started, for example when running tests

        List<IFEMModelDescriber> describers = new ArrayList<IFEMModelDescriber>();

        IExtensionPoint serviceObjectExtensionPoint = registry
        .getExtensionPoint("com.fem.api.model");

        IExtension[] serviceObjectExtensions
        = serviceObjectExtensionPoint.getExtensions();

        for(IExtension extension : serviceObjectExtensions) {
            for( IConfigurationElement element
                    : extension.getConfigurationElements() ) {

                if (element.getName().equals("drawModelFile")){

                    Object obj;
                    try {
                        obj = element.createExecutableExtension("describer");
                        IFEMModelDescriber describer = (IFEMModelDescriber) obj;
                        describers.add(describer);
                    } catch ( CoreException e ) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return describers;

    }
}
