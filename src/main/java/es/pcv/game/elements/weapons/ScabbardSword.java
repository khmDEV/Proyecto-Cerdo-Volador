package es.pcv.game.elements.weapons;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Walker;
import es.pcv.game.Game;
import es.pcv.game.elements.weapons.melee.Sword;

public class ScabbardSword extends Scabbard{
	protected final static int ID = 22;
	protected final static int DAMAGE_DEFAULT = 30;
	protected final static int DURABILITY_DEFAULT = 100;

	protected boolean vertical = true;
	
	public ScabbardSword(Walker w) {
		super(w,DURABILITY_DEFAULT,ID);
		if (w!=null) {
			melee=new Sword(owner,durability,DAMAGE_DEFAULT);
		}
	}
	
	public ScabbardSword(Walker w,int id) {
		super(w,DURABILITY_DEFAULT,id);
		if (w!=null) {
			melee=new Sword(owner,durability,DAMAGE_DEFAULT);
		}
	}
	
	public void doAttack(Walker attacker,Point2D origin,Point2D direction){
	}
	
	@Override
	public void equip(Walker w) {
		this.owner=w;
		melee=new Sword(owner,durability,DAMAGE_DEFAULT);
		Game.getGame().addElement(melee);
	}
	
	@Override
	public void unequip() {
		durability=melee.getDurability();
		melee.kill();
		melee=null;
	}
	
	@Override
	public boolean canAttack() {
		return false;
	}
	
	public int getDurability(){
		if (melee!=null) {
			durability=melee.getDurability();
		}
		return durability;
	}


	
}
