package es.pcv.game.elements.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import es.pcv.core.render.Point2D;
import es.pcv.core.render.Render3D;
import es.pcv.core.render.auxiliar.Helper3D;
import es.pcv.game.elements.scene.Wall;

public class Something extends Wall{
	protected int TEXTURE = 1;

	BufferedImage o;
	boolean inv;
	protected Color c = new Color(0, 0, 1);

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

	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		if(!inv){
			g.drawImage(o, getX(),getY(),getSizeX(),getSizeY(),null);
		}else{
			g.drawImage(o, getX(),getY(),getSizeY(),getSizeX(),null);
		}
		
		
	}

	public void draw3d(GL2 gl, GLU glu, GLUquadric quadric) {
		Helper3D.drawRectangle(gl, getCenterPos(), getSize(), 0, 0.1f, null,TEXTURE);
	}
}
