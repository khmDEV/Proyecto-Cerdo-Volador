package es.pcv.game.elements.weapons;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Walker;
import es.pcv.game.Game;
import es.pcv.game.elements.weapons.bulls.BulletWithRange;

public class Shotgun extends Gun{
	private float vbull = 0.05f;
	
	public Shotgun(Walker w) {
		super(w,1000,0);
	}
	
	@Override
	public void attack(Walker shooter, Point2D origin, Point2D direction) {
		for (double i = -Math.PI/4; i < Math.PI/4; i+=Math.PI/8) {
			double v=i+Math.atan2(direction.getY(),direction.getX());
			BulletWithRange b = new BulletWithRange(shooter, origin.getAbsolutePosition(),
						new Point2D(Math.cos(v), Math.sin(v)).multiply(vbull),0.2f);
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
