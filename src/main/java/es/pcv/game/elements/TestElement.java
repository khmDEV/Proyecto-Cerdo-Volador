package es.pcv.game.elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.core.updater.elements.Element;
import es.pcv.core.updater.elements.Obstacle;
import es.pcv.game.configuration.Config;

public class TestElement implements Obstacle {
	Point2D position = new Point2D(0.5f,0);
	Point2D velocity = new Point2D(0.005f,0.005f);
	Point2D size = new Point2D(0.05f,0.05f);

	Polygon ply;
	Color c=new Color(0, 255, 0);

	public TestElement() {
		ply = new Polygon(
				new int[] { Math.round((position.getX() + size.getX()) * Config.size.getX()), Math.round((position.getX() - size.getX()) * Config.size.getX()),
						Math.round((position.getX() - size.getX()) * Config.size.getX()), Math.round((position.getX() + size.getX()) * Config.size.getX()) },
				new int[] { Math.round((position.getY() - size.getY()) * Config.size.getY()), Math.round((position.getY() - size.getY()) * Config.size.getY()),
						Math.round((position.getY() + size.getY()) * Config.size.getY()), Math.round((position.getY() + size.getY()) * Config.size.getY()) },
				4);
	}

	public boolean isCollision(Collisionable c) {
		return c.getCollisionBox().intersects(ply.getBounds2D());
	}

	public void update() {
		position.add(velocity);
		float x=position.getX();
		float y=position.getY();
		if (x > 1 || x < 0) {
			velocity.setX(-velocity.getX());
			position.setX(x<0?0:1);
		}
		if (y > 1 || y < 0) {
			velocity.setY(-velocity.getY());
			position.setY(y<0?0:1);
		}
	}

	public void draw(Graphics g) {
		ply = new Polygon(
				new int[] { Math.round((position.getX() + size.getX()) * Config.size.getX()), Math.round((position.getX() - size.getX()) * Config.size.getX()),
						Math.round((position.getX() - size.getX()) * Config.size.getX()), Math.round((position.getX() + size.getX()) * Config.size.getX()) },
				new int[] { Math.round((position.getY() - size.getY()) * Config.size.getY()), Math.round((position.getY() - size.getY()) * Config.size.getY()),
						Math.round((position.getY() + size.getY()) * Config.size.getY()), Math.round((position.getY() + size.getY()) * Config.size.getY()) },
				4);
		g.setColor(c);
		g.drawPolygon(ply);
	}

	public void collision(Collisionable col) {
		c=new Color((int) Math.round(Math.random()*255),(int) Math.round(Math.random()*255),(int) Math.round(Math.random()*255));
	}

	public synchronized boolean isDead() {
		return false;
	}
	
	public synchronized boolean kill() {
		return false;
	}

	public Rectangle2D getCollisionBox() {
		return ply.getBounds2D();
	}

}
