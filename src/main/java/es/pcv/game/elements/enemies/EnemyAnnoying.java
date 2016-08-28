package es.pcv.game.elements.enemies;

import java.awt.Color;
import java.awt.Graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import es.pcv.core.render.ObjectIcon;
import es.pcv.core.render.Point2D;
import es.pcv.core.render.auxiliar.Helper3D;
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
	protected int TEXTURE = 5;
	protected ObjectIcon icon_hide= new ObjectIcon(Config.RESOURCES_PATH + "/icons/Jelly_no_eye.png", 1, 1),
						icon_show= new ObjectIcon(Config.RESOURCES_PATH + "/icons/Jelly_eye.png", 1, 1);;

	public EnemyAnnoying(Point2D position,Player pl, Weapon wp) {		
		super(position, new Point2D(0, 0), new Point2D(0.05f, 0.05f), 40, 1,pl);
		icon=icon_hide;
		weapon=wp;
		weapon.equip(this);
		//this.addLive(500);
	}
	
	public EnemyAnnoying(Point2D position,Player pl) {		
		super(position, new Point2D(0, 0), new Point2D(0.05f, 0.05f), 40, 1,pl);
		icon=icon_hide;
		weapon.equip(this);
		//this.addLive(500);
	}
	public EnemyAnnoying(Point2D position,Player pl, int damage) {		
		super(position, new Point2D(0, 0), new Point2D(0.05f, 0.05f), 40, 1,pl);
		icon=icon_hide;
		weapon = new AllDirectionsWeapon(this,damage);
		weapon.equip(this);
		//this.addLive(500);
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
		if (!vulnerable) {
			atack=0;
		}
		
		return vulnerable;
	}

	public void update(long ms) {	
if(ms>100){
			
		}
		else{
		Point2D dis=calcularDirection();

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
		}}
	}
	
	public void draw(Graphics g){
		if (vulnerable) {
			g.drawImage(icon_show.getImage(0), getX(), getY(), getSizeX(), getSizeY(), null);
		}else{
			g.drawImage(icon_hide.getImage(0), getX(), getY(), getSizeX(), getSizeY(), null);
		}
	}
	
	public void collision(Collisionable col) {
		super.collision(col);
		if (col instanceof Player) {
			Player pl = (Player) col;
			pl.doDamage(getDamage());
		}
	}
	Color inv=new Color(0, 0, 0),vul=new Color(0.3f, 0.3f, 0.3f);
	@Override
	public void draw3d(GL2 gl, GLU glu, GLUquadric quadric) {
		Point2D p=getCenterPos().adaptar();
		Point2D s=getSize().adaptar();
		if (vulnerable) {
			Helper3D.drawCilinder(gl,glu,quadric,p.getX(), 0,p.getY(), .1f, s.getY(),s.getY(),vul,TEXTURE);
		}else{
			Helper3D.drawRectangle(gl, getCenterPos(), getSize(), 0, 0.1f, null,3);
		}
	}


}