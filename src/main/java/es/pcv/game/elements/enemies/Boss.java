package es.pcv.game.elements.enemies;

import java.awt.Color;
import java.awt.Polygon;
import java.util.Random;

import es.pcv.core.render.ObjectIcon;
import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.game.configuration.Config;
import es.pcv.game.elements.player.Player;
import es.pcv.game.elements.weapons.LaserGun;
import es.pcv.game.elements.weapons.Weapon;

public class Boss extends EnemyShoter{

	Polygon ply;
	Color c = new Color(0, 255, 0);
	private long lastTeleport=0;
	private Point2D[] positions;
	
	public Boss(Point2D position,Player pl,Point2D maxVelocity) {
		super(position, pl,new Point2D(0.025f, 0.025f));
		weapon = new LaserGun(this,0,15,200);
		icon= new ObjectIcon(Config.RESOURCES_PATH + "/icons/ryuk.png", 4, 4);
		
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
		//System.out.println(velocity.getX());
		posAdd(velocity);
	}


}
