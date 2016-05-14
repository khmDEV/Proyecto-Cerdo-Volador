package es.pcv.game.elements.weapons;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Walker;
import es.pcv.game.Game;
import es.pcv.game.elements.weapons.bulls.BulletDefault;

public class GunDefault extends Gun {
	private float vbull = 0.05f;
	
	public GunDefault(Walker w) {
		super(w,500,4);
	}

	public void attack(Walker shooter, Point2D origin, Point2D direction) {
		BulletDefault b = new BulletDefault(shooter, origin.getAbsolutePosition(),
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
