package es.pcv.game.elements.weapons;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Walker;
import es.pcv.game.Game;
import es.pcv.game.elements.weapons.melee.Sword;

public class Scabbard extends Weapon{

	boolean vertical = true;
	private Sword sword;
	
	public Scabbard(Walker w) {
		super(w,5);
		if (w!=null) {
			sword=new Sword(owner);
		}
	}
	public void attack(Walker attacker,Point2D origin,Point2D direction){
	}
	
	@Override
	public void equip(Walker w) {
		this.owner=w;
		sword=new Sword(owner);
		Game.getGame().addElement(sword);
	}
	
	@Override
	public void unequip() {
		sword.kill();
		sword=null;
	}
	
	@Override
	public boolean canAttack() {
		return false;
	}


	
}
