package es.pcv.core.updater.elements;

import java.awt.geom.Rectangle2D;

public abstract class PolygonCollision implements Collisionable{
	protected Rectangle2D rect;
	protected Rectangle2D lastRectangle;

	public PolygonCollision(Rectangle2D ply) {
		this.rect = ply;
	}

	public PolygonCollision() {
	}

	public boolean isCollision(Collisionable c) {
		return c.getCollisionBox().intersects(rect);
	}

	public Rectangle2D getCollisionBox() {
		return rect;
	}

	public void setCollisionBox(Rectangle2D ply) {
		lastRectangle=rect;
		this.rect = ply;
	}
	

}
