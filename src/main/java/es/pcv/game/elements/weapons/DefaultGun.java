package es.pcv.game.elements.weapons;

import java.awt.Graphics;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.LiveEntity;
import es.pcv.core.updater.elements.Walker;
import es.pcv.game.Game;
import es.pcv.game.configuration.Config;
import es.pcv.game.elements.weapons.bulls.BulletDefault;

public class DefaultGun extends Gun {
	private float vbull = 0.05f;
	
	public DefaultGun(Walker w,Point2D p, int l, int d) {
		super(w,p,l,d,50);
	}

	public void shoot(Walker shooter, Point2D origin, Point2D direction) {
		BulletDefault b = new BulletDefault(shooter, origin.getAbsolutePosition(),
						new Point2D(vbull, vbull).multiply(direction));
		Game.getGame().addElement(b);
		resetCD();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}
}
