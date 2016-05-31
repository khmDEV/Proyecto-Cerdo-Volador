package es.pcv.game.elements.weapons.bulls;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Walker;
import es.pcv.game.configuration.Config;

public class BulletWithRange extends BulletDefault {

	private final Point2D init;
	private final float limit;
	public BulletWithRange(Walker whoAttack, Point2D position, Point2D vel,float limit) {
		super(whoAttack, position, vel);
		init=getPos().clone();
		this.limit=limit * Config.scale.getX();
	}

	public BulletWithRange(Walker whoAttack, Point2D position, Point2D vel, int hits, int damage,float limit) {
		super(whoAttack, position, vel, hits, damage);
		init=getPos().clone();
		this.limit=limit * Config.scale.getX();
	}
	
	public void update(long ms){
		Point2D d=init.clone().multiply(-1).add(getPos());
		double dist=Math.sqrt(Math.pow(d.getX(), 2)+Math.pow(d.getY(), 2));

		if (dist<limit) {
			super.update(ms);
		}else{
			kill();
		}
		
	}

}
