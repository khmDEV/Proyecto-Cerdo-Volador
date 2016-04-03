package es.pcv.core.updater.elements;

import java.awt.geom.Rectangle2D;

public interface Collisionable {
	boolean isCollision(Collisionable c);
	
	Rectangle2D getCollisionBox();

	void collision(Collisionable c);
	
	void collisionObstacle(Collisionable c);

	
}
