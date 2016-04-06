package es.pcv.game.elements.weapons;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.LiveEntity;
import es.pcv.core.updater.elements.Walker;

public abstract class Gun {
	private long RELOAD_CD;
	protected long reload = 0;
	
	public Gun(int CD){
		RELOAD_CD=CD;
	}
	
	public abstract void shoot(Walker shooter,Point2D origin,Point2D direction);
	
	
	public boolean canShoot() {
		return (System.currentTimeMillis() - reload) > RELOAD_CD;
	}
	
	public void resetCD(){
		reload = System.currentTimeMillis();
	}
	
}
