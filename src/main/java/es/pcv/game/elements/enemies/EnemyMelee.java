package es.pcv.game.elements.enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import es.pcv.core.render.Point2D;
import es.pcv.core.render.auxiliar.PolygonHelper;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.game.configuration.Config;
import es.pcv.game.elements.player.Player;

public class EnemyMelee extends Enemy {
	// Point2D position = new Point2D(0.5f, 0);
	// Point2D velocity = new Point2D(0.005f, 0.005f);
	// Point2D size = new Point2D(0.05f, 0.05f);

	Polygon ply;
	Color c = new Color(0, 255, 0);

	public EnemyMelee() {
		super(new Point2D(0.5f, 0), new Point2D(0.005f, 0.005f), new Point2D(0.05f, 0.05f), 10, 1);
		setCollisionBox(position, size);
	}

	public EnemyMelee(Point2D position) {
		super(position, new Point2D(-0.005f, -0.005f), new Point2D(0.05f, 0.05f), 10, 1);
		setCollisionBox(position, size);
	}

	public void setCollisionBox(Point2D position, Point2D size) {
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
		super.update();
		// System.out.println("moviendo enemigo");
		//System.out.println(obstacle_collision_x + "---" + obstacle_collision_y);

		if (obstacle_collision_dx) {
			velocity.setX(-Math.abs(velocity.getX()));
			obstacle_collision_dx = false;
		}
		if (obstacle_collision_dy) {
			velocity.setY(-Math.abs(velocity.getY()));
			obstacle_collision_dy = false;
		}
		if (obstacle_collision_ux) {
			velocity.setX(Math.abs(velocity.getX()));
			obstacle_collision_ux = false;
		}
		if (obstacle_collision_uy) {
			velocity.setY(Math.abs(velocity.getY()));
			obstacle_collision_uy = false;
		}
		//System.out.println(position);
		//System.out.println(velocity);
		position.add(velocity);
		//System.out.println(position);
		

		//float x = position.getX();
		//float y = position.getY();
		/*if (x > 1 || x < 0 ) {
			velocity.setX(-velocity.getX());
			position.setX(x < 0 ? 0 : 1);
		}
		if (y > 1 || y < 0 ) {
			velocity.setY(-velocity.getY());
			position.setY(y < 0 ? 0 : 1);
		}*/

	}

	public void draw(Graphics g) {
		ply = PolygonHelper.createRectangle(position, size);
		setCollisionBox(ply.getBounds2D());
		g.setColor(c);
		g.drawPolygon(ply);
	}

	public void collision(Collisionable col) {
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
