package es.pcv.game.elements.weapons.bulls;

import java.awt.Color;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Walker;

public class BulletRepeater extends BulletLaser {

	private int hits = 3;
	private static final long FIX = 40;
	private long last = 0;
	Color color = new Color(255, 0, 0);

	public BulletRepeater(Walker whoAttack, Point2D position, Point2D vel) {
		super(whoAttack, position, vel);
		c = color;
	}

	public BulletRepeater(Walker whoAttack, Point2D position, Point2D vel, int hits, int damage) {
		super(whoAttack, position, vel, 1, damage);
		this.hits = hits;
		c = color;

	}

	public boolean kill() {
		if (System.currentTimeMillis() - last > FIX) {
			if (hits != 0) {
				if (obstacle_collision_dx || obstacle_collision_ux) {
					velocity.multiply(new Point2D(-1, 1));
				}
				if (obstacle_collision_dy || obstacle_collision_uy) {
					velocity.multiply(new Point2D(1, -1));
				}
				update(10);
				hits--;
				return false;
			} else {
				return super.kill();
			}
		}else{
			return false;
		}
	}

}
