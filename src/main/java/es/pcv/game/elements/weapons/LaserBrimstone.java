package es.pcv.game.elements.weapons;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Walker;
import es.pcv.game.Game;
import es.pcv.game.elements.weapons.bulls.BulletBrimstone;

public class LaserBrimstone extends Gun {
	private final static float V_DEFAULT = 0.1f;
	private final static int DAMAGE_DEFAULT = 1;
	private final static int AMMO_DEFAULT = 500;
	private final static int CD_DEFAULT = 0;
	private final static int ID = 1;

	public LaserBrimstone(Walker w) {
		super(w,CD_DEFAULT,AMMO_DEFAULT,ID);
	}

	public void shoot(Walker shooter, Point2D origin, Point2D direction) {
		Point2D o=origin.clone().getAbsolutePosition();
		o.add(BulletBrimstone.size.clone().multiply(-0.5f)).add(direction.clone().multiply(BulletBrimstone.size.getX()/2));
		BulletBrimstone b = new BulletBrimstone(shooter, o,
						new Point2D(V_DEFAULT, V_DEFAULT).multiply(direction),1,DAMAGE_DEFAULT);
		Game.getGame().addElement(b);
	}
	

	@Override
	public void equip(Walker w) {
		
	}

	@Override
	public void unequip() {
		
	}
}
