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
import es.pcv.core.render.auxiliar.PolygonHelper;
import es.pcv.core.updater.elements.Walker;
import es.pcv.game.configuration.Config;

public class BulletLaser extends Bullet {

	public final static Point2D size = new Point2D(0.03f, 0.01f);
	Color c = new Color(255, 0, 255);

	public BulletLaser(Walker whoAttack, Point2D position, Point2D vel) {
		super(whoAttack, position.addX(0.01f), vel, size, 1, 50);
	}

	public BulletLaser(Walker whoAttack, Point2D position, Point2D vel, int hits, int damage) {
		super(whoAttack, position.addX(0.01f), vel, size, hits, damage);
	}

	public void draw(Graphics g) {
		g.setColor(c);
		double an = Math.atan2(velocity.getY(), velocity.getX());
		
		// TO-FIX
		int val=velocity.getY()>0?-1:1;
		addX((int) Math.round(val*Math.sin(an)*(0.01)*Config.scale.getX()));
		//////////
		
		Polygon rec = PolygonHelper.getRectangle(getPos().clone(), size.clone().multiply(Config.scale));

		Point center=new Point((int) Math.round(rec.getBounds2D().getCenterX()), (int) Math.round(rec.getBounds2D().getCenterY()));
		rec = PolygonHelper.rotatePolygon(rec,center, an);
		setCollisionBox(rec.getBounds2D());
		
		g.drawPolygon(rec);
	}

	@Override
	public void draw3d(GL2 gl, GLU glu, GLUquadric quadric) {
		// TODO Auto-generated method stub
		
	}
	
	
}
