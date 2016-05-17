package es.pcv.game.elements.scene;

import java.awt.geom.Rectangle2D;

import es.pcv.core.render.Point2D;
import es.pcv.core.render.auxiliar.PolygonHelper;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.core.updater.elements.Element;
import es.pcv.core.updater.elements.LiveEntity;
import es.pcv.core.updater.elements.PolygonCollision;

public abstract class Wall extends PolygonCollision implements Element {
	
	
	
	public Wall(Rectangle2D r){
		super(r);
	}
	public Wall(Point2D p, Point2D s){
		super(PolygonHelper.createRectangle(p,s).getBounds2D());
	}	

	
	public void update() {
	}

	public void collision(Collisionable c){
		if(c instanceof LiveEntity){
			c.collisionObstacle(this);
		}
	}
	
	public void collisionObstacle(Collisionable c) {
		
	}

}
