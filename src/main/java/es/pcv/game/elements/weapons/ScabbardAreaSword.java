package es.pcv.game.elements.weapons;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Walker;
import es.pcv.game.Game;
import es.pcv.game.elements.weapons.melee.AreaSword;

public class ScabbardAreaSword extends Scabbard{
	private final static int ID = 0;
	protected final static int DAMAGE_DEFAULT = 1;
	protected final static int DURABILITY_DEFAULT = 0;
	protected final static int CD_DEFAULT = 300;
	
	public ScabbardAreaSword(Walker w) {
		super(w,DURABILITY_DEFAULT,ID);
	}
	
	public void doAttack(Walker attacker,Point2D origin,Point2D direction){
		if (melee==null||melee.isDead()) {
			melee=new AreaSword(attacker, durability, DAMAGE_DEFAULT, CD_DEFAULT);
			Game.getGame().addElement(melee);
		}
	}
	
	@Override
	public void equip(Walker w) {
	}
	
	@Override
	public void unequip() {
	}
	
	@Override
	public boolean canAttack() {
		return melee==null||melee.isDead();
	}


	
}
