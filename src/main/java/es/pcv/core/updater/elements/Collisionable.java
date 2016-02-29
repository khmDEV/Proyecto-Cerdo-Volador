package es.pcv.core.updater.elements;

public interface Collisionable {
	boolean isCollision(Collisionable c);
	void collision(Collisionable c);
}
