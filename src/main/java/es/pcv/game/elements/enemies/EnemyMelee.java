package es.pcv.game.elements.enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.game.configuration.Config;
import es.pcv.game.elements.player.Player;

public class EnemyMelee extends Enemy {

	Polygon ply;
	Color c = new Color(0, 255, 0);
	private final static Point2D maxVelocity=(new Point2D(0.005f, 0.005f)).multiply(Config.scale);
	protected Point2D velocity=maxVelocity.clone();
	private float maxModVelocity;
	private boolean colPlayer;
	public EnemyMelee(Point2D position,Player pl) {
		super(position, new Point2D(-0.005f, -0.005f), new Point2D(0.05f, 0.05f), 10, 1,pl);
		float x = velocity.getX();
		float y = velocity.getY();
		maxModVelocity=(float) Math.sqrt((x*x)+(y*y));
		colPlayer=false;
		this.addLive(500);
	}

	public void update() {
		
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
		posAdd(velocity);
	}

	public void draw(Graphics g) {
		g.setColor(c);
		g.fillRect(getX(), getY(), getSizeX() , getSizeY());
	}

	public void collision(Collisionable col) {
		super.collision(col);
		if (col instanceof Player) {
			Player pl = (Player) col;
			pl.doDamage(getDamage());
			colPlayer=true;
		}
		c = new Color((int) Math.round(Math.random() * 255), (int) Math.round(Math.random() * 255),
				(int) Math.round(Math.random() * 255));
	}

}
