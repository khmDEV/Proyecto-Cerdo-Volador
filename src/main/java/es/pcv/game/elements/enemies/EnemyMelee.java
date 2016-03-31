package es.pcv.game.elements.enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.game.configuration.Config;
import es.pcv.game.elements.player.Player;

public class EnemyMelee extends Enemy {
	//Point2D position = new Point2D(0.5f, 0);
	//Point2D velocity = new Point2D(0.005f, 0.005f);
	//Point2D size = new Point2D(0.05f, 0.05f);

	Polygon ply;
	Color c = new Color(0, 255, 0);

	public EnemyMelee() {
		super(new Point2D(0.5f, 0),new Point2D(0.005f, 0.005f),new Point2D(0.05f, 0.05f),10, 1);
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
		setCollisionBox(ply.getBounds2D());
	}

	public void update() {
		System.out.println("moviendo enemigo");
		lastPosition.setX(position.getX());
		lastPosition.setY(position.getY());
		
		position.add(velocity);
		float x = position.getX();
		float y = position.getY();
		if (x > 1 || x < 0) {
			velocity.setX(-velocity.getX());
			position.setX(x < 0 ? 0 : 1);
		}
		if (y > 1 || y < 0) {
			velocity.setY(-velocity.getY());
			position.setY(y < 0 ? 0 : 1);
		}
	}

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
		setCollisionBox(ply.getBounds2D());
		g.setColor(c);
		g.drawPolygon(ply);
	}

	public void collision(Collisionable col) {
		super.collision(col);
		if (col instanceof Player) {
			Player pl = (Player) col;
			pl.doDamage(getDamage());
		}
		c = new Color((int) Math.round(Math.random() * 255), (int) Math.round(Math.random() * 255),
				(int) Math.round(Math.random() * 255));
	}

	public synchronized boolean isDead() {
		return false;
	}

	public synchronized boolean kill() {
		return false;
	}

}
