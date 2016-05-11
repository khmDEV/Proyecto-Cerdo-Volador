package es.pcv.game.elements.weapons;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Walker;

public abstract class Gun extends Weapon{
	private long RELOAD_CD;
	protected long reload = 0;
	
	public Gun(Walker w,int CD, int id){
		super(w, id);
		RELOAD_CD=CD;
	}
	
	public abstract void attack(Walker shooter,Point2D origin,Point2D direction);
	
	
	public boolean canAttack() {
		return (System.currentTimeMillis() - reload) > RELOAD_CD;
	}
	
	public void resetCD(){
		reload = System.currentTimeMillis();
	}
	
}
