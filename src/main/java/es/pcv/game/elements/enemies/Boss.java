package es.pcv.game.elements.enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.Random;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.game.configuration.Config;
import es.pcv.game.elements.player.Player;
import es.pcv.game.elements.weapons.LaserGun;
import es.pcv.game.elements.weapons.Weapon;

public class Boss extends Enemy{

	Polygon ply;
	Color c = new Color(0, 255, 0);
	private Point2D maxVelocity=(new Point2D(0.005f, 0.005f)).multiply(Config.scale);
	protected Point2D velocity=maxVelocity.clone();
	private float maxModVelocity;
	private boolean colPlayer;
	private long lastTeleport=0;
	private Point2D[] positions;
	private long CD=200;
	
	private Weapon weapon;
	int atack=0;
	
	public Boss(Point2D position,Player pl,Point2D maxVelocity) {
		super(position, new Point2D(-0.005f, -0.005f), new Point2D(0.05f, 0.05f), 10, 1,pl);
		weapon = new LaserGun(this,5000,15,200);
		this.maxVelocity=maxVelocity.clone();
		float x = maxVelocity.getX();
		float y = maxVelocity.getY();
		maxModVelocity=(float) Math.sqrt((x*x)+(y*y));
		
		
		positions = new Point2D[4];
		positions[0] = new Point2D(0.2f,0.2f).setAbsolutePosition();
		positions[1] = new Point2D(0.8f,0.2f).setAbsolutePosition();
		positions[2] = new Point2D(0.2f,0.8f).setAbsolutePosition();
		positions[3] = new Point2D(0.8f,0.8f).setAbsolutePosition();
	}

	public Point2D calcularVel(){
		Point2D point=pl.getPos();
		float x = getPos().getX();
		float y = getPos().getY();
		x=(point.getX()-pl.getSizeX()/2)-x;
		y=(point.getY()-pl.getSizeY()/2)-y;		
		float mod=(float) Math.sqrt((x*x)+(y*y));
		x=(x/mod);
		y=(y/mod);
		Point2D vel=new Point2D(x,y);
		return vel;
	}
	
	public void attack(Point2D point){
		// Calculate offset
		float ox = (float) (getSizeX());
		float oy = (float) (getSizeY());

		weapon.attack(this, new Point2D(ox, oy).add(getPos()), point);
	}
	
	public void update(long ms) {
		
		long time = System.currentTimeMillis();
		if((System.currentTimeMillis()-lastTeleport)>1200){
			lastTeleport = time;
			Random r = new Random();
			int n =r.nextInt(4);
			setPos(positions[n]);
			boolean dir = r.nextInt(2) == 0;
			if(dir){
				if(n==0){
					velocity.setX(2);
					velocity.setY(0);
				}else if(n==1){
					velocity.setX(0);
					velocity.setY(2);			
				}else if(n==2){
					velocity.setX(0);
					velocity.setY(-2);			
				}else if(n==3){
					velocity.setX(-2);
					velocity.setY(0);			
				}
			}else{
				if(n==0){
					velocity.setX(0);
					velocity.setY(2);
				}else if(n==1){
					velocity.setX(-2);
					velocity.setY(0);			
				}else if(n==2){
					velocity.setX(2);
					velocity.setY(0);			
				}else if(n==3){
					velocity.setX(0);
					velocity.setY(-2);	
				}
			}
			
		}
		Point2D di=calcularVel();
		if(atack+ms>CD){
			attack(di.clone());
			atack=0;
		}
		else{
			atack+=ms;
		}
		System.out.println(velocity.getX());
		posAdd(velocity);
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
