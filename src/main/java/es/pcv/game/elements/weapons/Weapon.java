package es.pcv.game.elements.weapons;

import es.pcv.core.render.ObjectIcon;
import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Walker;
import es.pcv.game.configuration.Config;

public abstract class Weapon {

	public static final ObjectIcon ICONS= new ObjectIcon(Config.RESOURCES_PATH+"/icons/ms.png", 4, 8);

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
