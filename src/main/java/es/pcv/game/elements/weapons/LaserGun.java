package es.pcv.game.elements.weapons;

import es.pcv.core.render.Point2D;
import es.pcv.core.sound.SoundPlayer;
import es.pcv.core.updater.elements.Walker;
import es.pcv.game.Game;
import es.pcv.game.elements.weapons.bulls.BulletLaser;

public class LaserGun extends Weapon {

	private final static float V_DEFAULT = 0.0005f;
	private int DAMAGE_DEFAULT = 5;
	private final static int AMMO_DEFAULT = 100;
	private final static int CD_DEFAULT = 130;
	private final static int ID = 11;
	private static final String sound="fx/laser-domson-8694_hifi.mp3";

	public LaserGun(Walker w) {
		super(w,CD_DEFAULT,AMMO_DEFAULT,ID);
	}
	public LaserGun(Walker w, int amo) {
		super(w,CD_DEFAULT,amo,ID);
	}
	public LaserGun(Walker w, int amo,int damage, int cd) {
		super(w,cd,amo,ID);
		DAMAGE_DEFAULT=damage;
	}

	public void doAttack(Walker shooter, Point2D origin, Point2D direction) {
		Point2D o=origin.clone().getAbsolutePosition();
		o.add(BulletLaser.size.clone().multiply(-0.5f)).add(direction.clone().multiply(BulletLaser.size.getX()/2));
		SoundPlayer.playInThreath(sound);
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
