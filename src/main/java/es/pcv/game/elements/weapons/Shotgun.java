package es.pcv.game.elements.weapons;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.LiveEntity;
import es.pcv.core.updater.elements.Walker;
import es.pcv.game.Game;
import es.pcv.game.elements.weapons.bulls.BulletWithRange;

public class Shotgun extends Gun{
	private float vbull = 0.05f;
	
	public Shotgun() {
		super(1000);
	}
	
	@Override
	public void shoot(Walker shooter, Point2D origin, Point2D direction) {
		for (double i = -Math.PI/4; i < Math.PI/4; i+=Math.PI/8) {
			//System.out.println(new Point2D(vbull*Math.sin(i), vbull*Math.cos(i)).add(direction));
			double v=i+Math.atan2(direction.getY(),direction.getX());
			BulletWithRange b = new BulletWithRange(shooter, origin,new Point2D(Math.cos(v), Math.sin(v)).multiply(vbull),0.2f);
			Game.getGame().render.add(b);
			Game.getGame().updater.add(b);
		}
		resetCD();
	}
	

}
