package es.pcv.game.elements.enemies;

import es.pcv.core.render.Point2D;
import es.pcv.core.render.auxiliar.PolygonHelper;
import es.pcv.core.updater.elements.Collisionable;
import es.pcv.game.configuration.Config;
import es.pcv.game.elements.player.Player;
import es.pcv.game.elements.scene.Wall;
import es.pcv.game.elements.weapons.LaserGun;
import es.pcv.game.elements.weapons.Weapon;

public class EnemyWall extends Enemy {

	// Polygon ply;
	// Color c = new Color(0, 255, 0);
	private float maxModVelocity=0.1f;
	int atack = 0;
	private long CD = 400;
	private Weapon weapon = new LaserGun(this,5000);
	private Wall wall;
	private Point2D size=new Point2D(0.05f, 0.05f).multiply(Config.scale);

	public EnemyWall(Wall w, Player pl, Point2D maxVelocity, Weapon wp) {
		super(w.getCenterPos(), new Point2D(0, 0), new Point2D(0.05f, 0.05f), 10, 1, pl);
		rect=PolygonHelper.getRectangle(w.getCenterPos(), size).getBounds2D();
		wall = w;
		weapon = wp;
		weapon.equip(this);
		float x = maxVelocity.getX();
		float y = maxVelocity.getY();
		maxModVelocity = (float) Math.sqrt((x * x) + (y * y));
		this.addLive(500);
	}

	public EnemyWall(Wall w, Player pl) {
		super(w.getCenterPos(), new Point2D(0, 0), new Point2D(0.05f, 0.05f), 10, 1, pl);
		rect=PolygonHelper.getRectangle(w.getCenterPos(), size).getBounds2D();
		wall = w;
		weapon.equip(this);
		this.addLive(500);
	}

	public void attack(Point2D point) {
		// Calculate offset
		float ox = (float) (getSizeX()/2);
		float oy = (float) (getSizeY()/2);

		weapon.attack(this, new Point2D(ox, oy).add(getPos()), point);
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
		x=x*maxModVelocity;
		y=y*maxModVelocity;
		Point2D di = new Point2D(x,y);
		if(atack+ms>CD){
			Point2D s=di.clone();
			if (wall.getSizeX()>wall.getSizeY()) {
				s.setX(0);
				s.setY(1);
			}else{
				s.setY(0);
				s.setX(1);
			}
			attack(s);
			atack=0;
		}
		else{
			atack+=ms;
		}

		velocity.multiply(0);
		if (wall.getCollisionBox().getMinX() < getX() + di.getX()
				&& wall.getCollisionBox().getMaxX() > getX() + di.getX()) {
			velocity.setX(di.getX());
		}
		if (wall.getCollisionBox().getMinY() < getY() + di.getY()
				&& wall.getCollisionBox().getMaxY() > getY() + di.getY()) {
			velocity.setY(di.getY());
		}
		
		if (wall.getCollisionBox().getMinX()>getX()) {
			setX((int) (wall.getCollisionBox().getMinX()));
		}else if (wall.getCollisionBox().getMaxX()<getX()) {
			setX((int) wall.getCollisionBox().getMaxX());
		}
		if (wall.getCollisionBox().getMinY()>getY()) {
			setX((int) (wall.getCollisionBox().getMinY()));
		}else if (wall.getCollisionBox().getMaxY()<getY()) {
			setX((int) wall.getCollisionBox().getMaxY());
		}
		super.update(ms);

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