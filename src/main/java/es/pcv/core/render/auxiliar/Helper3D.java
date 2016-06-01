package es.pcv.core.render.auxiliar;

import static com.jogamp.opengl.GL2ES3.GL_QUADS;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import es.pcv.core.render.Point2D;
import es.pcv.game.configuration.Config;

public class Helper3D {

	private static Texture[] textures = new Texture[6];

	private static boolean textInit = false;

	public static void initTextures(GL2 gl) {
		if(textInit){
			return;
		}
		textInit = true;
		for (int i = 0; i < 6; i++) {
			String ruta = Config.RESOURCES_PATH + "/textures/";
			if (i == 0) {
				ruta = ruta + "floor.bmp";
			} else if (i == 1) {
				ruta = ruta + "wall.bmp";
			} else if (i == 2) {
				ruta = ruta + "door.bmp";
			} else if (i == 3) {
				ruta = ruta + "box.bmp";
			} else if (i == 4) {
				ruta = ruta + "tu.bmp";
			} else if (i == 5) {
				ruta = ruta + "zombie.bmp";
			}
			Texture tex = loadGLTextures(ruta);
			tex.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
			tex.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
			tex.setTexParameteri(gl, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
			tex.setTexParameteri(gl, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR_MIPMAP_LINEAR);
			gl.glGenerateMipmap(GL2.GL_TEXTURE_2D);
			textures[i] = tex;
		}
	}

	private static Texture loadGLTextures(String file) { // Load image And
															// Convert To
		// Textures
		try {
			Texture texture = TextureIO.newTexture(new File(file), true);
			return texture;
		} catch (GLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static void drawSphere(GL2 gl, GLU glu, GLUquadric quadric, Point2D pos, double offH, double rad, Color c,int texture) {
		Point2D p = pos.adaptar();
		drawSphere(gl, glu, quadric, p.getX(), offH, p.getY(), rad, c,texture);
	}

	public static void drawSphere(GL2 gl, GLU glu, GLUquadric quadric, double posX, double posY, double posZ,
			double rad, Color c,int texture) {
		enableTexture(gl, texture);

		gl.glTranslated(-posX, posY, -posZ);
		if (c!=null) {
			gl.glColor3f(c.getRed(), c.getGreen(), c.getBlue());
		}
		glu.gluSphere(quadric, rad, 32, 32);
		gl.glTranslated(posX, -posY, posZ);
		disableTexture(gl, texture);
	}

	public static void drawSphere(GL2 gl, GLU glu, GLUquadric quadric, double posX, double posY, Color c, double h,
			double rad,int texture) {
		enableTexture(gl, texture);

		gl.glTranslated(-posX, h, -posY);
		if (c!=null) {
			gl.glColor3f(c.getRed(), c.getGreen(), c.getBlue());
		}
		glu.gluSphere(quadric, rad, 32, 32);
		gl.glTranslated(posX, -h, posY);
		disableTexture(gl, texture);

	}

	public static void drawBase(GL2 gl) {
		enableTexture(gl, 0);

		gl.glTranslatef(0.0f, -.5f, -3.2f);
		gl.glRotatef(45, 1, 0, 0);
		gl.glBegin(GL_QUADS);
		gl.glNormal3f(0.0f, 0.0f, 1.0f); // Front Face
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-1.0f, 0.7f, 1.0f);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(1.0f, 0.7f, 1.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, 1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, 1.0f);

		gl.glNormal3f(0.0f, 0.0f, -1.0f); // Back Face
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-1.0f, 0.7f, -1.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(1.0f, 0.7f, -1.0f);

		gl.glNormal3f(0.0f, 1.0f, 0.0f); // Top Face
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-1.0f, 1.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(1.0f, 1.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, -1.0f);

		gl.glNormal3f(0.0f, -1.0f, 0.0f); // Bottom Face
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-1.0f, 0.7f, -1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(1.0f, 0.7f, -1.0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(1.0f, 0.7f, 1.0f);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-1.0f, 0.7f, 1.0f);

		gl.glNormal3f(1.0f, 0.0f, 0.0f); // Right Face
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(1.0f, 0.7f, -1.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, 1.0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(1.0f, 0.7f, 1.0f);

		gl.glNormal3f(-1.0f, 0.0f, 0.0f); // Left Face
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-1.0f, 0.7f, -1.0f);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-1.0f, 0.7f, 1.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, 1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, -1.0f);
		gl.glEnd();

		disableTexture(gl, 0);
	}

	public static void drawRectangle(GL2 gl, Point2D center, Point2D size, float offH, float h, Color c, int texture) {
		Point2D abs = center.adaptar();
		abs.setX(-abs.getX());
		abs.setY(-abs.getY());
		float alt = size.getX() / Config.scale.getX();
		float anch = size.getY() / Config.scale.getY();
		drawRectangle(gl, abs.getX(), abs.getY(), offH, alt, anch, h, c, texture);
	}
	public static void drawRotatedRectangle(GL2 gl, Point2D center, Point2D size, float offH, float h, Color c, int texture,float angle){
		Point2D abs = center.adaptar();
		abs.setX(-abs.getX());
		abs.setY(-abs.getY());
		float alt = size.getX() / Config.scale.getX();
		float anch = size.getY() / Config.scale.getY();
		
		
		gl.glTranslatef(abs.getX(),0,abs.getY());
  	  	gl.glRotated(angle, 0, 1, 0);
  	  	gl.glTranslatef(-abs.getX(),0,-abs.getY());
  	    drawRectangle(gl, abs.getX(), abs.getY(), offH, alt, anch, h, c, texture);
  	    gl.glTranslatef(abs.getX(),0,abs.getY());
  	  	gl.glRotated(-angle, 0, 1, 0);
  	    gl.glTranslatef(-abs.getX(),0,-abs.getY());
		
		
	}
	public static void drawRotatedRectangle(GL2 gl, Point2D center, Point2D size, float offH, float h, Color c, int texture,Point2D p,float angle){
		Point2D abs = center.adaptar();
		abs.setX(-abs.getX());
		abs.setY(-abs.getY());
		float alt = size.getX() / Config.scale.getX();
		float anch = size.getY() / Config.scale.getY();
		Point2D ca=p.adaptar().multiply(-1);
		
		gl.glTranslatef(ca.getX(),0,ca.getY());
  	  	gl.glRotated(angle, 0, 1, 0);
  	  	gl.glTranslatef(-ca.getX(),0,-ca.getY());
  	    drawRectangle(gl, abs.getX(), abs.getY(), offH, alt, anch, h, c, texture);
  	    gl.glTranslatef(ca.getX(),0,ca.getY());
  	  	gl.glRotated(-angle, 0, 1, 0);
  	    gl.glTranslatef(-ca.getX(),0,-ca.getY());
		
		
	}
	public static void drawRectangle(GL2 gl, float x, float y, float z, float sizeX, float sizeY, float h, Color c,
			int texture) {
		gl.glTranslatef(0f, z, 0f);
		drawRectangle(x + sizeX, x - sizeX, x + sizeX, x - sizeX, y + sizeY, y + sizeY, y - sizeY, y - sizeY, gl, c, h,
				texture);
		gl.glTranslatef(0f, -z, 0f);

	}

	private static void drawRectangle(float x1, float x2, float x3, float x4, float y1, float y2, float y3, float y4,
			GL2 gl, Color c, float h, int texture) {
		// gl.glTranslatef(0f, -0.1f, 0f);
		if (c!=null) {
			gl.glColor3f(c.getRed(), c.getGreen(), c.getBlue());
		}
		enableTexture(gl, texture);

		gl.glBegin(GL_QUADS);
		//gl.glColor3f(c.getRed(), c.getGreen(), c.getBlue());
		
	  	gl.glNormal3f(0.0f, 0.0f, 1.0f);
	  	gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(x2, 0.0f, y2);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(x1, 0.0f, y1);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(x1, h, y1);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(x2, h, y2);
		
			
		 gl.glNormal3f(0.0f, 0.0f, -1.0f);  
	  	gl.glTexCoord2f(1.0f, 0.0f);// Back Face
		gl.glVertex3f(x4, 0.0f, y4);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(x4, h, y4);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(x3, h, y3);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(x3, 0.0f, y3);

		
	  	  // Top Face
		gl.glNormal3f(0.0f, 1.0f, 0.0f);
	  	
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(x2, h, y2);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(x4, h, y4);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(x3, h, y3);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(x1, h, y1);

		
		gl.glNormal3f(0.0f, -1.0f, 0.0f);                // Bottom Face
	  	gl.glTexCoord2f(1.0f, 1.0f);
	  	gl.glVertex3f(x4,0.0f, y4);
	  	gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(x3, 0.0f, y3);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(x1, 0.0f, y1);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(x2, 0.0f, y2);
		
		gl.glNormal3f(1.0f, 0.0f, 0.0f);
	  	gl.glTexCoord2f(1.0f, 0.0f);
	  	gl.glVertex3f(x3, 0.0f, y3);
	  	gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(x3, h, y3);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(x1, h, y1);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(x1, 0.0f, y1);

		gl.glNormal3f(-1.0f, 0.0f, 0.0f);               // Left Face
	  	gl.glTexCoord2f(0.0f, 0.0f);
	  	gl.glVertex3f(x4, 0.0f, y4);
	  	gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(x2, 0.0f, y2);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(x2, h, y2);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(x4, h, y4);


		gl.glEnd();
		//gl.glTranslatef(0f,0.1f, 0f);
		
		disableTexture(gl, texture);

	}

	private static void enableTexture(GL2 gl, int texture) {
		if (textures.length > texture && texture >= 0) {
			textures[texture].enable(gl);
			textures[texture].bind(gl);
		}
	}

	private static void disableTexture(GL2 gl, int texture) {
		if (textures.length > texture && texture >= 0) {
			textures[texture].disable(gl);
		}
	}

	public static void drawCilinder(GL2 gl, GLU glu, GLUquadric quadric, double posX, double posY, Color c,
			int texture) {

		enableTexture(gl, texture);
		gl.glTranslated(-posX, 0, -posY);
		gl.glRotated(-110, 1, 0, 0);
		
		//gl.glColor3f(c.getRed(), c.getGreen(), c.getBlue());
		glu.gluCylinder(quadric, 0.06f, 0.06f, .11f, 32, 32); 
		gl.glRotated(110, 1, 0, 0);
		gl.glTranslated(posX, 0, posY);
		
		disableTexture(gl, texture);

	}

	public static void drawCilinder(GL2 gl, GLU glu, GLUquadric quadric, Point2D pos, double rBase, double rTop,
			double offH, double heigth, Color c,int texture) {
		Point2D p = pos.clone().adaptar();
		drawCilinder(gl, glu, quadric, p.getX(), offH, p.getY(), rBase, rTop, heigth, c,texture);
	}

	public static void drawCilinder(GL2 gl, GLU glu, GLUquadric quadric, double posX, double posY, double posZ,
			double rBase, double rTop, double heigth, Color c,int texture) {
		
		enableTexture(gl, texture);
		gl.glTranslated(-posX, -posY, -posZ);
		gl.glRotated(-110, 1, 0, 0);
		
		//gl.glColor3f(c.getRed(), c.getGreen(), c.getBlue());
		glu.gluCylinder(quadric, 0.06f, 0.06f, .11f, 32, 32); 
		gl.glRotated(110, 1, 0, 0);
		gl.glTranslated(posX, -posY, posZ);
		disableTexture(gl, texture);
	}

	public static void end() {
		textInit=false;
	}
}
