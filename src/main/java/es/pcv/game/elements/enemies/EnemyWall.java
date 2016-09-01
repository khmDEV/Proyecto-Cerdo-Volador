package es.pcv.game.elements.enemies;

import java.awt.geom.Rectangle2D;

import es.pcv.core.render.ObjectIcon;
import es.pcv.core.render.Point2D;
import es.pcv.core.render.auxiliar.PolygonHelper;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.game.configuration.Config;
import es.pcv.game.elements.player.Player;
import es.pcv.game.elements.scene.Wall;
import es.pcv.game.elements.weapons.LaserGun;
import es.pcv.game.elements.weapons.Weapon;
import es.pcv.game.elements.weapons.WeaponEntity;

public class EnemyWall extends EnemyShoter {

	// Polygon ply;
	// Color c = new Color(0, 255, 0);
	private float maxModVelocity=0.1f;
	private Wall wall;
	private Point2D size=new Point2D(0.05f, 0.05f).multiply(Config.scale);
	private int DIFF=10;
	public EnemyWall(Wall w, Player pl, Point2D maxVelocity, Weapon wp) {
		super(w.getCenterPos(), pl, maxVelocity, wp);
		icon= new ObjectIcon(Config.RESOURCES_PATH + "/icons/silverbat.png", 4, 4);
		rect=PolygonHelper.getRectangle(w.getCenterPos(), size).getBounds2D();
		wall = w;
		weapon = wp;
		weapon.equip(this);
		CD = 400;
		float x = maxVelocity.getX();
		float y = maxVelocity.getY();
		maxModVelocity = (float) Math.sqrt((x * x) + (y * y));
		this.addLive(500);
	}

	public EnemyWall(Wall w, Player pl) {
		super(w.getCenterPos(),pl, new Point2D(0.05f, 0.05f));
		icon= new ObjectIcon(Config.RESOURCES_PATH + "/icons/silverbat.png", 4, 4);

		rect=PolygonHelper.getRectangle(w.getCenterPos(), size).getBounds2D();
		wall = w;
		this.addLive(500);
		CD = 400;
	}

	public void update(long ms) {
		if(ms>100){
			
		}
		else{	
		Point2D point=pl.getPos();
		float x = getPos().getX();
		float y = getPos().getY();
		x=point.getX()-x<0?-1:1;
		y=point.getY()-y<0?-1:1;
		float xv=x*maxModVelocity;
		float yv=y*maxModVelocity;
		Point2D di = new Point2D(xv,yv);
		Point2D s=di.clone();
		if (wall.getSizeX()>wall.getSizeY()) {
			s.setX(0);
			s.setY(y);
		}else{
			s.setY(0);
			s.setX(x);
		}
		attack(s);

		velocity.multiply(0);
		if (wall.getCollisionBox().getMinX() < getX() 
				&& wall.getCollisionBox().getMaxX() > getX() ) {
			velocity.setX(di.getX());
		}
		if (wall.getCollisionBox().getMinY() < getY() 
				&& wall.getCollisionBox().getMaxY() > getY() ) {
			velocity.setY(di.getY());
		}
		
		if (wall.getCollisionBox().getMinX()>getX()) {
			setX((int) (wall.getCollisionBox().getMinX()));
		}else if (wall.getCollisionBox().getMaxX()<getX()) {
			setX((int) wall.getCollisionBox().getMaxX());
		}
		if (wall.getCollisionBox().getMinY()>getY()) {
			setY((int) (wall.getCollisionBox().getMinY()));
		}else if (wall.getCollisionBox().getMaxY()<getY()) {
			setY((int) wall.getCollisionBox().getMaxY());
		}

		move(ms);
		}
	}
	
	public void attack(Point2D point) {
		// Calculate offset
		float ox = (float) (getSizeX()/2);
		float oy = (float) (getSizeY()/2);

		weapon.attack(this, new Point2D(ox, oy).multiply(point).add(getPos()), point);
	}

	public boolean isCollision(Collisionable c) {
		if (!(c instanceof WeaponEntity)&&!(c instanceof Player)) {
			return false;
		}
		Rectangle2D r=PolygonHelper.getRectangle(getPos().clone().add(DIFF), getSize()).getBounds2D();
		Rectangle2D r2=PolygonHelper.getRectangle(getPos().clone().add(-DIFF), getSize()).getBounds2D();
		return c.getCollisionBox().intersects(rect)||c.getCollisionBox().intersects(r)||c.getCollisionBox().intersects(r2);
	}

}