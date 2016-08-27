package es.pcv.game.elements.enemies;

import es.pcv.core.render.ObjectIcon;
import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.game.configuration.Config;
import es.pcv.game.elements.player.Player;
import es.pcv.game.elements.weapons.LaserGun;
import es.pcv.game.elements.weapons.Weapon;

public class EnemyShoter extends Enemy{

	//Polygon ply;
	//Color c = new Color(0, 255, 0);
	private Point2D maxVelocity=(new Point2D(0.0005f, 0.0005f)).multiply(Config.scale);
	int atack=0;
	private long CD=400;
	private Weapon weapon = new LaserGun(this,5000);
	
	public EnemyShoter(Point2D position,Player pl,Point2D maxVelocity, Weapon wp) {		
		super(position, new Point2D(0, 0), new Point2D(0.05f, 0.05f), 20, 1,pl);
		icon= new ObjectIcon(Config.RESOURCES_PATH + "/icons/wintersoldier.png", 4, 4);
		this.maxVelocity=maxVelocity.clone();
		weapon=wp;
		weapon.equip(this);
		//this.addLive(500);
	}
	public EnemyShoter(Point2D position,Player pl,Point2D maxVelocity) {		
		super(position, new Point2D(0, 0), new Point2D(0.05f, 0.05f), 20, 1,pl);
		icon= new ObjectIcon(Config.RESOURCES_PATH + "/icons/wintersoldier.png", 4, 4);
		this.maxVelocity=maxVelocity.clone();
		weapon.equip(this);
		//this.addLive(500);
	}
	
	
	
	public EnemyShoter(Point2D position,Player pl) {		
		super(position, new Point2D(0, 0), new Point2D(0.05f, 0.05f), 20, 1,pl);
		icon= new ObjectIcon(Config.RESOURCES_PATH + "/icons/wintersoldier.png", 4, 4);
		weapon.equip(this);
		//this.addLive(500);
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
		x=(x/mod);
		y=(y/mod);
		Point2D vel=new Point2D(x,y);
		return vel;
	}
	public void update(long ms) {
		if(ms>100){
			
		}
		else{
			Point2D di=calcularVel();
			if(atack+ms>CD){
				attack(di.clone());
				atack=0;
			}
			else{
				atack+=ms;
			}
			velocity=di.clone().multiply(maxVelocity);
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
			super.update(ms);
		}

	}
	
	public void collision(Collisionable col) {
		super.collision(col);
		if (col instanceof Player) {
			Player pl = (Player) col;
			pl.doDamage(getDamage());
		}
	}

}