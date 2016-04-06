package es.pcv.game.elements.weapons;

import java.awt.Graphics;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.core.updater.elements.Element;
import es.pcv.core.updater.elements.LiveEntity;
import es.pcv.core.updater.elements.Walker;
import es.pcv.game.elements.enemies.Enemy;
import es.pcv.game.elements.player.Player;
import es.pcv.game.elements.weapons.bulls.Bullet;

public abstract class Weapon extends LiveEntity{

	protected Walker whoAttack; 
	public Weapon(Walker w,Point2D p, Point2D v, Point2D s, int l, int d) {
		super(p, v, s, l, d);
		whoAttack = w;
	}
	
	public boolean isVulnerable() {
		return false;
	}
	
	public void collision(Collisionable col) {
		if(whoAttack!=col&&!(col instanceof Bullet)&&
				(col instanceof Player && whoAttack instanceof Enemy) 
				|| (col instanceof Enemy && whoAttack instanceof Player)){
			LiveEntity r=(LiveEntity) col;
			r.doDamage(getDamage());
		}
	}
}
