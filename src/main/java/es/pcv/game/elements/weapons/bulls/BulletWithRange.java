package es.pcv.game.elements.weapons.bulls;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.LiveEntity;
import es.pcv.core.updater.elements.Walker;

public class BulletWithRange extends BulletDefault {

	private final Point2D init;
	private final float limit;
	public BulletWithRange(Walker whoAttack, Point2D position, Point2D vel,float limit) {
		super(whoAttack, position, vel);
		init=position.clone();
		this.limit=limit;
	}

	public BulletWithRange(Walker whoAttack, Point2D position, Point2D vel, int hits, int damage,float limit) {
		super(whoAttack, position, vel, hits, damage);
		init=position.clone();
		this.limit=limit;
	}
	
	public void update(){
		Point2D d=init.clone().multiply(-1).add(getPos());
		double dist=Math.sqrt(Math.pow(d.getX(), 2)+Math.pow(d.getY(), 2));
		System.out.println(dist);
		if (dist<limit) {
			super.update();
		}else{
			kill();
		}
		
	}

}
