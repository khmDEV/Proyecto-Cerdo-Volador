package es.pcv.game.elements.weapons.bulls;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.core.updater.elements.LiveEntity;
import es.pcv.core.updater.elements.Walker;

public abstract class Bullet extends LiveEntity{
	
	private Walker whoAttack;
	public Bullet(Walker whoAttack,Point2D position, Point2D vel,Point2D size,int hits,int damage) {
		super(position,vel,size, hits, damage);
		this.whoAttack=whoAttack;
		
	}
	public void collisionObstacle(Collisionable c) {
		if (c!=whoAttack) {
			kill();
			if(c instanceof Walker){
				((Walker) c).doDamage(getDamage());
			}
		}
	}
	
	
	public boolean isCollision(Collisionable col){
		return whoAttack!=col&&super.isCollision(col);
	}
}
