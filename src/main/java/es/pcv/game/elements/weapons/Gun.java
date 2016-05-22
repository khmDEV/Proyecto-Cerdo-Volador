package es.pcv.game.elements.weapons;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Walker;

public abstract class Gun extends Weapon{
	private long RELOAD_CD;
	protected long reload = 0;
	private int ammo;
	private int max_ammo;
	public Gun(Walker w,int CD,int maxammo, int id){
		super(w, id);
		RELOAD_CD=CD;
		max_ammo=maxammo;
		ammo=max_ammo;
	}
	
	public  void attack(Walker shooter,Point2D origin,Point2D direction){
		shoot(shooter, origin, direction);
		resetCD();
		ammo--;
	};
	
	public abstract void shoot(Walker shooter, Point2D origin, Point2D direction);
	
	public boolean canAttack() {
		return (System.currentTimeMillis() - reload) > RELOAD_CD && (max_ammo==0 || ammo>0);
	}
	
	public void resetCD(){
		reload = System.currentTimeMillis();
	}
	
	public int getAmmo(){
		return ammo;
	}
	
	public int getMaxAmmo(){
		return max_ammo;
	}
	
}
