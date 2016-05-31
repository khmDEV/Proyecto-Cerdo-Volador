package es.pcv.game.elements.weapons.bulls;

import java.awt.Color;
import java.awt.Graphics;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Walker;

public class BulletDefault extends Bullet {

	public final static Point2D size = new Point2D(0.01f, 0.01f);
	Color c = new Color(255, 0, 255);

	public BulletDefault(Walker whoAttack, Point2D position, Point2D vel) {
		super(whoAttack, position, vel, size, 1, 50);
	}

	public BulletDefault(Walker whoAttack, Point2D position, Point2D vel, int hits, int damage) {
		super(whoAttack, position, vel, size, hits, damage);
	}

	public void draw(Graphics g) {
		g.setColor(c);
		g.drawOval(getX(), getY(), getSizeX(), getSizeY());
	}

}
