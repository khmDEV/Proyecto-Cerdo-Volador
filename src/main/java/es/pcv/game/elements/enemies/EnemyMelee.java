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

	Polygon ply;
	Color c = new Color(0, 255, 0);
	private final static Point2D maxVelocity=new Point2D(0.005f, 0.005f);
	protected Point2D velocity=maxVelocity.clone();

	public EnemyMelee() {
		super(new Point2D(0.5f, 0), maxVelocity.clone(), new Point2D(0.05f, 0.05f), 1000, 1);
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
		if (obstacle_collision_dx && obstacle_collision_ux) {
			velocity.setX(0);
			obstacle_collision_dx = false;
			obstacle_collision_ux = false;
		}else if (obstacle_collision_dx) {
			velocity.setX(-Math.abs(maxVelocity.getX()));
			obstacle_collision_dx = false;
		}else if (obstacle_collision_ux) {
			velocity.setX(Math.abs(maxVelocity.getX()));
			obstacle_collision_ux = false;
		}
		if (obstacle_collision_dy && obstacle_collision_uy) {
			velocity.setY(0);
			obstacle_collision_dy = false;
			obstacle_collision_uy = false;
		}else if (obstacle_collision_dy) {
			velocity.setY(-Math.abs(maxVelocity.getY()));
			obstacle_collision_dy = false;
		}else if (obstacle_collision_uy) {
			velocity.setY(Math.abs(maxVelocity.getY()));
			obstacle_collision_uy = false;
		}
		position.add(velocity);
	}

	public void draw(Graphics g) {
		ply = PolygonHelper.createRectangle(position, size);
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


}
