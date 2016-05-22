package es.pcv.game.elements.weapons;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Walker;
import es.pcv.game.Game;
import es.pcv.game.elements.weapons.bulls.BulletWithRange;

public class Shotgun extends Gun{
	private final static float V_DEFAULT = 0.05f;
	private final static int DAMAGE_DEFAULT = 1;
	private final static int AMMO_DEFAULT = 50;
	private final static int CD_DEFAULT = 1000;
	private final static int ID = 18;

	public Shotgun(Walker w) {
		super(w,CD_DEFAULT,AMMO_DEFAULT,ID);
	}
	
	@Override
	public void shoot(Walker shooter, Point2D origin, Point2D direction) {
		for (double i = -Math.PI/4; i < Math.PI/4; i+=Math.PI/8) {
			double v=i+Math.atan2(direction.getY(),direction.getX());
			Point2D o=origin.clone().getAbsolutePosition();
			o.add(BulletWithRange.size.clone().multiply(-0.5f)).add(direction.clone().multiply(BulletWithRange.size.getX()/2));
			BulletWithRange b = new BulletWithRange(shooter, o,
						new Point2D(Math.cos(v), Math.sin(v)).multiply(V_DEFAULT),1,DAMAGE_DEFAULT,0.2f);
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
