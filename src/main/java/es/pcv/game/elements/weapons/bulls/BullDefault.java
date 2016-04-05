package es.pcv.game.elements.weapons.bulls;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.Semaphore;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.core.updater.elements.Element;
import es.pcv.core.updater.elements.LiveEntity;
import es.pcv.game.configuration.Config;
import es.pcv.game.elements.enemies.Enemy;
import es.pcv.game.elements.player.Player;

public class BullDefault implements Element {
	Point2D position = new Point2D(0.5f, 0);
	Point2D velocity = new Point2D(0.005f, -0.005f);
	Point2D size = new Point2D(0.05f, 0.05f);
	Color c = new Color(255, 255, 0);
	Semaphore deadS = new Semaphore(1);
	Element whoShoot; 
	public BullDefault(float x, float y, float vx, float vy,Element whoShoot) {
		this.whoShoot=whoShoot;
		ply = new Polygon(
				new int[] { Math.round((position.getX() + size.getX()) * Config.size.getX()),
						Math.round((position.getX() - size.getX()) * Config.size.getX()),
						Math.round((position.getX() - size.getX()) * Config.size.getX()),
						Math.round((position.getX() + size.getX()) * Config.size.getX()) },
				new int[] { Math.round((position.getY() - size.getY()) * Config.size.getY()),
						Math.round((position.getY() - size.getY()) * Config.size.getY()),
						Math.round((position.getY() + size.getY()) * Config.size.getY()),
						Math.round((position.getY() + size.getY()) * Config.size.getY()) },
				4);
		this.velocity = new Point2D(vx, vy);
		this.position = new Point2D(x, y);
	}

	boolean dead = false;

	public void update() {
		position.add(velocity);
		if (position.getX() > 1 || position.getX() < 0) {
			kill();
		}
		if (position.getY() > 1 || position.getY() < 0) {
			kill();
		}
	}

	Polygon ply;

	public void draw(Graphics g) {
		ply = new Polygon(
				new int[] { Math.round((position.getX() + size.getX()) * Config.size.getX()),
						Math.round((position.getX() - size.getX()) * Config.size.getX()),
						Math.round((position.getX() - size.getX()) * Config.size.getX()),
						Math.round((position.getX() + size.getX()) * Config.size.getX()) },
				new int[] { Math.round((position.getY() - size.getY()) * Config.size.getY()),
						Math.round((position.getY() - size.getY()) * Config.size.getY()),
						Math.round((position.getY() + size.getY()) * Config.size.getY()),
						Math.round((position.getY() + size.getY()) * Config.size.getY()) },
				4);
		g.setColor(c);
		g.drawOval(Math.round(position.getX() * Config.size.getX()), Math.round(position.getY() * Config.size.getY()),
				Math.round(size.getX() * Config.size.getX()), Math.round(size.getY() * Config.size.getY()));
		// g.drawPolygon(ply);
	}

	public boolean isDead() {
		try {
			deadS.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean r = dead;
		deadS.release();
		return r;
	}

	public boolean kill() {
		try {
			deadS.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean r = (dead = true);
		deadS.release();
		return r;
	}

	public boolean isCollision(Collisionable c) {
		return c.getCollisionBox().intersects(ply.getBounds2D());
	}

	public void collision(Collisionable col) {
		if((col instanceof Player && whoShoot instanceof Enemy) 
				|| (col instanceof Enemy && whoShoot instanceof Player)){
			this.kill();
			LiveEntity e=(LiveEntity) whoShoot;
			LiveEntity r=(LiveEntity) col;
			r.doDamage(e.getDamage());
		}
		c = new Color((int) Math.round(Math.random() * 255), (int) Math.round(Math.random() * 255),
				(int) Math.round(Math.random() * 255));
	}

	public Rectangle2D getCollisionBox() {
		return ply.getBounds2D();
	}

	public boolean playerShoot() {
		// TODO Auto-generated method stub
		return whoShoot instanceof Player;
	}

	public void collisionObstacle(Collisionable c) {
		if (c!=whoShoot) {
			kill();
		}
	}
}
