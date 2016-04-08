package es.pcv.game.elements.weapons;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.LiveEntity;
import es.pcv.core.updater.elements.Walker;
import es.pcv.game.Game;
import es.pcv.game.configuration.Config;
import es.pcv.game.elements.weapons.bulls.BulletDefault;

public class DefaultGun extends Gun {
	private float vbull = 0.05f;
	
	public DefaultGun() {
		super(50);
	}

	public void shoot(Walker shooter, Point2D origin, Point2D direction) {
		System.out.println("creando una bala");
		System.out.println(origin.getX());
		System.out.println(origin.getY());
		System.out.println(new Point2D(vbull, vbull).multiply(direction));
		BulletDefault b = new BulletDefault(shooter, new Point2D((origin.getX()/Config.scale.getX())
				,(origin.getY() / Config.scale.getY())), new Point2D(vbull, vbull).multiply(direction));
		Game.getGame().render.add(b);
		Game.getGame().updater.add(b);
		resetCD();
	}
}
