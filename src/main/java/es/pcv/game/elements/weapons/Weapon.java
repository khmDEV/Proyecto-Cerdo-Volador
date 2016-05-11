package es.pcv.game.elements.weapons;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Walker;

public abstract class Weapon {

	protected Walker owner; 
	protected final int id;
	public Weapon(Walker w, int id) {
		owner = w;
		this.id =id;
	}
	
	public abstract void attack(Walker attacker,Point2D origin,Point2D direction);
	
	public abstract void equip(Walker w);
	
	public abstract void unequip();
	
	public int getId(){
		return id;
	}

	public abstract boolean canAttack();

}
