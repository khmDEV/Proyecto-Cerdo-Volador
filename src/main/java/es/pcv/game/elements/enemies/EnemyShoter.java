package es.pcv.game.elements.enemies;

import es.pcv.core.render.ObjectIcon;
import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.game.configuration.Config;
import es.pcv.game.elements.player.Player;
import es.pcv.game.elements.weapons.LaserGun;
import es.pcv.game.elements.weapons.Weapon;

public class EnemyShoter extends EnemyMelee{

	//Polygon ply;
	//Color c = new Color(0, 255, 0);
	protected Point2D maxVelocity=(new Point2D(0.0005f, 0.0005f)).multiply(Config.scale);
	protected int atack=0;
	protected long CD=400;
	protected Weapon weapon = new LaserGun(this,0);
	
	public EnemyShoter(Point2D position,Player pl,Point2D maxVelocity, Weapon wp) {		
		super(position, pl, maxVelocity);
		icon= new ObjectIcon(Config.RESOURCES_PATH + "/icons/wintersoldier.png", 4, 4);
		this.maxVelocity=maxVelocity.clone();
		weapon=wp;
		weapon.equip(this);
		//this.addLive(500);
	}
	public EnemyShoter(Point2D position,Player pl,Point2D maxVelocity) {		
		super(position, pl, maxVelocity);
		icon= new ObjectIcon(Config.RESOURCES_PATH + "/icons/wintersoldier.png", 4, 4);
		this.maxVelocity=maxVelocity.clone();
		weapon.equip(this);
		//this.addLive(500);
	}
	
	
	
	public EnemyShoter(Point2D position,Player pl) {		
		super(position,pl);
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
	
	public void attack(long ms,Point2D point) {
		if(atack+ms>CD){
			// Calculate offset
			float ox = (float) (getSizeX()/2);
			float oy = (float) (getSizeY()/2);

			weapon.attack(this, new Point2D(ox, oy).multiply(point).add(getPos()), point);
			atack=0;
		}
		else{
			atack+=ms;
		}
		
	}
	
	public void update(long ms) {
		if(ms>100){
			
		}
		else{
			Point2D di=calcularVel();
			attack(di.clone());
			super.update(ms);
		}
	}
	
	public void move(){
		
	}

}