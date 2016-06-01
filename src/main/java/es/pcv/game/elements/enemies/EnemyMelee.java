package es.pcv.game.elements.enemies;

import java.awt.Polygon;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.game.configuration.Config;
import es.pcv.game.elements.player.Player;

public class EnemyMelee extends Enemy {

	Polygon ply;
	private Point2D maxVelocity=(new Point2D(0.0003f, 0.0003f)).multiply(Config.scale);
	private float maxModVelocity;
	private boolean colPlayer;
	public EnemyMelee(Point2D position,Player pl) {
		super(position, new Point2D(0, 0), new Point2D(0.05f, 0.05f), 10, 1,pl);
		velocity=maxVelocity;
		float x = maxVelocity.getX();
		float y = maxVelocity.getY();
		maxModVelocity=(float) Math.sqrt((x*x)+(y*y));
		colPlayer=false;
		this.addLive(500);
	}
	
	public EnemyMelee(Point2D position,Player pl,Point2D maxVelocity) {
		super(position, new Point2D(0, 0), new Point2D(0.05f, 0.05f), 10, 1,pl);
		this.maxVelocity=maxVelocity.clone();
		float x = maxVelocity.getX();
		float y = maxVelocity.getY();
		maxModVelocity=(float) Math.sqrt((x*x)+(y*y));
		colPlayer=false;
	}
	
	public EnemyMelee(Point2D position,Player pl,Point2D maxVelocity,int live) {
		super(position,new Point2D(0, 0), new Point2D(0.05f, 0.05f), 10, 1,pl);
		this.maxVelocity=maxVelocity.clone();
		float x = maxVelocity.getX();
		float y = maxVelocity.getY();
		maxModVelocity=(float) Math.sqrt((x*x)+(y*y));
		colPlayer=false;
		this.setMaxLive(live);
		addLive(live);
	}

	public void update(long ms) {
		if(ms>100){
			
		}
		else{
		Point2D point=pl.getPos();
		float x = getPos().getX();
		float y = getPos().getY();
		x=point.getX()-x;
		y=point.getY()-y;
		float mod=(float) Math.sqrt((x*x)+(y*y));
		x=(x/mod)*maxModVelocity;
		y=(y/mod)*maxModVelocity;
		velocity=new Point2D(x,y);
		if (obstacle_collision_dx && obstacle_collision_ux) {
			velocity.setX(0);
			obstacle_collision_dx = false;
			obstacle_collision_ux = false;
		}else if (obstacle_collision_dx) {
			velocity.setX(-Math.abs(maxVelocity.getX()));
			obstacle_collision_dx = false;
		}else if (obstacle_collision_ux) {
			velocity.setX(Math.abs(maxVelocity.getX()));
			obstacle_collision_ux = false;
		}
		if (obstacle_collision_dy && obstacle_collision_uy) {
			velocity.setY(0);
			obstacle_collision_dy = false;
			obstacle_collision_uy = false;
		}else if (obstacle_collision_dy) {
			velocity.setY(-Math.abs(maxVelocity.getY()));
			obstacle_collision_dy = false;
		}else if (obstacle_collision_uy) {
			velocity.setY(Math.abs(maxVelocity.getY()));
			obstacle_collision_uy = false;
		}
		if(colPlayer){
			velocity.setX(-velocity.getX());
			velocity.setY(-velocity.getY());
			colPlayer=false;
		}

		super.update(ms);
		}
	}

	public void collision(Collisionable col) {
		super.collision(col);
		if (col instanceof Player) {
			Player pl = (Player) col;
			pl.doDamage(getDamage());
			colPlayer=true;
		}
	}


}
