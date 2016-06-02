package es.pcv.game.elements.weapons;

import es.pcv.core.render.Point2D;
import es.pcv.core.sound.SoundPlayer;
import es.pcv.core.updater.elements.Walker;
import es.pcv.game.Game;
import es.pcv.game.elements.weapons.bulls.BulletWithRange;

public class AllDirectionsWeapon extends Weapon{
	private final static float V_DEFAULT = 0.0005f;
	private int DAMAGE_DEFAULT = 15;
	private final static int AMMO_DEFAULT = 50;
	public final static float SCOPE_DEFAULT = 0.2f;
	private final static int CD_DEFAULT = 1000;
	private final static int ID = 0;
	private static final String sound="fx/Desert_E-Diode111-8780_hifi.mp3";

	public AllDirectionsWeapon(Walker w) {
		super(w,CD_DEFAULT,AMMO_DEFAULT,ID);
	}
	public AllDirectionsWeapon(Walker w, int damage) {
		super(w,CD_DEFAULT,AMMO_DEFAULT,ID);
		DAMAGE_DEFAULT = damage;
	}
	
	@Override
	public void doAttack(Walker shooter, Point2D origin, Point2D direction) {
		for (double i = -Math.PI/4; i < 2*Math.PI; i+=Math.PI/8) {
			double v=i+Math.atan2(direction.getY(),direction.getX());
			Point2D o=origin.clone().getAbsolutePosition();
			//o.add(BulletWithRange.size.clone().multiply(-0.5f)).add(direction.clone().multiply(BulletWithRange.size.getX()/2));
			SoundPlayer.playInThreath(sound);
			BulletWithRange b = new BulletWithRange(shooter, o,
						new Point2D(Math.cos(v), Math.sin(v)).multiply(V_DEFAULT),1,DAMAGE_DEFAULT,SCOPE_DEFAULT);
			Game.getGame().addElement(b);
		}
		resetCD();
	}

	@Override
	public void equip(Walker w) {
		
	}

	@Override
	public void unequip() {
		// TODO Auto-generated method stub
		
	}
	

}
