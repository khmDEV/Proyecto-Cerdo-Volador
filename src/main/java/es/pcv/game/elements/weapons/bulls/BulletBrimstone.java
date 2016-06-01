package es.pcv.game.elements.weapons.bulls;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import es.pcv.core.render.Point2D;
import es.pcv.core.render.Render3D;
import es.pcv.core.render.auxiliar.Helper3D;
import es.pcv.core.render.auxiliar.PolygonHelper;
import es.pcv.core.updater.elements.Walker;

public class BulletBrimstone extends Bullet {

	public final static Point2D size = new Point2D(0.4f, 0.05f);
	Color c = new Color(255, 0, 255);

	public BulletBrimstone(Walker whoAttack, Point2D position, Point2D vel) {
		super(whoAttack, position, vel, size, 1, 50);
	}

	public BulletBrimstone(Walker whoAttack, Point2D position, Point2D vel, int hits, int damage) {
		super(whoAttack, position, vel, size, hits, damage);
	}

	public void update(long ms) {
	}

	public void draw(Graphics g) {
		g.setColor(c);
		Polygon rec = PolygonHelper.getRectangle(getPos(), getSize());
		double an = Math.atan2(velocity.getY(), velocity.getX());
		Point center=new Point((int) Math.round(rec.getBounds2D().getCenterX()-0.5), (int) Math.round(rec.getBounds2D().getCenterY()-0.5));
		rec = PolygonHelper.rotatePolygon(rec,center, an);
		setCollisionBox(rect);
		g.drawPolygon(rec);
		kill();
	}

	public void draw3d(GL2 gl, GLU glu, GLUquadric quadric) {
		float an =(float) (Math.atan2(-velocity.getX(), -velocity.getY())/Math.PI*180 + 180-90);;
		Helper3D.drawRotatedRectangle(gl, getCenterPos(), getSize(), 0.05f, 0.01f, c, -1,an );	
		kill();
	}

}
