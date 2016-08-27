package es.pcv.core.render;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ObjectIcon {
	private ArrayList<BufferedImage>imagenes;
	public final int h,w;
	
	public ObjectIcon(String path,int height,int width){
		File f = new File(path);
		h=height;
		w=width;
		imagenes=new ArrayList<BufferedImage>();
        try {
        	BufferedImage image = ImageIO.read(f);
			for(int h=0;h<height;h++){
				for(int w=0;w<width;w++){
					BufferedImage image2 = ((BufferedImage) image).getSubimage((image.getWidth()/width)*w, (image.getHeight()/height)*h,image.getWidth()/width, image.getHeight()/height); 
					imagenes.add(image2);
				}
			}
        } catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public BufferedImage getImage(int i){
		return imagenes.get(i);
	}
}