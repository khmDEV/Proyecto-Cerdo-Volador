package es.pcv.core.updater.elements;

import java.awt.geom.Rectangle2D;

import es.pcv.core.updater.elements.Collisionable;

public abstract class PolygonObstacle extends PolygonCollision {

	public PolygonObstacle(Rectangle2D ply) {
		super(ply);
	}
	
	public void collision(Collisionable c){
		c.collisionObstacle(this);
	}
}
