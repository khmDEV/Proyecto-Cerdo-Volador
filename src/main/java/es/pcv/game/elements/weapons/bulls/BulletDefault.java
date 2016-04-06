package es.pcv.game.elements.weapons.bulls;

import java.awt.Color;
import java.awt.Graphics;

import es.pcv.core.render.Point2D;
import es.pcv.core.render.auxiliar.PolygonHelper;
import es.pcv.core.updater.elements.LiveEntity;
import es.pcv.core.updater.elements.Walker;
import es.pcv.game.configuration.Config;

public class BulletDefault extends Bullet {
	
	public final static Point2D size = new Point2D(0.05f, 0.05f);
	Color c = new Color(255, 0, 255);
	
	public BulletDefault(Walker whoAttack, Point2D position, Point2D vel) {
		super(whoAttack, position, vel,size,1,50);
	}
	
	public BulletDefault(Walker whoAttack, Point2D position, Point2D vel,int hits,int damage) {
		super(whoAttack, position, vel,size,hits,damage);
	}

	public void update() {
		position.add(velocity);
		if (position.getX() > 1 || position.getX() < 0) {
			kill();
		}
		if (position.getY() > 1 || position.getY() < 0) {
			kill();
		}
	}



	public void draw(Graphics g) {
		setCollisionBox(PolygonHelper.createRectangle(position,size).getBounds2D());
		g.setColor(c);
		g.drawOval(Math.round(position.getX() * Config.size.getX()), Math.round(position.getY() * Config.size.getY()),
				Math.round(size.getX() * Config.size.getX()), Math.round(size.getY() * Config.size.getY()));
	}




}
