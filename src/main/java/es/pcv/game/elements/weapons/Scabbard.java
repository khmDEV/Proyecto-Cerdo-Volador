package es.pcv.game.elements.weapons;

import es.pcv.core.updater.elements.Walker;
import es.pcv.game.elements.weapons.melee.Melee;

public abstract class Scabbard extends Weapon{

	protected boolean vertical = true;
	protected Melee melee;
	
	public Scabbard(Walker w,int dur,int id) {
		super(w,0,dur,id);
	}
	
	public int getDurability(){
		if (melee!=null) {
			durability=melee.getDurability();
		}
		return durability;
	}


	
}
