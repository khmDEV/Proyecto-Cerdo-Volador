package es.pcv.game.elements.weapons;

import es.pcv.core.render.Point2D;
import es.pcv.core.sound.SoundPlayer;
import es.pcv.core.updater.elements.Walker;
import es.pcv.game.Game;
import es.pcv.game.elements.weapons.bulls.BulletDefault;

public class GunDefault extends Weapon {
	
	private final static float V_DEFAULT = 0.001f;
	private final static int DAMAGE_DEFAULT = 10;
	private final static int AMMO_DEFAULT = 100;
	private final static int CD_DEFAULT = 300;
	private final static int ID = 3;
	private static final String sound="fx/Glock_Fi-Twisted4-9039_hifi.mp3";

	public GunDefault(Walker w) {
		super(w,CD_DEFAULT,AMMO_DEFAULT,ID);
	}

	public void doAttack(Walker shooter, Point2D origin, Point2D direction) {
		Point2D o=origin.clone().getAbsolutePosition();
		o.add(BulletDefault.size.clone().multiply(-0.5f)).add(direction.clone().multiply(BulletDefault.size.getX()/2));
		SoundPlayer.playInThreath(sound);
		BulletDefault b = new BulletDefault(shooter, o,
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
