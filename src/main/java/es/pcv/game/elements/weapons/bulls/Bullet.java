package es.pcv.game.elements.weapons.bulls;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.core.updater.elements.Element;
import es.pcv.core.updater.elements.LiveEntity;
import es.pcv.game.elements.enemies.Enemy;
import es.pcv.game.elements.player.Player;

public abstract class Bullet extends LiveEntity{
	Element whoShoot; 
	
	
	public Bullet(LiveEntity whoShoot,Point2D position, Point2D vel,Point2D size,int hits,int damage) {
		super(position,vel,size, hits, damage);
		this.whoShoot=whoShoot;
	}
	
	public void collisionObstacle(Collisionable c) {
		if (c!=whoShoot) {
			kill();
		}
	}
	

	public boolean isVulnerable() {
		return false;
	}
	
	public void collision(Collisionable col) {
		if(whoShoot!=col&&!(col instanceof Bullet)&&
				(col instanceof Player && whoShoot instanceof Enemy) 
				|| (col instanceof Enemy && whoShoot instanceof Player)){
			this.kill();
			LiveEntity r=(LiveEntity) col;
			r.doDamage(getDamage());
		}
	}
	
	public boolean isCollision(Collisionable col){
		return whoShoot!=col&&!(col instanceof Bullet)&&super.isCollision(col);
	}
}
