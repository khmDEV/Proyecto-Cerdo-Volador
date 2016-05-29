package es.pcv.game.elements.weapons;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Walker;
import es.pcv.game.Game;
import es.pcv.game.elements.weapons.bulls.BulletLaser;

public class LaserGun extends Weapon {

	private final static float V_DEFAULT = 0.1f;
	private final static int DAMAGE_DEFAULT = 1;
	private final static int AMMO_DEFAULT = 100;
	private final static int CD_DEFAULT = 100;
	private final static int ID = 11;

	public LaserGun(Walker w) {
		super(w,CD_DEFAULT,AMMO_DEFAULT,ID);
	}

	public void doAttack(Walker shooter, Point2D origin, Point2D direction) {
		Point2D o=origin.clone().getAbsolutePosition();
		o.add(BulletLaser.size.clone().multiply(-0.5f)).add(direction.clone().multiply(BulletLaser.size.getX()/2));
		BulletLaser b = new BulletLaser(shooter, o,
						new Point2D(V_DEFAULT, V_DEFAULT).multiply(direction),1,DAMAGE_DEFAULT);
		Game.getGame().addElement(b);
		resetCD();
	}

	@Override
	public void equip(Walker w) {
		
	}

	@Override
	public void unequip() {
		
	}
}
