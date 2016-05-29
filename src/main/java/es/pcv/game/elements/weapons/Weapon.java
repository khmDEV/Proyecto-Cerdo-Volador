package es.pcv.game.elements.weapons;

import es.pcv.core.render.ObjectIcon;
import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Walker;
import es.pcv.game.configuration.Config;

public abstract class Weapon {

	public static final ObjectIcon ICONS= new ObjectIcon(Config.RESOURCES_PATH+"/icons/ms.png", 4, 8);

	protected Walker owner; 
	protected final int id;
	protected long RELOAD_CD;
	protected long reload = 0;
	protected int durability;
	protected int max_durability;
	
	public Weapon(Walker w,int id){
		RELOAD_CD=0;
		max_durability=0;
		durability=max_durability;
		this.id =id;
		owner = w;
	}
	
	public Weapon(Walker w,int CD,int maxdurability, int id){
		RELOAD_CD=CD;
		max_durability=maxdurability;
		durability=max_durability;
		this.id =id;
		owner = w;
	}
	
	public abstract void doAttack(Walker shooter,Point2D origin,Point2D direction);;
	
	public void attack(Walker shooter,Point2D origin,Point2D direction){
		doAttack(shooter, origin, direction);
		resetCD();
		durability--;
	};
	
	public boolean canAttack() {
		return (System.currentTimeMillis() - reload) > RELOAD_CD && (max_durability==0 || durability>0);
	}
	
	public void resetCD(){
		reload = System.currentTimeMillis();
	}
	
	public int getDurability(){
		return durability;
	}
	
	public int getMaxDurability(){
		return max_durability;
	}

	public abstract void equip(Walker w);
	
	public abstract void unequip();
	
	public int getId(){
		return id;
	}

}
