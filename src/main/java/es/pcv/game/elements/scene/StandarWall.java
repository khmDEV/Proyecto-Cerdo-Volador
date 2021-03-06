package es.pcv.game.elements.scene;

import java.awt.Color;
import java.awt.Graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import es.pcv.core.render.ObjectIcon;
import es.pcv.core.render.Point2D;
import es.pcv.core.render.auxiliar.Helper3D;
import es.pcv.game.configuration.Config;

public class StandarWall extends Wall {
	Color c;
	protected int TEXTURE = 1;
	protected ObjectIcon icon = new ObjectIcon(Config.RESOURCES_PATH + "/textures/wall.bmp", 1, 1);

	public StandarWall(Point2D p, Point2D s) {
		super(p, s);
		this.c = new Color(0, 0, 0);
	}

	public void draw(Graphics g) {
		g.drawImage(icon.getImage(0), getX(), getY(), getSizeX(), getSizeY(), null);
	}

	public boolean isDead() {
		return false;
	}

	public boolean kill() {
		return false;
	}

	public void draw3d(GL2 gl, GLU glu, GLUquadric quadric) {
		Helper3D.drawRectangle(gl, getCenterPos(), getSize(), 0, 0.1f, null,TEXTURE);
	}

}
