package es.pcv.core.updater.elements;

import java.awt.geom.Rectangle2D;

import es.pcv.core.updater.elements.Collisionable;

public abstract class Obstacle implements Collisionable {
	private Rectangle2D ply;

	public Obstacle(Rectangle2D ply) {
		this.ply = ply;
	}

	public Obstacle() {
	}

	public boolean isCollision(Collisionable c) {
		return c.getCollisionBox().intersects(ply);
	}

	public Rectangle2D getCollisionBox() {
		return ply;
	}

	public void setCollisionBox(Rectangle2D ply) {
		this.ply = ply;
	}

}
