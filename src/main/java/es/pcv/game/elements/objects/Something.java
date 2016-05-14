package es.pcv.game.elements.objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import es.pcv.core.render.ObjectIcon;
import es.pcv.core.render.Point2D;
import es.pcv.game.elements.scene.Wall;

public class Something extends Wall{

	BufferedImage o;
	boolean inv;
	public Something(Point2D p,Point2D s,String icon,boolean inv){
		super(p,s);
		File f = new File(icon);
		try {
			o = ImageIO.read(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.inv=inv;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		if(!inv){
			g.drawImage(o, getX(),getY(),getSizeX(),getSizeY(),null);
		}else{
			g.drawImage(o, getX(),getY(),getSizeY(),getSizeX(),null);
		}
		
		
	}
}
