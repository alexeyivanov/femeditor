package com.fem.cmd;

import org.eclipse.swt.graphics.Image;
import org.eclipse.jface.resource.ImageDescriptor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ImageCache {

    private static Map<String, ImageDescriptor> map = new HashMap<String, ImageDescriptor>();
    private static final String PATH = "/icons/";

    public static Image getImage(String fileName) {
        ImageDescriptor ima = getImageDescriptor(fileName);
        return ima.createImage();
    }

    public static ImageDescriptor getImageDescriptor(String fileName) {
        Object obj = map.get(fileName);
        if (obj == null) {
            ImageDescriptor image = createImage(fileName);
            map.put(fileName, image);
            return image;
        }

        return ((ImageDescriptor)obj);
    }

    private static ImageDescriptor createImage(String fileName) {
        return ImageDescriptor.createFromURL(ImageCache.class.getResource(PATH + fileName));
    }

    public static void dispose() {
        Iterator ite = map.keySet().iterator();
        while (ite.hasNext()) {
            Object key = ite.next();
            ImageDescriptor im = (ImageDescriptor) map.get(key);
            im.createImage().dispose(); 
        }
        map = null;
    }
}
