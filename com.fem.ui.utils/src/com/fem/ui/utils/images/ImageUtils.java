package com.fem.ui.utils.images;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.ByteArrayOutputStream;

import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

public class ImageUtils {
	
	/**
	 * Convert SWT image to AWT with specific  SWT image type
	 * @param swtImage
	 * @param type
	 * @return
	 */

	public static Image convertSWTToAWT(org.eclipse.swt.graphics.Image swtImage, int type){
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		
		ImageData swtImageData = swtImage.getImageData();
		ImageLoader imageLoader = new ImageLoader();
		imageLoader.data = new ImageData [] {swtImageData};
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		imageLoader.save(output, type);

		byte[] aBA = output.toByteArray();


		java.awt.Image result = toolkit.createImage(aBA);
		
		return result;
		
	}
	
}
