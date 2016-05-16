package es.pcv.game.elements.weapons.bulls;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

import es.pcv.core.render.Point2D;
import es.pcv.core.render.auxiliar.PolygonHelper;
import es.pcv.core.updater.elements.Walker;

public class BulletLaser extends Bullet {

	public final static Point2D size = new Point2D(0.03f, 0.01f);
	Color c = new Color(255, 0, 255);

	public BulletLaser(Walker whoAttack, Point2D position, Point2D vel) {
		super(whoAttack, position, vel, size, 1, 50);
	}

	public BulletLaser(Walker whoAttack, Point2D position, Point2D vel, int hits, int damage) {
		super(whoAttack, position, vel, size, hits, damage);
	}

	public void update() {
		posAdd(velocity);
	}

	public void draw(Graphics g) {
		g.setColor(c);
		Polygon rec = PolygonHelper.getRectangle(getPos(), getSize());
		double an = Math.atan2(velocity.getY(), velocity.getX());
		Point center=new Point((int) Math.round(rec.getBounds2D().getCenterX()-0.5), (int) Math.round(rec.getBounds2D().getCenterY()-0.5));
		rec = PolygonHelper.rotatePolygon(rec,center, an);
		setCollisionBox(rect);
		g.drawPolygon(rec);
	}

}
