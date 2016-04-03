package es.pcv.game.elements.scene;

import java.awt.geom.Rectangle2D;

import es.pcv.core.updater.elements.Collisionable;
import es.pcv.core.updater.elements.LiveEntity;
import es.pcv.core.updater.elements.PolygonCollision;

public class Wall extends PolygonCollision {
	
	public Wall(Rectangle2D r){
		super(r);
	}

	public void collision(Collisionable c){
		if(c instanceof LiveEntity){
			c.collisionObstacle(this);
		}
	}

	public void collisionObstacle(Collisionable c) {
		
	}

}
