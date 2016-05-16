package es.pcv.game.elements.weapons;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Walker;
import es.pcv.game.Game;
import es.pcv.game.elements.weapons.bulls.BulletBrimstone;

public class LaserBrimstone extends Gun {
	private float vbull = 0.1f;
	
	public LaserBrimstone(Walker w) {
		super(w,0,4);
	}

	public void attack(Walker shooter, Point2D origin, Point2D direction) {
		BulletBrimstone b = new BulletBrimstone(shooter, origin.getAbsolutePosition(),
						new Point2D(vbull, vbull).multiply(direction));
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
