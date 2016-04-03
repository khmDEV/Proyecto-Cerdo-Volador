package es.pcv.core.updater.elements;

import es.pcv.core.render.Point2D;

public class Collision {
	public Collisionable collionable;
	public Point2D intersection;
	public Collision(Collisionable collionable, Point2D intersection){
		this.collionable=collionable;
		this.intersection=intersection;
	}
}
