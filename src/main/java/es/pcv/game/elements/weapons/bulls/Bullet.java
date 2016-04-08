package es.pcv.game.elements.weapons.bulls;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.core.updater.elements.Walker;
import es.pcv.game.elements.weapons.Weapon;

public abstract class Bullet extends Weapon{
	
	
	public Bullet(Walker whoAttack,Point2D position, Point2D vel,Point2D size,int hits,int damage) {
		super(whoAttack,position,vel,size, hits, damage);
	}
	
	public void collisionObstacle(Collisionable c) {
		if (c!=whoAttack) {
			kill();
		}
	}
	
	
	public boolean isCollision(Collisionable col){
		return whoAttack!=col&&!(col instanceof Weapon)&&super.isCollision(col);
	}
}
