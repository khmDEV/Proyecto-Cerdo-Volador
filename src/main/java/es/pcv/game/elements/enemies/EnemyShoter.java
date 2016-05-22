package es.pcv.game.elements.enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.concurrent.Semaphore;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.game.configuration.Config;
import es.pcv.game.elements.player.Player;
import es.pcv.game.elements.weapons.LaserGun;
import es.pcv.game.elements.weapons.Weapon;

public class EnemyShoter extends Enemy{

	//Polygon ply;
	//Color c = new Color(0, 255, 0);
	private final static Point2D maxVelocity=(new Point2D(0.005f, 0.005f)).multiply(Config.scale);
	protected Point2D velocity=maxVelocity.clone();
	private float maxModVelocity;
	int atack=0;
	private Weapon weapon = new LaserGun(this);
	public EnemyShoter(Point2D position,Player pl) {		
		super(position, new Point2D(-0.005f, -0.005f), new Point2D(0.05f, 0.05f), 10, 1,pl);
		weapon.equip(this);
		float x = velocity.getX();
		float y = velocity.getY();
		maxModVelocity=(float) Math.sqrt((x*x)+(y*y));
		this.addLive(500);
	}
	public void attack(Point2D point){
		// Calculate offset
		float ox = (float) (getSizeX());
		float oy = (float) (getSizeY());

		weapon.attack(this, new Point2D(ox, oy).add(getPos()), point);
	}
	public Point2D calcularVel(){
		Point2D point=pl.getPos();
		float x = getPos().getX();
		float y = getPos().getY();
		x=(point.getX()-pl.getSizeX()/2)-x;
		y=(point.getY()-pl.getSizeY()/2)-y;		
		float mod=(float) Math.sqrt((x*x)+(y*y));
		x=(x/mod)*maxModVelocity;
		y=(y/mod)*maxModVelocity;
		Point2D vel=new Point2D(x/10,y/10);
		return vel;
	}
	public void update() {		
		if(atack==10){
			attack(calcularVel());
			atack=0;
		}
		else{
			atack++;
		}
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
		
		//posAdd(vel);
	}
	
	/*public void draw(Graphics g) {
		//g.setColor(c);
		g.fillRect(getX(), getY(), getSizeX() , getSizeY());
	}*/

	public void collision(Collisionable col) {
		super.collision(col);
		if (col instanceof Player) {
			Player pl = (Player) col;
			pl.doDamage(getDamage());
		}
	}

}