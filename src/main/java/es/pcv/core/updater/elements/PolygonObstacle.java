package es.pcv.core.updater.elements;

import java.awt.geom.Rectangle2D;

import es.pcv.core.render.Point2D;
import es.pcv.core.render.auxiliar.PolygonHelper;
import es.pcv.core.updater.elements.Collisionable;

public abstract class PolygonObstacle extends PolygonCollision {

	public PolygonObstacle(Rectangle2D ply) {
		super(ply);
	}
	
	/**
	 * 
	 * @param p posicion de la esquina superior izquierda
	 * @param s tamaño
	 */
	public PolygonObstacle(Point2D p, Point2D s) {
		super(PolygonHelper.createRectangle(p,s).getBounds2D());
	}
	
	public void collision(Collisionable c){
		c.collisionObstacle(this);
	}
}
