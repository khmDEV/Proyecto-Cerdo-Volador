package es.pcv.game.elements.weapons.bulls;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Walker;

public class BulletRepeater extends BulletLaser {

	private int hits = 3;
	public BulletRepeater(Walker whoAttack, Point2D position, Point2D vel) {
		super(whoAttack, position, vel);
	}

	public BulletRepeater(Walker whoAttack, Point2D position, Point2D vel, int hits, int damage) {
		super(whoAttack, position, vel, 1, damage);
		this.hits = hits;
	}

	public boolean kill() {
		if (hits != 0) {
			if (obstacle_collision_dx || obstacle_collision_ux) {
				velocity.multiply(new Point2D(-1, 1));
			}
			if (obstacle_collision_dy || obstacle_collision_uy) {
				velocity.multiply(new Point2D(1, -1));
			}
			posAdd(velocity);
			hits--;
			return false;
		}else{
			return super.kill();
		}
	}
	

}
