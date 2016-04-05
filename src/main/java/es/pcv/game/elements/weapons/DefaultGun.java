package es.pcv.game.elements.weapons;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.LiveEntity;
import es.pcv.game.Game;
import es.pcv.game.elements.weapons.bulls.BulletDefault;

public class DefaultGun extends Gun {
	private float vbull = 0.05f;
	
	public DefaultGun() {
		super(50);
	}

	public void shoot(LiveEntity shooter, Point2D origin, Point2D direction) {
		BulletDefault b = new BulletDefault(shooter, origin, new Point2D(vbull, vbull).multiply(direction));
		Game.getGame().render.add(b);
		Game.getGame().updater.add(b);
		resetCD();
	}

}
