package es.pcv.core.render;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ObjectIcon {
	private BufferedImage image;
	public ObjectIcon(String path){
		File f = new File(path);
        try {
			image = ImageIO.read(f);
			image =((BufferedImage) image).getSubimage(0,0,image.getWidth()/3, image.getHeight()/4);        
        
        } catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public Image getImage(){
		return image;
	}
}
