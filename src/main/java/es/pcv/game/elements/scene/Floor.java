package es.pcv.game.elements.scene;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import es.pcv.core.render.ObjectIcon;
import es.pcv.core.render.Point2D;
import es.pcv.core.render.auxiliar.Helper3D;
import es.pcv.core.render.auxiliar.PolygonHelper;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.core.updater.elements.Element;
import es.pcv.core.updater.elements.PolygonCollision;
import es.pcv.game.configuration.Config;

public class Floor extends PolygonCollision implements Element{

	protected int TEXTURE = 0;
	protected ObjectIcon icon = new ObjectIcon(Config.RESOURCES_PATH + "/textures/floor.bmp", 1, 1);
	
	public Floor(Point2D p, Point2D s) {
		super(PolygonHelper.createRectangle(p,s).getBounds2D());
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
		Helper3D.drawRectangle(gl, getCenterPos(), getSize(), -0.2f, 0.2f, null,TEXTURE);
	}

	@Override
	public void collisionObstacle(Collisionable c) {
		
	}

	@Override
	public void update(long diff) {
		
	}

	@Override
	public boolean isCollision(Collisionable c) {
		return false;
	}

	@Override
	public Rectangle2D getCollisionBox() {
		return null;
	}

	@Override
	public void collision(Collisionable c) {
		
	}
}
