package es.pcv.game.elements.weapons.bulls;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.core.updater.elements.Walker;
import es.pcv.game.elements.enemies.Enemy;
import es.pcv.game.elements.player.Player;
import es.pcv.game.elements.weapons.WeaponEntity;

public abstract class Bullet extends WeaponEntity{
	
	protected Walker whoAttack;
	public Bullet(Walker whoAttack,Point2D position, Point2D vel,Point2D size,int hits,int damage) {
		super(position,vel,size, hits, damage);
		this.whoAttack=whoAttack;
		
	}
	public void collisionObstacle(Collisionable c) {
		super.collisionObstacle(c);
		if (c!=whoAttack&&!(c instanceof Bullet)&& !(whoAttack instanceof Enemy && c instanceof Enemy)) {
			
			if(c instanceof Player){
				if(!((Player) c).isJumping()){
					((Player) c).doDamage(getDamage());
					kill();
				}		
			
			}
			else if(c instanceof Walker){	
				kill();
				((Walker) c).doDamage(getDamage());
			}
			else {
				kill();
			}
			
		}
	}
	
	
	public boolean isCollision(Collisionable col){
		return whoAttack!=col&&!(col instanceof Bullet)&&super.isCollision(col)
				&& !(whoAttack instanceof Enemy && col instanceof Enemy) &&
				 !((col instanceof Player) && ((Player) col).isJumping());
	}
}
