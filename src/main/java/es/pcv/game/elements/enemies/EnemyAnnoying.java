package es.pcv.game.elements.enemies;

import java.awt.Graphics;

import es.pcv.core.render.Point2D;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.game.configuration.Config;
import es.pcv.game.elements.player.Player;
import es.pcv.game.elements.weapons.AllDirectionsWeapon;
import es.pcv.game.elements.weapons.Weapon;

public class EnemyAnnoying extends Enemy{

	//Polygon ply;
	//Color c = new Color(0, 255, 0);
	int atack=0;
	private Weapon weapon = new AllDirectionsWeapon(this);
	private long CD_SHOOT=2000,CD_VULNERABLE=1000;
	private double MIN_DISTANCE=AllDirectionsWeapon.SCOPE_DEFAULT*Config.scale.getX();
	private boolean vulnerable=false;
	public EnemyAnnoying(Point2D position,Player pl, Weapon wp) {		
		super(position, new Point2D(0, 0), new Point2D(0.05f, 0.05f), 10, 1,pl);
		weapon=wp;
		weapon.equip(this);
		this.addLive(500);
	}
	
	public EnemyAnnoying(Point2D position,Player pl) {		
		super(position, new Point2D(0, 0), new Point2D(0.05f, 0.05f), 10, 1,pl);
		weapon.equip(this);
		this.addLive(500);
	}
	public void attack(Point2D point){
		// Calculate offset
		float ox = (float) (getSizeX()/2-1);
		float oy = (float) (getSizeY()/2-1);

		weapon.attack(this, new Point2D(ox, oy).add(getPos()), point);
	}
	public Point2D calcularDirection(){
		Point2D point=pl.getPos();
		float x = getPos().getX();
		float y = getPos().getY();
		x=(point.getX()-pl.getSizeX()/2)-x;
		y=(point.getY()-pl.getSizeY()/2)-y;		
		Point2D vel=new Point2D(x,y);
		return vel;
	}
	
	public boolean isVulnerable(){
		atack=0;
		return vulnerable;
	}

	public void update(long ms) {	
		System.out.println(atack);
		Point2D dis=calcularDirection();

		System.out.println(dis.value()+" < "+MIN_DISTANCE);
		if(atack>CD_SHOOT&&atack<=CD_VULNERABLE+CD_SHOOT){
			if (vulnerable||dis.value()<MIN_DISTANCE) {
				attack(dis);
				vulnerable=true;
				atack+=ms;
			}
		}else if(atack>CD_VULNERABLE+CD_SHOOT){
			atack=0;
			vulnerable=false;
		}else{
			atack+=ms;
		}
	}
	
	public void draw(Graphics g){
		if (vulnerable) {
			g.drawOval(getX(), getY(), getSizeX(), getSizeY());
		}else{
			g.drawRect(getX(), getY(), getSizeX(), getSizeY());
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